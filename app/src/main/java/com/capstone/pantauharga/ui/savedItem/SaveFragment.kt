package com.capstone.pantauharga.ui.savedItem


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pantauharga.data.retrofit.ApiConfig
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.database.HargaKomoditas
import com.capstone.pantauharga.database.NormalPrice
import com.capstone.pantauharga.databinding.FragmentSavedItemBinding
import com.capstone.pantauharga.repository.PredictInflationRepository
import com.google.android.material.tabs.TabLayout


class SaveFragment : Fragment() {

    private lateinit var binding: FragmentSavedItemBinding
    private lateinit var hargaKomoditasAdapter: HargaKomoditasAdapter
    private lateinit var normalPricesAdapter: NormalPricesAdapter
    private lateinit var viewModel: SaveViewModel
    private var currentItems = mutableListOf<Any>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedItemBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        val apiService = ApiConfig.getApiService()
        val repository = PredictInflationRepository(apiService, database)
        val factory = SaveViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SaveViewModel::class.java]

        setupTabLayout()
        setupRecyclerView()
        loadInitialData()
    }

    private fun loadInitialData() {
        val currentTab = binding.tabLayout.selectedTabPosition
        when (currentTab) {
            0 -> loadHargaKomoditasData()
            1 -> loadNormalPricesData()
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.apply {
            addTab(newTab().setText("Harga Komoditas"))
            addTab(newTab().setText("Normal Prices"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    currentItems.clear()
                    when (tab?.position) {
                        0 -> {
                            binding.rvSave.adapter = hargaKomoditasAdapter
                            loadHargaKomoditasData()
                        }
                        1 -> {
                            binding.rvSave.adapter = normalPricesAdapter
                            loadNormalPricesData()
                        }
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun loadHargaKomoditasData() {
        viewModel.commodityNames.observe(viewLifecycleOwner) { commodityNames ->
            viewModel.provinceNames.observe(viewLifecycleOwner) { provinceNames ->
                val allItems = mutableListOf<HargaKomoditas>()
                var loadedItemCount = 0
                val totalExpectedItems = commodityNames.size * provinceNames.size

                commodityNames.forEach { commodityName ->
                    provinceNames.forEach { provinceName ->
                        viewModel.getPredictionByCommodityAndProvince(commodityName, provinceName)
                            .observe(viewLifecycleOwner) { data ->
                                loadedItemCount++
                                data?.let {
                                    allItems.add(it)
                                }

                                if (loadedItemCount == totalExpectedItems) {
                                    currentItems.clear()
                                    currentItems.addAll(allItems)
                                    hargaKomoditasAdapter.submitList(allItems.toList())
                                    updateEmptyState(allItems.isEmpty())
                                }
                            }
                    }
                }
            }
        }
    }

    private fun loadNormalPricesData() {
        viewModel.commodityNamesNormal.observe(viewLifecycleOwner) { commodityNames ->
            viewModel.provinceNamesNormal.observe(viewLifecycleOwner) { provinceNames ->
                val allItems = mutableListOf<NormalPrice>()
                var loadedItemCount = 0
                val totalExpectedItems = commodityNames.size * provinceNames.size

                commodityNames.forEach { commodityName ->
                    provinceNames.forEach { provinceName ->
                        viewModel.getNormalPriceByCommodityAndProvince(commodityName, provinceName)
                            .observe(viewLifecycleOwner) { data ->
                                loadedItemCount++
                                data?.let {
                                    allItems.add(it)
                                }

                                if (loadedItemCount == totalExpectedItems) {
                                    currentItems.clear()
                                    currentItems.addAll(allItems)
                                    normalPricesAdapter.submitList(allItems.toList())
                                    updateEmptyState(allItems.isEmpty())
                                }
                            }
                    }
                }
            }
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvSave.visibility = View.GONE
        } else {
            binding.rvSave.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        hargaKomoditasAdapter = HargaKomoditasAdapter { hargaKomoditas ->
            val intent = Intent(requireContext(), SavePredictActivity::class.java)
            intent.putExtra("prediction", hargaKomoditas)
            startActivity(intent)
        }

        normalPricesAdapter = NormalPricesAdapter { normalPrice ->
            val intent = Intent(requireContext(), SaveNormalPriceActivity::class.java)
            intent.putExtra("normalPrices", normalPrice)
            startActivity(intent)
        }

        binding.rvSave.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSave.adapter = hargaKomoditasAdapter
    }

    override fun onResume() {
        super.onResume()
        loadInitialData()
    }
}