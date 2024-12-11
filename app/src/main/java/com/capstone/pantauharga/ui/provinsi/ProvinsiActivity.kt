package com.capstone.pantauharga.ui.provinsi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.DataItem
import com.capstone.pantauharga.data.response.DataItemDaerah
import com.capstone.pantauharga.databinding.ActivityProvinceBinding
import com.capstone.pantauharga.ui.detail.DetailPricesActivity
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class ProvinsiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProvinceBinding
    private val viewModel: ProvinceViewModel by viewModels()
    private lateinit var adapter: ProvinceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val komoditas = intent.getParcelableExtra<DataItem>("komoditas")
        val provinsiId = komoditas?.idKomoditas.toString()

        Log.d("ProvinsiActivity", "Komoditas ID yang diambil: $komoditas")

        setupRecyclerView(komoditas)
        observeViewModel()

        setupSearchFunctionality()
        viewModel.getProvinces(provinsiId)
    }

    private fun setupRecyclerView(komoditas: DataItem?) {
        adapter = ProvinceAdapter { selectedProvince ->
            val intent = Intent(this, DetailPricesActivity::class.java).apply {
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

    private fun setupSearchFunctionality() {
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable.toString().trim()
            filterProvinces(query)
        }
    }

    private fun filterProvinces(query: String) {
        val filteredList = if (query.isEmpty()) {
            viewModel.provinsi.value ?: emptyList()
        } else {
            viewModel.provinsi.value?.filter { province ->
                province.namaDaerah.contains(query, ignoreCase = true)
            } ?: emptyList()
        }

        adapter.submitList(filteredList)
    }


}