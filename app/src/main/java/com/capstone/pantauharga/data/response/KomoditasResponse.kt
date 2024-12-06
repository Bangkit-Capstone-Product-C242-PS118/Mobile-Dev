package com.capstone.pantauharga.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class KomoditasResponse(

    @field:SerializedName("listCommodities")
    val listCommodities: List<ListCommoditiesItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class ListCommoditiesItem(

    @field:SerializedName("img")
    val img: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String
) : Parcelable
