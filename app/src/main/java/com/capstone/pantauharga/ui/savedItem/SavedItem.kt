package com.capstone.pantauharga.ui.savedItem

import com.capstone.pantauharga.database.HargaKomoditas
import com.capstone.pantauharga.database.NormalPrice

sealed class SavedItem {
    data class HargaKomoditasItem(val data: HargaKomoditas) : SavedItem()
    data class NormalPricesItem(val data: NormalPrice) : SavedItem()
}