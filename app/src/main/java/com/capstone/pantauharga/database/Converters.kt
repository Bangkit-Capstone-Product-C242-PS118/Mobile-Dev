package com.capstone.pantauharga.database

import androidx.room.TypeConverter

import com.capstone.pantauharga.data.response.PricesKomoditasItem
import com.capstone.pantauharga.data.response.PricesNormalItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromPricesKomoditasItemList(value: List<PricesKomoditasItem>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PricesKomoditasItem>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPricesKomoditasItemList(value: String): List<PricesKomoditasItem> {
        val gson = Gson()
        val type = object : TypeToken<List<PricesKomoditasItem>>() {}.type
        return gson.fromJson(value, type)
    }



    @TypeConverter
    fun fromPricesNormalItemList(value: List<PricesNormalItem>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPricesNormalItemList(value: String): List<PricesNormalItem> {
        val listType = object : TypeToken<List<PricesNormalItem>>() {}.type
        return Gson().fromJson(value, listType)
    }
}