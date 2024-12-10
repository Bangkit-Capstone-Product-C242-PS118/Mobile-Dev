package com.capstone.pantauharga.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Response(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class DataItem(

	@field:SerializedName("id_komoditas")
	val idKomoditas: Int,

	@field:SerializedName("img_url")
	val imgUrl: String,

	@field:SerializedName("nama_komoditas")
	val namaKomoditas: String
) : Parcelable
