package com.capstone.pantauharga.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.ListCommoditiesItem
import com.capstone.pantauharga.data.response.ListProvincesItem
import com.capstone.pantauharga.data.retrofit.ApiConfig
import com.capstone.pantauharga.data.retrofit.ApiService
import com.capstone.pantauharga.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val apiService: ApiService = ApiConfig.getApiService()

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    //    private fun loadFragment(fragment: Fragment, tag: String) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, fragment, tag)
//            .commit()
//        viewModel.setActiveFragment(tag)
//    }
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
