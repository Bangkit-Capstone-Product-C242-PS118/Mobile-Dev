package com.capstone.pantauharga.data.response

import com.google.gson.annotations.SerializedName

data class TesResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItem>
)

data class DataItem(

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String
)
