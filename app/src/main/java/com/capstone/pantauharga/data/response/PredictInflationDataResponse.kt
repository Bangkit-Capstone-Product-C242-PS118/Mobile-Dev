package com.capstone.pantauharga.data.response

import com.google.gson.annotations.SerializedName

data class PredictInflationDataResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("Prediksi Inflasi")
	val prediksiInflasi: String,

	@field:SerializedName("deskripsi")
	val deskrispi: String
)
