package com.capstone.pantauharga.ui.savedItem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.capstone.pantauharga.R
import com.capstone.pantauharga.data.response.PricesKomoditasItem
import com.capstone.pantauharga.data.response.PricesNormalItem
import com.capstone.pantauharga.data.retrofit.ApiConfig
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.database.HargaKomoditas
import com.capstone.pantauharga.database.NormalPrice
import com.capstone.pantauharga.databinding.ActivitySaveNormalPriceBinding
import com.capstone.pantauharga.databinding.ActivitySavePredictBinding
import com.capstone.pantauharga.repository.PredictInflationRepository
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class SaveNormalPriceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaveNormalPriceBinding
    private lateinit var detailDataEntity: NormalPrice
    private lateinit var viewModel: SaveViewModel
    private var isBookmarked: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveNormalPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        detailDataEntity = intent.getParcelableExtra("normalPrices")!!

        val apiService = ApiConfig.getApiService()
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = PredictInflationRepository(apiService, database)
        val factory = SaveViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SaveViewModel::class.java]

        setupUI()
        setupBookmarkButton()

        displayChart(detailDataEntity.normalPrice)
        updateChartForTimeRange(11)
        setupChart()
        setupTimeRangeButtons()
    }

    private fun setupUI() {
        binding.tvComodityName.text = detailDataEntity.commodityName
        binding.tvDescription.text = detailDataEntity.description
        binding.tvLocation.text = detailDataEntity.provinceName
    }

    private fun displayChart(predictions: List<PricesNormalItem>) {
        val entries = predictions.mapIndexed { index, item ->
            Entry(index.toFloat(), item.hargaNormal.toFloat())
        }
        val labels = predictions.map { it.tanggalHarga }

        val dataSet = LineDataSet(entries, "Harga Normal").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            setDrawCircles(true)
            setDrawFilled(true)
        }

        binding.chartInflation.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.chartInflation.data = LineData(dataSet)
        binding.chartInflation.invalidate()
    }

    private fun setupBookmarkButton() {
        updateBookmarkIcon(isBookmarked)
        binding.btnBookmark.setOnClickListener {
            if (isBookmarked) {
                viewModel.deleteNormalByCommodityAndProvince(detailDataEntity.commodityName, detailDataEntity.provinceName)
                Toast.makeText(this@SaveNormalPriceActivity, "Normal Prices Data removed from bookmarks", Toast.LENGTH_SHORT).show()
                isBookmarked = false
            }
            else {
                isBookmarked = true
            }
            updateBookmarkIcon(isBookmarked) }
    }

    private fun updateBookmarkIcon(isBookmarked: Boolean) {
        if (isBookmarked) {
            binding.btnBookmark.setImageResource(R.drawable.save_full)
        }
        else {
            binding.btnBookmark.setImageResource(R.drawable.save)
        }
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

    private fun setupTimeRangeButtons() {
        binding.btn1m.setOnClickListener {
            updateChartForTimeRange(11)
        }

        binding.btn3m.setOnClickListener {
            updateChartForTimeRange(23)
        }

        binding.btn6m.setOnClickListener {
            updateChartForTimeRange(35)
        }

        binding.btn9m.setOnClickListener {
            updateChartForTimeRange(47)
        }

        binding.btn1y.setOnClickListener {
            updateChartForTimeRange(59)
        }
    }

    private fun updateChartForTimeRange(timeRange: Int) {
        val komoditasid = detailDataEntity.commodityName
        val provinsiId = detailDataEntity.provinceName

        Log.d("SavePredictActivity", "Button clicked: timeRange = $timeRange")

        viewModel.getWaktuNormal(komoditasid, provinsiId, timeRange).observe(this) { hargaKomoditas ->
            if (hargaKomoditas?.normalPrice != null) {
                Log.d("SavePredictActivity", "Data received: ${hargaKomoditas.normalPrice}")
                displayChart(hargaKomoditas.normalPrice)
            } else {
                Log.d("SavePredictActivity", "No data received or predictions is null")
            }
        }
    }
}