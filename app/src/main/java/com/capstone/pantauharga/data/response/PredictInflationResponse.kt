package com.capstone.pantauharga.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class PredictionsItem(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("value")
    val value: Int
) : Parcelable
