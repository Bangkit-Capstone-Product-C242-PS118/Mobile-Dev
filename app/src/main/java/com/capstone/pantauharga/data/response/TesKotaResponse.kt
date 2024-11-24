package com.capstone.pantauharga.data.response

import com.google.gson.annotations.SerializedName

data class TesKotaResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItemProvinsi>
)

data class DataItemProvinsi(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String
)
