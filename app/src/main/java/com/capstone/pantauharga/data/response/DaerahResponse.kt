package com.capstone.pantauharga.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DaerahResponse(

	@field:SerializedName("data")
	val data: List<DataItemDaerah>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class DataItemDaerah(

	@field:SerializedName("nama_daerah")
	val namaDaerah: String,

	@field:SerializedName("img_url")
	val img: String,

	@field:SerializedName("daerah_id")
	val daerahId: Int
) : Parcelable
