package com.capstone.pantauharga.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface HargaKomoditasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHargaKomoditas(prediction: HargaKomoditas)

    @Query("SELECT * FROM HargaKomoditas WHERE id = :id")
    fun getHargaKomoditasById(id: Int): LiveData<HargaKomoditas?>

    @Query("SELECT * FROM HargaKomoditas WHERE commodityName = :commodityName AND provinceName = :provinceName")
    fun getHargaKomoditasCommodityAndProvince(commodityName: String, provinceName: String): LiveData<HargaKomoditas?>

    @Query("SELECT * FROM HargaKomoditas")
    fun getAllHargaKomoditas(): LiveData<List<HargaKomoditas>>

    @Query("SELECT DISTINCT commodityName FROM HargaKomoditas")
    fun getAllCommodityNames(): LiveData<List<String>>

    @Query("SELECT DISTINCT provinceName FROM HargaKomoditas")
    fun getAllProvinceNames(): LiveData<List<String>>


    @Query("DELETE FROM HargaKomoditas WHERE commodityName = :commodityName AND provinceName = :provinceName")
    suspend fun deleteByCommodityAndProvince(commodityName: String, provinceName: String)



    @Query("SELECT * FROM HargaKomoditas WHERE commodityName = :commodityName AND provinceName = :provinceName AND timeRange = :timeRange")
    fun getWaktu(commodityName: String, provinceName: String, timeRange: Int): LiveData<HargaKomoditas?>

    @Query("SELECT * FROM HargaKomoditas WHERE timeRange = :timeRange")
    fun getHargaKomoditasByTimeRange(timeRange: Int): LiveData<List<HargaKomoditas>>

    @Delete
    suspend fun deleteHargaKomoditas(prediction: HargaKomoditas)

}
