package com.capstone.pantauharga.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.capstone.pantauharga.data.response.PredictionsItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.parcelize.WriteWith

@Entity
@Parcelize
data class PredictInflation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    val predictions: @WriteWith<PredictionsItemListParceler>() List<PredictionsItem>,
    val commodityName: String,
    val provinceName: String
) : Parcelable

