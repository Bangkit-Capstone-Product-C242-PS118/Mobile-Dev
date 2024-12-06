package com.capstone.pantauharga.data.response

import com.google.gson.annotations.SerializedName

data class PredictInflationResponse(

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("predictions")
    val predictions: List<PredictionsItem>
)

data class PredictionsItem(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("value")
    val value: Int
)
