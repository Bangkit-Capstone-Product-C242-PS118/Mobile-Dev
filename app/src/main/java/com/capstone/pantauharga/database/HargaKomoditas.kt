package com.capstone.pantauharga.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.capstone.pantauharga.data.response.PricesKomoditasItem
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.WriteWith

@Entity
@Parcelize
data class HargaKomoditas(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val komoditasId: String = "",
    val daerahId: String = "",
    val description: String,
    val predictions: @WriteWith<PredictionsItemListParceler>() List<PricesKomoditasItem>,
    val commodityName: String,
    val provinceName: String,
    val timeRange: Int
) : Parcelable

