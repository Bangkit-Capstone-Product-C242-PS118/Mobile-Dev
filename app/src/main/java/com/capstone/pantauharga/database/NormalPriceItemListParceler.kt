package com.capstone.pantauharga.database

import android.os.Parcel

import com.capstone.pantauharga.data.response.PricesNormalItem
import kotlinx.parcelize.Parceler

object NormalPriceItemListParceler : Parceler<List<PricesNormalItem>> {
    override fun create(parcel: Parcel): List<PricesNormalItem> {
        return mutableListOf<PricesNormalItem>().apply {
            parcel.readList(this, PricesNormalItem::class.java.classLoader)
        }
    }

    override fun List<PricesNormalItem>.write(parcel: Parcel, flags: Int) {
        parcel.writeList(this)
    }
}
