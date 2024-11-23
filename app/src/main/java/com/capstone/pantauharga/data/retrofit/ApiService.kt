package com.capstone.pantauharga.data.retrofit

import com.capstone.pantauharga.data.response.DetailResponse
import com.capstone.pantauharga.data.response.EventResponse
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
}