package com.capstone.pantauharga.database

import android.os.Parcel
import com.capstone.pantauharga.data.response.PredictionsItem
import kotlinx.parcelize.Parceler

object PredictionsItemListParceler : Parceler<List<PredictionsItem>> {
    override fun create(parcel: Parcel): List<PredictionsItem> {
        return mutableListOf<PredictionsItem>().apply {
            parcel.readList(this, PredictionsItem::class.java.classLoader)
        }
    }

    override fun List<PredictionsItem>.write(parcel: Parcel, flags: Int) {
        parcel.writeList(this)
    }
}
