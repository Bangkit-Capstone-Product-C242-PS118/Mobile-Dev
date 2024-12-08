package com.capstone.pantauharga.database

import androidx.room.TypeConverter
import com.capstone.pantauharga.data.response.PredictionsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromPredictionsItemList(value: List<PredictionsItem>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<PredictionsItem>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPredictionsItemList(value: String?): List<PredictionsItem>? {
        val gson = Gson()
        val type = object : TypeToken<List<PredictionsItem>>() {}.type
        return gson.fromJson(value, type)
    }
}