package com.capstone.pantauharga.ui.provinsi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.ListCommoditiesItem
import com.capstone.pantauharga.databinding.ActivityProvinceBinding
import com.capstone.pantauharga.ui.detail.DetailActivity
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


//class ProvinsiActivity : AppCompatActivity() {
//
//private lateinit var binding: ActivityProvinceBinding
//    private val viewModel: ProvinceViewModel by viewModels()
//    private lateinit var adapter: ProvinceAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        supportActionBar?.hide()
//
//        binding = ActivityProvinceBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val komoditas = intent.getParcelableExtra<ListCommoditiesItem>("komoditas")
//
//        setupRecyclerView(komoditas)
//        observeViewModel()
//
//        val searchBar = binding.searchBar
//
//        val shapeModel = ShapeAppearanceModel.builder()
//            .setAllCornerSizes(30f)
//            .build()
//
//        @Suppress("DEPRECATION") val materialShapeDrawable = MaterialShapeDrawable(shapeModel).apply {
//            setStroke(3f, resources.getColor(R.color.blue))
//        }
//
//        searchBar.background = materialShapeDrawable
//
//        val provinsiId = intent.getStringExtra("commodityId") ?: ""
//        viewModel.getProvinces(provinsiId)
//    }
//
//    private fun setupRecyclerView(komoditas: ListCommoditiesItem?) {
//        adapter = ProvinceAdapter { selectedProvince ->
//            val intent = Intent(this, DetailActivity::class.java).apply {
//                putExtra("provinsi", selectedProvince)
//                putExtra("komoditas", komoditas)
//            }
//            startActivity(intent)
//        }
//
//        binding.rvProvince.apply {
//            layoutManager = GridLayoutManager(this@ProvinsiActivity, 2)
//            adapter = this@ProvinsiActivity.adapter
//        }
//    }
//
//
//    private fun observeViewModel() {
//        viewModel.provinsi.observe(this) { provinsi ->
//            adapter.submitList(provinsi)
//        }
//
//        viewModel.loading.observe(this) { isLoading ->
//            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }
//
//        viewModel.error.observe(this) { isError ->
//            if (isError) {
//                Toast.makeText(this@ProvinsiActivity, "Gagal memuat data.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//}
class ProvinsiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProvinceBinding
    private val viewModel: ProvinceViewModel by viewModels()
    private lateinit var adapter: ProvinceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val komoditas = intent.getParcelableExtra<ListCommoditiesItem>("komoditas")
        val provinsiId = intent.getStringExtra("commodityId") ?: ""

        Log.d("ProvinsiActivity", "Komoditas ID yang diambil: $komoditas")

        setupRecyclerView(komoditas)
        observeViewModel()

        styleSearchBar()
        viewModel.getProvinces(provinsiId)
    }



    private fun setupRecyclerView(komoditas: ListCommoditiesItem?) {
        adapter = ProvinceAdapter { selectedProvince ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("provinsi", selectedProvince)
                putExtra("komoditas", komoditas)
            }
            startActivity(intent)
        }

        binding.rvProvince.apply {
            layoutManager = GridLayoutManager(this@ProvinsiActivity, 2)
            adapter = this@ProvinsiActivity.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.provinsi.observe(this) { provinsiList ->
            adapter.submitList(provinsiList)
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { isError ->
            if (isError) {
                Toast.makeText(this@ProvinsiActivity, "Gagal memuat data.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun styleSearchBar() {
        val searchBar = binding.searchBar
        val shapeModel = ShapeAppearanceModel.builder()
            .setAllCornerSizes(30f)
            .build()

        @Suppress("DEPRECATION") val materialShapeDrawable = MaterialShapeDrawable(shapeModel).apply {
            setStroke(3f, resources.getColor(R.color.blue))
        }

        searchBar.background = materialShapeDrawable
    }
}