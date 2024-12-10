package com.capstone.pantauharga.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class Inflasi(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val daerahId: String = "",
    val inflasi: String,
) : Parcelable
