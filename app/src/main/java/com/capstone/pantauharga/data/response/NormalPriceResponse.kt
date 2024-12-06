package com.capstone.pantauharga.data.response

import com.google.gson.annotations.SerializedName

data class NormalPriceResponse(

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("prices")
    val prices: List<PricesItem>
)

data class PricesItem(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("value")
    val value: Int
)
