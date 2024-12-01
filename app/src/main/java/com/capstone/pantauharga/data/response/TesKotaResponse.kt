package com.capstone.pantauharga.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TesKotaResponse(

	@field:SerializedName("data")
	val data: List<DataItemProvinsi>
)

@Parcelize
data class DataItemProvinsi(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String
) : Parcelable
