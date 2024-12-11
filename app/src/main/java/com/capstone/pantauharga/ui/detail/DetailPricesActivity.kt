package com.capstone.pantauharga.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.DataItem
import com.capstone.pantauharga.data.response.DataItemDaerah
import com.capstone.pantauharga.data.retrofit.ApiConfig
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.database.NormalPrice
import com.capstone.pantauharga.database.HargaKomoditas
import com.capstone.pantauharga.databinding.ActivityDetailBinding
import com.capstone.pantauharga.repository.PredictInflationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailPricesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var isHargaKomoditasSaved = false
    private var isNormalPriceSaved = false
    private var currentPrediction: HargaKomoditas? = null
    private var currentNormalPrice: NormalPrice? = null
    private lateinit var viewModel: DetailPricesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase.getDatabase(this)
        val apiService = ApiConfig.getApiService()
        val repository = PredictInflationRepository(apiService, database)
        val factory = DetailPricesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[DetailPricesViewModel::class.java]

        supportActionBar?.hide()

        val komoditas = intent.getParcelableExtra<DataItem>("komoditas")
        val provinsi = intent.getParcelableExtra<DataItemDaerah>("provinsi")

        komoditas?.let { viewModel.setCommodityName(it) }
        provinsi?.let { viewModel.setLocation(it) }

        viewModel.location.observe(this) { location ->
            binding.tvLocation.text = location
            checkBookmarkStatus()
        }

        viewModel.commodityName.observe(this) { commodityName ->
            binding.tvComodityName.text = commodityName
            checkBookmarkStatus()
        }

        loadFragment(InflationPredictFragment(), "InflationPredict")

        viewModel.activeFragment.observe(this) { activeFragment ->
            updateButtonStyles(activeFragment)
            checkBookmarkStatus()
        }

        binding.btnInflationPredict.setOnClickListener {
            loadFragment(InflationPredictFragment(), "InflationPredict")
        }

        binding.btnNormalPrice.setOnClickListener {
            loadFragment(NormalPriceFragment(), "NormalPrice")
        }



        binding.btnBookmark.setOnClickListener {
            when (viewModel.activeFragment.value) {
                "InflationPredict" -> handleInflationPredictBookmark()
                "NormalPrice" -> handleNormalPriceBookmark()
            }
        }
    }

    private fun handleInflationPredictBookmark() {
        CoroutineScope(Dispatchers.IO).launch {
            val commodityId = intent.getParcelableExtra<DataItem>("komoditas")?.idKomoditas.toString()
            val provinceId = intent.getParcelableExtra<DataItemDaerah>("provinsi")?.daerahId.toString()

            viewModel.hargaKomoditas.value?.let { inflationData ->

                if (isHargaKomoditasSaved && currentPrediction != null) {
                    viewModel.deleteHargaKomoditasByIds(commodityId, provinceId)
                    isHargaKomoditasSaved = false
                    runOnUiThread {
                        updateBookmarkIcon()
                        Toast.makeText(this@DetailPricesActivity, "removed from bookmarks", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    viewModel.saveAllPredictions(commodityId, provinceId)
                    isHargaKomoditasSaved = true
                    runOnUiThread {
                        updateBookmarkIcon()
                        Toast.makeText(this@DetailPricesActivity, "bookmarked successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                runOnUiThread {
                    Toast.makeText(this@DetailPricesActivity, "No data to bookmark", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleNormalPriceBookmark() {
        CoroutineScope(Dispatchers.IO).launch {
            val commodityId = intent.getParcelableExtra<DataItem>("komoditas")?.idKomoditas.toString()
            val provinceId = intent.getParcelableExtra<DataItemDaerah>("provinsi")?.daerahId.toString()

            viewModel.normalPriceData.value?.let { normalPriceData ->
                if (isNormalPriceSaved && currentNormalPrice != null) {
                    viewModel.deleteHargaNormalByIds(commodityId, provinceId)
                    isNormalPriceSaved = false
                    runOnUiThread {
                        updateBookmarkIcon()
                        Toast.makeText(this@DetailPricesActivity, "Normal price removed from bookmarks", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    viewModel.saveAllNormalPrice(commodityId, provinceId)
                    isNormalPriceSaved = true
                    runOnUiThread {
                        updateBookmarkIcon()
                        Toast.makeText(this@DetailPricesActivity, "Normal price bookmarked successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                runOnUiThread {
                    Toast.makeText(this@DetailPricesActivity, "No normal price data to bookmark", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkBookmarkStatus() {
        val commodityName = binding.tvComodityName.text.toString()
        val provinceName = binding.tvLocation.text.toString()

        if (commodityName.isNotEmpty() && provinceName.isNotEmpty()) {
            when (viewModel.activeFragment.value) {
                "InflationPredict" -> {
                    viewModel.getPredictionByCommodityAndProvince(commodityName, provinceName)
                        .observe(this) { prediction ->
                            isHargaKomoditasSaved = prediction != null
                            currentPrediction = prediction
                            updateBookmarkIcon()
                        }
                }
                "NormalPrice" -> {
                    viewModel.getNormalPriceByCommodityAndProvince(commodityName, provinceName)
                        .observe(this) { normalPrice ->
                            isNormalPriceSaved = normalPrice != null
                            currentNormalPrice = normalPrice
                            updateBookmarkIcon()
                        }
                }
            }
        }
    }

    private fun updateBookmarkIcon() {
        val isSaved = when (viewModel.activeFragment.value) {
            "InflationPredict" -> isHargaKomoditasSaved
            "NormalPrice" -> isNormalPriceSaved
            else -> false
        }

        binding.btnBookmark.setImageResource(
            if (isSaved) R.drawable.save_full
            else R.drawable.save
        )
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        val commodityId = intent.getParcelableExtra<DataItem>("komoditas")?.idKomoditas.toString()
        val provinceId = intent.getParcelableExtra<DataItemDaerah>("provinsi")?.daerahId.toString()

        val args = Bundle().apply {
            putString("komoditas", commodityId)
            putString("provinsi", provinceId)
        }
        fragment.arguments = args

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .commit()
        viewModel.setActiveFragment(tag)
    }

    private fun updateButtonStyles(activeFragment: String) {
        binding.btnInflationPredict.apply {
            setBackgroundResource(
                if (activeFragment == "InflationPredict") R.drawable.bg_button_active
                else R.drawable.bg_button_secondary
            )
            setTextColor(ContextCompat.getColor(context, if (activeFragment == "InflationPredict") android.R.color.white else R.color.blue))
        }

        binding.btnNormalPrice.apply {
            setBackgroundResource(
                if (activeFragment == "NormalPrice") R.drawable.bg_button_active
                else R.drawable.bg_button_secondary
            )
            setTextColor(ContextCompat.getColor(context, if (activeFragment == "NormalPrice") android.R.color.white else R.color.blue))
        }
    }
}