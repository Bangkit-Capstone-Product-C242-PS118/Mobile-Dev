package com.capstone.pantauharga.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.PricesKomoditasItem
import com.capstone.pantauharga.databinding.FragmentInflationPredictBinding
import com.capstone.pantauharga.ui.settings.SettingPreferences
import com.capstone.pantauharga.ui.settings.SettingViewModelFactory
import com.capstone.pantauharga.ui.settings.SettingsViewModel
import com.capstone.pantauharga.ui.settings.dataStore
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import java.text.NumberFormat
import java.util.Locale

class InflationPredictFragment : Fragment() {

    private lateinit var binding: FragmentInflationPredictBinding
    private val viewModel: DetailPricesViewModel by activityViewModels()
    private val viewModell: SettingsViewModel by activityViewModels { SettingViewModelFactory(
        SettingPreferences.getInstance(requireContext().dataStore)) }

    private var commodityId: String = ""
    private var provinceId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInflationPredictBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commodityId = arguments?.getString("komoditas") ?: ""
        provinceId = arguments?.getString("provinsi") ?: ""

        Log.d("InflationPredictFragment", "Commodity ID: $commodityId, Province ID: $provinceId")


        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                val progressBar = binding.progressBar
                val scrollView = binding.scrollView
                val noConnectionIcon = binding.noConnectionIcon
                val retryButton = binding.retryButton
                val tvNetwork = binding.tvNetwork

                if (isError) {
                    progressBar.visibility = View.GONE
                    scrollView.visibility = View.GONE
                    noConnectionIcon.visibility = View.VISIBLE
                    retryButton.visibility = View.VISIBLE
                    tvNetwork.visibility = View.VISIBLE

                    retryButton.setOnClickListener {
                        fetchInflationPredict(provinceId)
                        fetchInflation(provinceId)
                        fetchLastPrice(provinceId, commodityId)
                        fetchInflationData(1)
                        noConnectionIcon.visibility = View.GONE
                        retryButton.visibility = View.GONE
                        tvNetwork.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                        scrollView.visibility = View.VISIBLE
                    }
                } else {
                    noConnectionIcon.visibility = View.GONE
                    retryButton.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    tvNetwork.visibility = View.GONE
                    scrollView.visibility = View.VISIBLE
                }
            }
        }

        viewModell.getThemeSettings().observe(viewLifecycleOwner) { isDarkMode ->
            setupChart(isDarkMode)
            viewModel.hargaKomoditas.observe(viewLifecycleOwner) { data ->
                data?.let {
                    displayChart(it.prices, isDarkMode)
                }
            }
        }


        viewModel.inflationDataPredict.observe(viewLifecycleOwner) { data ->
            data?.let {
                binding.tvDescription.text = it.deskrispi
            }
        }


        fetchInflationPredict(provinceId)

        viewModel.inflationDataPredict.observe(viewLifecycleOwner) { dataPredict ->
            if (dataPredict != null) {
                Log.d("DataPredict", "Received data: ${dataPredict.prediksiInflasi}")
                binding.tvValuePrediction.visibility = View.VISIBLE
                binding.tvValuePrediction.text = dataPredict.prediksiInflasi
            } else {
                Log.d("DataPredict", "Data is null")
            }
        }


        fetchInflation(provinceId)

        viewModel.inflation.observe(viewLifecycleOwner) { dataInflation ->
            dataInflation?.let {
                binding.tvValueInflation.visibility = View.VISIBLE
                binding.tvValueInflation.text = it.tingkatInflasi
            }
        }

        fetchLastPrice(provinceId, commodityId)

        viewModel.lastPrice.observe(viewLifecycleOwner) { dataInflation ->
            dataInflation?.let {
                val value = it.harga.toDouble()
                val formattedValue =
                    NumberFormat.getNumberInstance(Locale("id", "ID")).format(value)
                binding.tvValueLastPrice.visibility = View.VISIBLE
                binding.tvValueLastPrice.text = getString(R.string.hargarupiah, formattedValue)
            }
        }


        binding.btn1m.setOnClickListener { fetchInflationData(1) }
        binding.btn3m.setOnClickListener { fetchInflationData(2) }
        binding.btn6m.setOnClickListener { fetchInflationData(3) }
        binding.btn9m.setOnClickListener { fetchInflationData(4) }
        binding.btn1y.setOnClickListener { fetchInflationData(5) }

        fetchInflationData(1)
    }

    private fun fetchInflationData(timeRange: Int) {
        viewModel.fetchInflationPrediction(commodityId, provinceId, timeRange)
    }

    private fun fetchInflationPredict(idDaerah: String){
        viewModel.fetchPredictInflation(idDaerah)
    }

    private fun fetchInflation(idDaerah: String){
        viewModel.fetchDataInflation(idDaerah)
    }

    private fun fetchLastPrice(idDaerah: String, idKomoditas: String){
        viewModel.fetchLastPrice(idDaerah, idKomoditas)
    }

    private fun displayChart(predictions: List<PricesKomoditasItem>, isDarkMode: Boolean) {
        val textColor = if (isDarkMode) {
            ResourcesCompat.getColor(resources, R.color.white, null)
        } else {
            ResourcesCompat.getColor(resources, R.color.black, null)
        }

        val entries = predictions.mapIndexed { index, item -> Entry(index.toFloat(), item.harga.toFloat()) }
        val labels = predictions.map { it.tanggalHarga }

        val dataSet = LineDataSet(entries, "Price").apply {
            color = Color.BLUE
            valueTextColor = textColor
            setDrawCircles(true)
            setDrawFilled(true)
        }

        binding.chartInflation.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.chartInflation.data = LineData(dataSet)
        binding.chartInflation.invalidate()
    }


    private fun setupChart(isDarkMode: Boolean) {
        val textColor = if (isDarkMode) {
            ResourcesCompat.getColor(resources, R.color.white, null)
        } else {
            ResourcesCompat.getColor(resources, R.color.black, null)
        }

        with(binding.chartInflation) {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)

            xAxis.apply {
                labelRotationAngle = -45f
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawGridLines(false)
                this.textColor = textColor
            }

            axisRight.apply {
                isEnabled = true
                setDrawLabels(true)
                setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
                this.textColor = textColor
            }

            axisLeft.apply {
                isEnabled = false
                this.textColor = textColor
            }

            legend.textColor = textColor
        }
    }
}