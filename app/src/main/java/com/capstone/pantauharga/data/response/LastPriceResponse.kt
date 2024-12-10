package com.capstone.pantauharga.data.response

import com.google.gson.annotations.SerializedName

data class LastPriceResponse(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("last_price")
	val lastPrice: LastPrice
)

data class LastPrice(

	@field:SerializedName("komoditas_id")
	val komoditasId: Int,

	@field:SerializedName("harga")
	val harga: String,

	@field:SerializedName("price_id")
	val priceId: Int,

	@field:SerializedName("daerah_id")
	val daerahId: Int,

	@field:SerializedName("tanggal_harga")
	val tanggalHarga: String
)
