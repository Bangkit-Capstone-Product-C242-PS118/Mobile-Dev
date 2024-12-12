package com.capstone.pantauharga.data.retrofit

import com.capstone.pantauharga.data.response.DaerahResponse
import com.capstone.pantauharga.data.response.EventResponse
import com.capstone.pantauharga.data.response.HargaNormalResponse
import com.capstone.pantauharga.data.response.InflasiResponse
import com.capstone.pantauharga.data.response.InflationDataResponse
import com.capstone.pantauharga.data.response.LastPriceResponse
import com.capstone.pantauharga.data.response.PredictInflationDataResponse
import com.capstone.pantauharga.data.response.Response
import retrofit2.http.*

interface ApiService {

    @GET("komoditas")
    suspend fun getCommodities(): Response


    @GET("daerah")
    suspend fun getProvincesByCommodity(
        @Query("commodityId") commodityId: String
    ): DaerahResponse

    @GET("harga_komoditas/{id_komoditas}/{id_daerah}")
    suspend fun getHargaKomoditas(
        @Path("id_daerah") idDaerah: String,
        @Path("id_komoditas") idKomoditas: String,
        @Query("timeRange") timeRange: Int
    ): InflasiResponse

    @GET("harga_normal/{id_komoditas}/{id_daerah}")
    suspend fun getHargaNormal(
        @Path("id_daerah") idDaerah: String,
        @Path("id_komoditas") idKomoditas: String,
        @Query("timeRange") timeRange: Int
    ): HargaNormalResponse

    @GET("prediksi/{id_daerah}")
    suspend fun getPredictInflation(
        @Path("id_daerah") idDaerah: String
    ): PredictInflationDataResponse

    @GET("/harga_komoditas/last/{id_daerah}/{id_komoditas}")
    suspend fun getLastPrice(
        @Path("id_daerah") idDaerah: String,
        @Path("id_komoditas") idKomoditas: String
    ): LastPriceResponse


    @GET("inflasi/{id_daerah}")
    suspend fun getDataInflation(
        @Path("id_daerah") idDaerah: String
    ): InflationDataResponse

}