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

        lineChart = findViewById(R.id.chart_inflation)
        setupChart()

        val data = listOf(
            DataModel("9/2024", 120000f),
            DataModel("10/2024", 150000f),
            DataModel("11/2024", 180000f)
        )

        displayChart(data)
    }

    private fun setupChart() {
        lineChart.setBackgroundColor(Color.WHITE)
        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)
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
        dataSet.setCircleColor(Color.RED)

        val lineData = LineData(dataSet)

        lineChart.data = lineData

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        lineChart.invalidate()
    }

    data class DataModel(val date: String, val price: Float)
}
