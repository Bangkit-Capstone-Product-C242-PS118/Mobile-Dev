package com.capstone.pantauharga.repository

import androidx.lifecycle.LiveData
import com.capstone.pantauharga.data.response.HargaNormalResponse
import com.capstone.pantauharga.data.response.InflasiResponse
import com.capstone.pantauharga.data.response.InflationDataResponse
import com.capstone.pantauharga.data.response.LastPriceResponse
import com.capstone.pantauharga.data.response.PredictInflationDataResponse
import com.capstone.pantauharga.data.response.PricesKomoditasItem
import com.capstone.pantauharga.data.response.PricesNormalItem
import com.capstone.pantauharga.data.retrofit.ApiService
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.database.NormalPrice
import com.capstone.pantauharga.database.HargaKomoditas
import com.capstone.pantauharga.database.Inflasi

class PredictInflationRepository(
    private val apiService: ApiService,
    private val database: AppDatabase
) {

    suspend fun deleteByCommodityAndProvince(commodityName: String, provinceName: String) {
        return database.predictInflationDao().deleteByCommodityAndProvince(commodityName, provinceName)
    }

    suspend fun deleteNormalByCommodityAndProvince(commodityName: String, provinceName: String) {
        return database.normalPriceDao().deleteNormalByCommodityAndProvince(commodityName, provinceName)
    }




    fun getKomoditasByTimeRange(timeRange: Int): LiveData<List<HargaKomoditas>> {
        return database.predictInflationDao().getHargaKomoditasByTimeRange(timeRange)
    }

    fun getWaktu(komoditasId: String, daerahId: String, timeRange: Int): LiveData<HargaKomoditas?> {
        return database.predictInflationDao().getWaktu(komoditasId, daerahId, timeRange)
    }

    fun getWaktuNormal(komoditasId: String, daerahId: String, timeRange: Int): LiveData<NormalPrice?> {
        return database.normalPriceDao().getWaktu(komoditasId, daerahId, timeRange)
    }


    fun getAllCommodityNames(): LiveData<List<String>> {
        return database.predictInflationDao().getAllCommodityNames()
    }

    fun getAllProvinceNames(): LiveData<List<String>> {
        return database.predictInflationDao().getAllProvinceNames()
    }

    fun getAllCommodityNamesNormal(): LiveData<List<String>> {
        return database.normalPriceDao().getAllCommodityNamesWaktu()
    }

    fun getAllProvinceNamesNormal(): LiveData<List<String>> {
        return database.normalPriceDao().getAllProvinceNamesWaktu()
    }

    fun getPredictionById(id: Int): LiveData<HargaKomoditas?> {
        return database.predictInflationDao().getHargaKomoditasById(id)
    }

    fun getPredictionByCommodityAndProvince(commodityName: String, provinceName: String): LiveData<HargaKomoditas?>{
        return database.predictInflationDao().getHargaKomoditasCommodityAndProvince(commodityName, provinceName)
    }

    fun getAllPrediction(): LiveData<List<HargaKomoditas>> {
        return database.predictInflationDao().getAllHargaKomoditas()
    }

    suspend fun saveHargaKomoditas(
        komoditasId: String,
        daerahId: String,
        description: String,
        predictions: List<PricesKomoditasItem>,
        commodityName: String,
        provinceName: String,
        timeRange: Int
    ) {
        val predictionEntity = HargaKomoditas(
            komoditasId = komoditasId,
            daerahId = daerahId,
            description = description,
            predictions = predictions,
            commodityName = commodityName,
            provinceName = provinceName,
            timeRange = timeRange
        )
        database.predictInflationDao().insertHargaKomoditas(predictionEntity)
    }



    suspend fun deletePrediction(prediction: HargaKomoditas) {
        database.predictInflationDao().deleteHargaKomoditas(prediction)
    }

    suspend fun getHargaKomoditas(
        daerahId: String,
        komoditasId: String,
        timeRange: Int
    ): InflasiResponse {
        return apiService.getHargaKomoditas(daerahId, komoditasId, timeRange)
    }



    fun getAllNormalPrices(): LiveData<List<NormalPrice>> {
        return database.normalPriceDao().getAllNormalPrice()
    }

    fun getNormalPriceByCommodityAndProvince(commodityName: String, provinceName: String): LiveData<NormalPrice?> {
        return database.normalPriceDao().getNormalPriceByCommodityAndProvince(commodityName, provinceName)
    }

    suspend fun saveNormalPrice(
        komoditasId: String,
        daerahId: String,
        commodityName: String,
        provinceName: String,
        prices: List<PricesNormalItem>,
        description: String,
        timeRange: Int
    ) {
        val normalPriceEntity = NormalPrice(
            komoditasId = komoditasId,
            daerahId = daerahId,
            commodityName = commodityName,
            provinceName = provinceName,
            normalPrice = prices,
            description = description,
            timeRange = timeRange
        )
        database.normalPriceDao().insertNormalPrice(normalPriceEntity)
    }

    suspend fun deleteNormalPrice(normalPrice: NormalPrice) {
        database.normalPriceDao().deleteNormalPrice(normalPrice)
    }


    suspend fun getNormalPrices(
        commodityId: String,
        provinceId: String,
        timeRange: Int
    ): HargaNormalResponse {
        return apiService.getHargaNormal(commodityId, provinceId, timeRange)
    }

    suspend fun getPredictInflation(
        daerahId: String
    ) : PredictInflationDataResponse {
        return apiService.getPredictInflation(daerahId)
    }

    suspend fun saveInflasi(
        daerahId: String,
        inflasi: String
    ){
        val inflasiEntity = Inflasi(
            daerahId = daerahId,
            inflasi = inflasi
        )
        database.inflasiDao().insertInflationData(inflasiEntity)
    }

    suspend fun  getDataInflation(
        daerahId: String
    ) : InflationDataResponse {
        return apiService.getDataInflation(daerahId)
    }

    suspend fun  getLastPrice(
        daerahId: String,
        komoditasId: String
    ) : LastPriceResponse {
        return apiService.getLastPrice(daerahId, komoditasId)
    }
}