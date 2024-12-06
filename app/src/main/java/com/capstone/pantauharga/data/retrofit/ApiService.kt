package com.capstone.pantauharga.data.retrofit

import com.capstone.pantauharga.data.response.DetailResponse
import com.capstone.pantauharga.data.response.EventResponse
import com.capstone.pantauharga.data.response.KomoditasResponse
import com.capstone.pantauharga.data.response.NormalPriceResponse
import com.capstone.pantauharga.data.response.PredictInflationResponse
import com.capstone.pantauharga.data.response.ProvinsiResponse
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