package com.capstone.pantauharga.ui.detail

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.pantauharga.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class DetailComodityActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_comodity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        lineChart = findViewById(R.id.chart_inflation)
        setupChart()

        val data = listOf(
            DataModel("1/2024", 95000f),
            DataModel("2/2024", 123000f),
            DataModel("3/2024", 145000f),
            DataModel("4/2024", 98000f),
            DataModel("5/2024", 155000f),
            DataModel("6/2024", 170000f),
            DataModel("7/2024", 142000f),
            DataModel("8/2024", 210000f),
            DataModel("9/2024", 118000f),
            DataModel("10/2024", 160000f),
            DataModel("11/2024", 193000f),
            DataModel("12/2024", 204000f),
            DataModel("1/2025", 110000f),
            DataModel("2/2025", 122500f),
            DataModel("3/2025", 137000f),
            DataModel("4/2025", 168000f),
            DataModel("5/2025", 145500f),
            DataModel("6/2025", 153000f),
            DataModel("7/2025", 176000f),
            DataModel("8/2025", 189000f),
            DataModel("9/2025", 132000f),
            DataModel("10/2025", 150500f),
            DataModel("11/2025", 165000f),
            DataModel("12/2025", 185000f)
        )

        displayChart(data)
    }

    private fun setupChart() {
        // BUAT DYNAMIC KETIKA DARK MODE
        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        // Enable right Y-axis
        val yAxisRight = lineChart.axisRight
        yAxisRight.isEnabled = true  // Enable the right Y-axis
        yAxisRight.setDrawLabels(true)  // Draw the labels

        // Move labels to the right
        yAxisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)  // Correct way to position labels on the right side

        // Optionally disable the left Y-axis
        lineChart.axisLeft.isEnabled = false
    }

    private fun displayChart(data: List<DataModel>) {
        val entries = ArrayList<Entry>()
        val labels = ArrayList<String>()

        for ((index, item) in data.withIndex()) {
            entries.add(Entry(index.toFloat(), item.price))
            labels.add(item.date)
        }

        val dataSet = LineDataSet(entries, "Harga per Bulan")

        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK

        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)

        val lineData = LineData(dataSet)

        lineChart.data = lineData

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        lineChart.invalidate()
    }

    data class DataModel(val date: String, val price: Float)
}
