package com.capstone.pantauharga.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.DataItem
import com.capstone.pantauharga.databinding.FragmentHomeBinding
import com.capstone.pantauharga.ui.KomoditasAdapter
import com.capstone.pantauharga.ui.provinsi.ProvinsiActivity
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import androidx.core.widget.addTextChangedListener

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var komoditasAdapter: KomoditasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupUI()
        observeViewModel()

        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        return binding.root
    }

    private fun setupUI() {
        setupRecyclerView()
        setupSearchFunctionality()
    }

    private fun setupSearchFunctionality() {
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable.toString().trim()
            filterCommodities(query)
        }
    }

    private fun filterCommodities(query: String) {
        val filteredList = if (query.isEmpty()) {
            viewModel.komoditas.value ?: emptyList()
        } else {
            viewModel.komoditas.value?.filter { commodity ->
                commodity.namaKomoditas.contains(query, ignoreCase = true)
            } ?: emptyList()
        }

        komoditasAdapter.submitList(filteredList)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewFinished.layoutManager = LinearLayoutManager(context)
        komoditasAdapter = KomoditasAdapter { selectedCommodity ->
            navigateToProvinceActivity(selectedCommodity)
        }
        binding.recyclerViewFinished.adapter = komoditasAdapter
    }


    private fun observeViewModel() {
        viewModel.komoditas.observe(viewLifecycleOwner) { komoditas ->
            komoditasAdapter.submitList(komoditas)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToProvinceActivity(selectedCommodity: DataItem) {
        val intent = Intent(activity, ProvinsiActivity::class.java).apply {
            putExtra("komoditas", selectedCommodity)
        }
        startActivity(intent)
    }
}