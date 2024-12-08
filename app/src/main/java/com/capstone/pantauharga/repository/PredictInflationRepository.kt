package com.capstone.pantauharga.repository

import androidx.lifecycle.LiveData
import com.capstone.pantauharga.data.response.NormalPriceResponse
import com.capstone.pantauharga.data.response.PredictInflationResponse
import com.capstone.pantauharga.data.response.PredictionsItem
import com.capstone.pantauharga.data.retrofit.ApiService
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.database.PredictInflation
import com.capstone.pantauharga.database.PredictInflationDao

class PredictInflationRepository(
    private val apiService: ApiService,
    private val database: AppDatabase
) {
    fun getPredictionById(id: Int): LiveData<PredictInflation?> {
        return database.predictInflationDao().getPredictionById(id)
    }

    fun getAllPrediction(): LiveData<List<PredictInflation>> {
        return database.predictInflationDao().getAllPredictions()
    }

    suspend fun savePrediction(
        description: String,
        predictions: List<PredictionsItem>,
        commodityName: String,
        provinceName: String
    ) {
        val predictionEntity = PredictInflation(
            description = description,
            predictions = predictions,
            commodityName = commodityName,
            provinceName = provinceName
        )
        database.predictInflationDao().insertPrediction(predictionEntity)
    }

    suspend fun deletePrediction(prediction: PredictInflation) {
        database.predictInflationDao().deletePrediction(prediction)
    }

    suspend fun getInflationPredictions(
        commodityId: String,
        provinceId: String,
        timeRange: Int
    ): PredictInflationResponse {
        return apiService.getInflationPredictions(commodityId, provinceId, timeRange)
    }

    suspend fun getNormalPrices(
        commodityId: String,
        provinceId: String
    ): NormalPriceResponse {
        return apiService.getNormalPrices(commodityId, provinceId)
    }
}