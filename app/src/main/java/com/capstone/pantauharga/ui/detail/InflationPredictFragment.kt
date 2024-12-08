package com.capstone.pantauharga.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.capstone.pantauharga.data.response.DataModel
import com.capstone.pantauharga.data.response.PredictionsItem
import com.capstone.pantauharga.databinding.FragmentInflationPredictBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.google.gson.Gson

class InflationPredictFragment : Fragment() {

    private lateinit var binding: FragmentInflationPredictBinding
    private val viewModel: DetailViewModel by activityViewModels()
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

        setupChart()

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.inflationData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                displayChart(it.predictions)
                binding.tvDescription.text = it.description
            }
        })

        binding.btn1m.setOnClickListener { fetchInflationData(30) }
        binding.btn3m.setOnClickListener { fetchInflationData(90) }
        binding.btn6m.setOnClickListener { fetchInflationData(180) }
        binding.btn9m.setOnClickListener { fetchInflationData(270) }
        binding.btn1y.setOnClickListener { fetchInflationData(360) }

        fetchInflationData(30)
    }

    private fun fetchInflationData(timeRange: Int) {
        viewModel.fetchInflationPrediction(commodityId, provinceId, timeRange)
    }

    private fun displayChart(predictions: List<PredictionsItem>) {
        val entries = predictions.mapIndexed { index, item -> Entry(index.toFloat(), item.value.toFloat()) }
        val labels = predictions.map { it.date }

        val dataSet = LineDataSet(entries, "Inflasi").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            setDrawCircles(true)
            setDrawFilled(true)
        }

        binding.chartInflation.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.chartInflation.data = LineData(dataSet)
        binding.chartInflation.invalidate()
    }

    private fun setupChart() {
        with(binding.chartInflation) {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)

            xAxis.apply {
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