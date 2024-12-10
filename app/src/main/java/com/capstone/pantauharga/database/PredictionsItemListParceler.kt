package com.capstone.pantauharga.database

import android.os.Parcel

import com.capstone.pantauharga.data.response.PricesKomoditasItem
import kotlinx.parcelize.Parceler

object PredictionsItemListParceler : Parceler<List<PricesKomoditasItem>> {
    override fun create(parcel: Parcel): List<PricesKomoditasItem> {
        return mutableListOf<PricesKomoditasItem>().apply {
            parcel.readList(this, PricesKomoditasItem::class.java.classLoader)
        }
    }

    override fun List<PricesKomoditasItem>.write(parcel: Parcel, flags: Int) {
        parcel.writeList(this)
    }
}
