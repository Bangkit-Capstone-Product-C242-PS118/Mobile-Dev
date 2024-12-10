package com.capstone.pantauharga.data.response

import com.google.gson.annotations.SerializedName

data class InflationDataResponse(

	@field:SerializedName("data")
	val data: DataInflasi,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataInflasi(

	@field:SerializedName("tingkat_inflasi")
	val tingkatInflasi: String,

	@field:SerializedName("tanggal_inflasi")
	val tanggalInflasi: String
)
