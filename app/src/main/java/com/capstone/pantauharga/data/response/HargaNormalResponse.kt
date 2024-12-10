package com.capstone.pantauharga.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HargaNormalResponse(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("prices")
	val prices: List<PricesNormalItem>
)

@Parcelize
data class PricesNormalItem(

	@field:SerializedName("Harga_Normal")
	val hargaNormal: Int,

	@field:SerializedName("tanggal_harga")
	val tanggalHarga: String
) : Parcelable
