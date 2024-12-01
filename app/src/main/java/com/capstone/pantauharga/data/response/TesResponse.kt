package com.capstone.pantauharga.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TesResponse(

	@field:SerializedName("data")
	val data: List<DataItem>
)

@Parcelize
data class DataItem(

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String
) : Parcelable
