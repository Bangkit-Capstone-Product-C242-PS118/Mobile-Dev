package com.capstone.pantauharga.ui.provinsi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.pantauharga.R
import com.capstone.pantauharga.databinding.ActivityProvinceBinding
import com.capstone.pantauharga.ui.detail.DetailActivity
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


class ProvinsiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProvinceBinding
    private val viewModel: ProvinceViewModel by viewModels()
    private lateinit var adapter: ProvinceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityProvinceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        val searchBar = binding.searchBar

        val shapeModel = ShapeAppearanceModel.builder()
            .setAllCornerSizes(30f)
            .build()

        @Suppress("DEPRECATION") val materialShapeDrawable = MaterialShapeDrawable(shapeModel).apply {
            setStroke(3f, resources.getColor(R.color.blue))
        }

        searchBar.background = materialShapeDrawable

    }

    private fun setupRecyclerView() {

        adapter = ProvinceAdapter { data ->
//            val intent = Intent(this, InflationPredictActivity::class.java)
//            startActivity(intent)
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("provinsi", data)
            intent.putExtra("komoditas", data)
            startActivity(intent)

        }

        binding.rvProvince.apply {
            layoutManager = GridLayoutManager(this@ProvinsiActivity, 2)
            adapter = this@ProvinsiActivity.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.provinsi.observe(this) { provinsi ->
            adapter.submitList(provinsi)
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

    companion object {
        const val EVENT_ID_KEY = "EVENT_ID"
    }
}