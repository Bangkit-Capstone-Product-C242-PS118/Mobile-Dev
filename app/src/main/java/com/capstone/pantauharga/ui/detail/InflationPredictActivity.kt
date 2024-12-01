package com.capstone.pantauharga.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.DataModel
import com.capstone.pantauharga.databinding.ActivityDetailComodityBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

//class InflationPredictActivity : AppCompatActivity() {
//    private lateinit var lineChart: LineChart
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_detail_comodity)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        supportActionBar?.hide()
//
//        lineChart = findViewById(R.id.chart_inflation)
//        setupChart()
//
//        val data = listOf(
//            DataModel("1/2024", 95000f),
//            DataModel("2/2024", 123000f),
//            DataModel("3/2024", 145000f),
//            DataModel("4/2024", 98000f),
//            DataModel("5/2024", 155000f),
//            DataModel("6/2024", 170000f),
//            DataModel("7/2024", 142000f),
//            DataModel("8/2024", 210000f),
//            DataModel("9/2024", 118000f),
//            DataModel("10/2024", 160000f),
//            DataModel("11/2024", 193000f),
//            DataModel("12/2024", 204000f),
//            DataModel("1/2025", 110000f),
//            DataModel("2/2025", 122500f),
//            DataModel("3/2025", 137000f),
//            DataModel("4/2025", 168000f),
//            DataModel("5/2025", 145500f),
//            DataModel("6/2025", 153000f),
//            DataModel("7/2025", 176000f),
//            DataModel("8/2025", 189000f),
//            DataModel("9/2025", 132000f),
//            DataModel("10/2025", 150500f),
//            DataModel("11/2025", 165000f),
//            DataModel("12/2025", 185000f)
//        )
//
//        displayChart(data)
//    }
//
//    private fun setupChart() {
//        // BUAT DYNAMIC KETIKA DARK MODE
//        lineChart.description.isEnabled = false
//        lineChart.setTouchEnabled(true)
//        lineChart.isDragEnabled = true
//        lineChart.setScaleEnabled(true)
//        lineChart.setPinchZoom(true)
//
//        val xAxis = lineChart.xAxis
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.granularity = 1f
//        xAxis.setDrawGridLines(false)
//
//        // Enable right Y-axis
//        val yAxisRight = lineChart.axisRight
//        yAxisRight.isEnabled = true  // Enable the right Y-axis
//        yAxisRight.setDrawLabels(true)  // Draw the labels
//
//        // Move labels to the right
//        yAxisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)  // Correct way to position labels on the right side
//
//        // Optionally disable the left Y-axis
//        lineChart.axisLeft.isEnabled = false
//    }
//
//    private fun displayChart(data: List<DataModel>) {
//        val entries = ArrayList<Entry>()
//        val labels = ArrayList<String>()
//
//        for ((index, item) in data.withIndex()) {
//            entries.add(Entry(index.toFloat(), item.price))
//            labels.add(item.date)
//        }
//
//        val dataSet = LineDataSet(entries, "Harga per Bulan")
//
//        dataSet.color = Color.BLUE
//        dataSet.valueTextColor = Color.BLACK
//
//        dataSet.setDrawCircles(false)
//        dataSet.setDrawValues(false)
//
//        val lineData = LineData(dataSet)
//
//        lineChart.data = lineData
//
//        val xAxis = lineChart.xAxis
//        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
//
//        lineChart.invalidate()
//    }
//
//    data class DataModel(val date: String, val price: Float)
//}

//class InflationPredictActivity : AppCompatActivity() {
//    private lateinit var lineChart: LineChart
//    private lateinit var descriptionTextView: TextView
//    private val dataByRange = mutableMapOf<String, List<DataModel>>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail_comodity)
//
//        supportActionBar?.hide()
//
//        lineChart = findViewById(R.id.chart_inflation)
//        descriptionTextView = findViewById(R.id.tv_description)
//
//        setupChart()
//
//        dataByRange["1M"] = listOf(DataModel("11/2024", 193000f), DataModel("12/2024", 204000f))
//        dataByRange["3M"] = listOf(
//            DataModel("9/2024", 118000f),
//            DataModel("10/2024", 160000f),
//            DataModel("11/2024", 193000f)
//        )
//        dataByRange["6M"] = listOf(
//            DataModel("6/2024", 170000f),
//            DataModel("7/2024", 142000f),
//            DataModel("8/2024", 210000f),
//            DataModel("9/2024", 118000f),
//            DataModel("10/2024", 160000f),
//            DataModel("11/2024", 193000f)
//        )
//
//        findViewById<Button>(R.id.btn_1m).setOnClickListener { updateChart("1M") }
//        findViewById<Button>(R.id.btn_3m).setOnClickListener { updateChart("3M") }
//        findViewById<Button>(R.id.btn_6m).setOnClickListener { updateChart("6M") }
//        findViewById<Button>(R.id.btn_9M).setOnClickListener { updateChart("9M") }
//        findViewById<Button>(R.id.btn_1y).setOnClickListener { updateChart("1Y") }
//
//       //default
//        updateChart("1M")
//    }
//
//    private fun updateChart(range: String) {
//        val data = dataByRange[range] ?: emptyList()
//        displayChart(data)
//
//       //nanti ambil dari gemini
//        val description = when (range) {
//            "1M" -> "Ini inflasi bulan 1"
//            "3M" -> "Ini inflasi bulan 3"
//            "6M" -> "Ini inflasi bulan 6"
//            "9M" -> "Ini inflasi bulan 9"
//            "1Y" -> "Ini inflasi 1 tahun"
//            else -> "Tidak ada data untuk periode ini."
//        }
//        descriptionTextView.text = description
//    }
//
//    private fun displayChart(data: List<DataModel>) {
//        val entries = data.mapIndexed { index, item -> Entry(index.toFloat(), item.value) }
//        val labels = data.map { it.label }
//
//        val dataSet = LineDataSet(entries, "Inflasi").apply {
//            color = Color.BLUE
//            valueTextColor = Color.BLACK
//            setDrawCircles(true)
//            setDrawFilled(true)
//        }
//
//        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
//        lineChart.data = LineData(dataSet)
//        lineChart.invalidate()
//    }
//
//    private fun setupChart() {
//        lineChart.description.isEnabled = false
//        lineChart.setTouchEnabled(true)
//        lineChart.isDragEnabled = true
//        lineChart.setScaleEnabled(true)
//        lineChart.setPinchZoom(true)
//
//        val xAxis = lineChart.xAxis
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.granularity = 1f
//        xAxis.setDrawGridLines(false)
//
//        val yAxisRight = lineChart.axisRight
//        yAxisRight.isEnabled = true
//        yAxisRight.setDrawLabels(true)
//        yAxisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
//
//        lineChart.axisLeft.isEnabled = false
//    }
//
//}

class InflationPredictActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailComodityBinding
    private val dataByRange = mutableMapOf<String, List<DataModel>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailComodityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

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


