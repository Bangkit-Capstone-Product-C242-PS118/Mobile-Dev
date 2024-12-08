package com.capstone.pantauharga.ui.savedItem

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.PredictionsItem
import com.capstone.pantauharga.database.PredictInflation

import com.capstone.pantauharga.databinding.ActivitySavePredictBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class SavePredictActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavePredictBinding
    private lateinit var detailDataEntity: PredictInflation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavePredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        detailDataEntity = intent.getParcelableExtra("prediction")!!

        setupUI()

        displayChart(detailDataEntity.predictions)
        setupChart()
    }

    private fun setupUI() {
        binding.tvComodityName.text = detailDataEntity.commodityName
        binding.tvDescription.text = detailDataEntity.description
        binding.tvLocation.text = detailDataEntity.provinceName
    }

    private fun displayChart(predictions: List<PredictionsItem>) {
        val entries = predictions.mapIndexed { index, item ->
            Entry(index.toFloat(), item.value.toFloat())
        }
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