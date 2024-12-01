package com.capstone.pantauharga.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.pantauharga.data.response.DataModel
import com.capstone.pantauharga.databinding.FragmentInflationPredictBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis

class InflationPredictFragment : Fragment() {

    private lateinit var binding: FragmentInflationPredictBinding
    private val dataByRange = mutableMapOf<String, List<DataModel>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInflationPredictBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupChart()

        dataByRange["1M"] = listOf(DataModel("11/2024", 193000f), DataModel("12/2024", 204000f))
        dataByRange["3M"] = listOf(
            DataModel("9/2024", 118000f),
            DataModel("10/2024", 160000f),
            DataModel("11/2024", 193000f)
        )
        dataByRange["6M"] = listOf(
            DataModel("6/2024", 170000f),
            DataModel("7/2024", 142000f),
            DataModel("8/2024", 210000f),
            DataModel("9/2024", 118000f),
            DataModel("10/2024", 160000f),
            DataModel("11/2024", 193000f)
        )

        binding.btn1m.setOnClickListener { updateChart("1M") }
        binding.btn3m.setOnClickListener { updateChart("3M") }
        binding.btn6m.setOnClickListener { updateChart("6M") }

       //default chartnya
        updateChart("1M")
    }

    private fun updateChart(range: String) {
        val data = dataByRange[range] ?: emptyList()
        displayChart(data)

        val description = when (range) {
            "1M" -> "Ini inflasi bulan 1"
            "3M" -> "Ini inflasi bulan 3"
            "6M" -> "Ini inflasi bulan 6"
            else -> "Tidak ada data untuk periode ini."
        }
        binding.tvDescription.text = description
    }

    private fun displayChart(data: List<DataModel>) {
        val entries = data.mapIndexed { index, item -> Entry(index.toFloat(), item.value) }
        val labels = data.map { it.label }

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
