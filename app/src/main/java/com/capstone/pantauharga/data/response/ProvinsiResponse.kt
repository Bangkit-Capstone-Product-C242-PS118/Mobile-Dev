package com.capstone.pantauharga.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ProvinsiResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listProvinces")
    val listProvinces: List<ListProvincesItem>
)

@Parcelize
data class ListProvincesItem(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
) : Parcelable
