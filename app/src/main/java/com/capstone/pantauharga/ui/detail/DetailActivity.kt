package com.capstone.pantauharga.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.DataItem
import com.capstone.pantauharga.data.response.DataItemProvinsi
import com.capstone.pantauharga.databinding.ActivityDetailBinding

//class DetailActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityDetailBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        supportActionBar?.hide()
//
//        loadFragment(InflationPredictFragment())
//
//        binding.btnInflationPredict.setOnClickListener {
//            loadFragment(InflationPredictFragment())
//        }
//
//        binding.btnNormalPrice.setOnClickListener {
//            loadFragment(NormalPriceFragment())
//        }
//    }
//
//    private fun loadFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, fragment)
//            .commit()
//    }
//}

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

//        val provinsi = intent.getParcelableExtra<DataItemProvinsi>("provinsi")
        val komoditas = intent.getParcelableExtra<DataItem>("komoditas")

//        provinsi?.let { viewModel.setLocation(it.title) }
        komoditas?.let { viewModel.setCommodityName(it.title) }

        viewModel.location.observe(this) { location ->
            binding.tvLocation.text = location
        }

        viewModel.commodityName.observe(this) { commodityName ->
            binding.tvComodityName.text = commodityName
        }

        //loadFragment(InflationPredictFragment())

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

//        binding.btnInflationPredict.setOnClickListener {
//            loadFragment(InflationPredictFragment())
//        }
//
//        binding.btnNormalPrice.setOnClickListener {
//            loadFragment(NormalPriceFragment())
//        }
    }

//    private fun loadFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, fragment)
//            .commit()
//    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .commit()
        viewModel.setActiveFragment(tag)
    }


    private fun updateButtonStyles(activeFragment: String) {
        Log.d("DetailActivity", "Active Fragment: $activeFragment")
        binding.btnInflationPredict.apply {
            if (activeFragment == "InflationPredict") {
                setBackgroundResource(R.drawable.bg_button_active)
                setTextColor(ContextCompat.getColor(context, android.R.color.white))
            } else {
                setBackgroundResource(R.drawable.bg_button_secondary)
                setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
        }

        binding.btnNormalPrice.apply {
            if (activeFragment == "NormalPrice") {
                setBackgroundResource(R.drawable.bg_button_active)
                setTextColor(ContextCompat.getColor(context, android.R.color.white))
            } else {
                setBackgroundResource(R.drawable.bg_button_secondary)
                setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
        }
    }

}