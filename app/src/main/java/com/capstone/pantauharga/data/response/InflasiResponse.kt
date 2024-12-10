package com.capstone.pantauharga.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class InflasiResponse(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("prices")
	val prices: List<PricesKomoditasItem>
)


@Parcelize
data class PricesKomoditasItem(

	@field:SerializedName("komoditas_id")
	val komoditasId: Int,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("price_id")
	val priceId: Int,

	@field:SerializedName("daerah_id")
	val daerahId: Int,

	@field:SerializedName("tanggal_harga")
	val tanggalHarga: String
) : Parcelable
