package com.capstone.pantauharga.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.ListCommoditiesItem
import com.capstone.pantauharga.data.response.ListProvincesItem
import com.capstone.pantauharga.data.retrofit.ApiConfig
import com.capstone.pantauharga.data.retrofit.ApiService
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.database.PredictInflation
import com.capstone.pantauharga.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isPredictionSaved = false
    private var currentPrediction: PredictInflation? = null
    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase.getDatabase(this)
        val apiService = ApiConfig.getApiService()
        val factory = DetailViewModelFactory(apiService, database)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        supportActionBar?.hide()

        val komoditas = intent.getParcelableExtra<ListCommoditiesItem>("komoditas")
        val provinsi = intent.getParcelableExtra<ListProvincesItem>("provinsi")

        komoditas?.let { viewModel.setCommodityName(it) }
        provinsi?.let { viewModel.setLocation(it) }

        Log.d("ProvinsiActivity", "Komoditas ID yang diambil: $komoditas")
        Log.d("ProvinsiActivity", "Komoditas ID yang diambil: $provinsi")

        viewModel.location.observe(this) { location ->
            binding.tvLocation.text = location
        }

        viewModel.commodityName.observe(this) { commodityName ->
            binding.tvComodityName.text = commodityName
        }

        loadFragment(InflationPredictFragment(), "InflationPredict")

        viewModel.activeFragment.observe(this) { activeFragment ->
            updateButtonStyles(activeFragment)
        }

        binding.btnInflationPredict.setOnClickListener {
            loadFragment(InflationPredictFragment(), "InflationPredict")
        }

        binding.btnNormalPrice.setOnClickListener {
            loadFragment(NormalPriceFragment(), "NormalPrice")
        }

        checkBookmarkStatus()

        binding.btnBookmark.setOnClickListener {
            // 1. Dapatkan data provinsi_id dan komoditas_id
            var commodityId = komoditas?.id.toString()
            var provinceId = provinsi?.id.toString()
            var months = arrayOf(30, 90, 180, 270, 360, 720);

            CoroutineScope(Dispatchers.IO).launch {
                for(timeRange in months)
                {
                    // 2. Fecth data comodity
                    var result =  viewModel.fetchInflationPrediction(commodityId, provinceId, timeRange)

                    // 3. Hapus seluruh data yang ada di database berdasarkan id_komoditas, id_provinsi, timeRange
                    viewModel.deletePrediction(provinceId, commodityId, timeRange)

                    // 4. Simpan hasil result kedalam database
                    viewModel.savePrediction(
                        provinceId = provinceId,
                        comodityId = comodityId,
                        timeRange  = timeRange,
                        description = inflationData.description,
                        predictions = inflationData.predictions,
                        commodityName = binding.tvComodityName.text.toString(),
                        provinceName = binding.tvLocation.text.toString()
                    )

                }
            }

//            CoroutineScope(Dispatchers.IO).launch {
//                viewModel.inflationData.value?.let { inflationData ->
//                    // MENGAMBIL SELURUH DATA BERDASARKAN RENTANG FILTER
//
//
//                    if (isPredictionSaved && currentPrediction != null) {
//                        viewModel.deletePrediction(currentPrediction!!)
//                        isPredictionSaved = false
//                        runOnUiThread {
//                            updateBookmarkIcon()
//                            Toast.makeText(this@DetailActivity, "Prediction removed from bookmarks", Toast.LENGTH_SHORT).show()
//                        }
//                    } else {
//                        viewModel.savePrediction(
//                            description = inflationData.description,
//                            predictions = inflationData.predictions,
//                            commodityName = binding.tvComodityName.text.toString(),
//                            provinceName = binding.tvLocation.text.toString()
//                        )
//                        isPredictionSaved = true
//                        runOnUiThread {
//                            updateBookmarkIcon()
//                            Toast.makeText(this@DetailActivity, "Prediction bookmarked successfully", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                } ?: run {
//                    runOnUiThread {
//                        Toast.makeText(this@DetailActivity, "No data to bookmark", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
        }

    }

    private fun checkBookmarkStatus() {
        viewModel.getAllPrediction().observe(this) { predictions ->
            if (predictions.isEmpty()) {
                isPredictionSaved = false
            } else {
                val existingPrediction = predictions.firstOrNull()
                isPredictionSaved = existingPrediction != null
                currentPrediction = existingPrediction
            }
            updateBookmarkIcon()
        }
    }

    private fun updateBookmarkIcon() {
        binding.btnBookmark.setImageResource(
            if (isPredictionSaved) R.drawable.save_full
            else R.drawable.save
        )
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        val commodityId = intent.getParcelableExtra<ListCommoditiesItem>("komoditas")?.id.toString()
        val provinceId = intent.getParcelableExtra<ListProvincesItem>("provinsi")?.id.toString()

        if (fragment is InflationPredictFragment ) {
            val args = Bundle().apply {
                putString("komoditas", commodityId)
                putString("provinsi", provinceId)
            }
            fragment.arguments = args
        }

        if (fragment is NormalPriceFragment) {
            val args = Bundle().apply {
                putString("komoditas", commodityId)
                putString("provinsi", provinceId)
            }
            fragment.arguments = args
        }

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

