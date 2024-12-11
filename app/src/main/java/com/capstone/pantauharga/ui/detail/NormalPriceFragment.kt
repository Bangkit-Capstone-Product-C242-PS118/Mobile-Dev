package com.capstone.pantauharga.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.graphics.Color
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.capstone.pantauharga.data.response.PricesNormalItem
import com.capstone.pantauharga.databinding.FragmentNormalPriceBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class NormalPriceFragment : Fragment()  {
    private lateinit var binding: FragmentNormalPriceBinding
    private val viewModel: DetailPricesViewModel by activityViewModels()
    private var commodityId: String = ""
    private var provinceId: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNormalPriceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commodityId = arguments?.getString("komoditas") ?: ""
        provinceId = arguments?.getString("provinsi") ?: ""

        Log.d("NormalPriceFragment", "Commodity ID: $commodityId, Province ID: $provinceId")

        setupChart()

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(context, "Failed to load normal price data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.normalPriceData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                displayChart(it.prices)
                binding.tvDescription.text = it.description
            }
        })

        binding.btn1m.setOnClickListener { fetchNormalPriceData(1) }
        binding.btn3m.setOnClickListener { fetchNormalPriceData(2) }
        binding.btn6m.setOnClickListener { fetchNormalPriceData(3) }
        binding.btn9m.setOnClickListener { fetchNormalPriceData(4) }
        binding.btn1y.setOnClickListener { fetchNormalPriceData(5) }


        fetchNormalPriceData(1)
    }



    private fun fetchNormalPriceData(timeRange: Int) {
        viewModel.fetchNormalPrices(commodityId, provinceId, timeRange)
    }

    private fun displayChart(prices: List<PricesNormalItem>) {
        val entries = prices.mapIndexed { index, item -> Entry(index.toFloat(), item.hargaNormal.toFloat()) }
        val labels = prices.map { it.tanggalHarga }

        val dataSet = LineDataSet(entries, "Normal Price").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            setDrawCircles(true)
            setDrawFilled(true)
        }

        binding.chartNormalPrice.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.chartNormalPrice.data = LineData(dataSet)
        binding.chartNormalPrice.invalidate()
    }

    private fun setupChart() {
        with(binding.chartNormalPrice) {
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
            }

            axisRight.apply {
                isEnabled = true
                setDrawLabels(true)
                setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            }

            axisLeft.isEnabled = false
        }
    }
}