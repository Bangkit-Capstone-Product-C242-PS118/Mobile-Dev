package com.capstone.pantauharga.data.retrofit

import com.capstone.pantauharga.data.response.DetailResponse
import com.capstone.pantauharga.data.response.EventResponse
import com.capstone.pantauharga.data.response.KomoditasResponse
import com.capstone.pantauharga.data.response.NormalPriceResponse
import com.capstone.pantauharga.data.response.PredictInflationResponse
import com.capstone.pantauharga.data.response.ProvinsiResponse
import com.capstone.pantauharga.data.response.TesKotaResponse
import com.capstone.pantauharga.data.response.TesResponse
import retrofit2.http.*

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int,
        @Query("limit") limit: Int = 30
    ): EventResponse

    @GET("events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: String
    ): DetailResponse

    @GET("2f518dc2-4bde-46da-9a9c-766f8459207f")
    suspend fun getKota(): TesKotaResponse

    @GET("7362b6e2-0be4-4580-a6c9-201683c1d74d")
    suspend fun getKomoditas(): TesResponse

    @GET("commodities")
    suspend fun getCommodities(): KomoditasResponse

    @GET("commodities/{commodityId}/provinces")
    suspend fun getProvincesByCommodity(
        @Path("commodityId") commodityId: String
    ): ProvinsiResponse

    @GET("commodities/{commodityId}/provinces/{provinceId}/inflation-predictions")
    suspend fun getInflationPredictions(
        @Path("commodityId") commodityId: String,
        @Path("provinceId") provinceId: String,
        @Query("timeRange") timeRange: Int
    ): PredictInflationResponse

    @GET("commodities/{commodityId}/provinces/{provinceId}/normal-prices")
    suspend fun getNormalPrices(
        @Path("commodityId") commodityId: String,
        @Path("provinceId") provinceId: String,
    ): NormalPriceResponse
}