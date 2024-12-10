package com.capstone.pantauharga.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface NormalPriceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNormalPrice(NormalPrice: NormalPrice)

    @Query("SELECT * FROM NormalPrice WHERE id = :id")
    fun getNormalPriceById(id: Int): LiveData<NormalPrice?>

    @Query("SELECT * FROM NormalPrice WHERE commodityName = :commodityName AND provinceName = :provinceName")
    fun getNormalPriceByCommodityAndProvince(commodityName: String, provinceName: String): LiveData<NormalPrice?>

    @Query("SELECT * FROM NormalPrice WHERE timeRange = :timeRange")
    fun getNormalPricesByTimeRange(timeRange: Int): LiveData<List<NormalPrice>>

    @Query("SELECT * FROM NormalPrice WHERE commodityName = :commodityName AND provinceName = :provinceName AND timeRange = :timeRange")
    fun getWaktu(commodityName: String, provinceName: String, timeRange: Int): LiveData<NormalPrice?>

    @Query("SELECT DISTINCT commodityName FROM NormalPrice")
    fun getAllCommodityNamesWaktu(): LiveData<List<String>>

    @Query("SELECT DISTINCT provinceName FROM NormalPrice")
    fun getAllProvinceNamesWaktu(): LiveData<List<String>>


    @Query("DELETE FROM NormalPrice WHERE commodityName = :commodityName AND provinceName = :provinceName")
    suspend fun deleteNormalByCommodityAndProvince(commodityName: String, provinceName: String)

    @Query("SELECT * FROM NormalPrice")
    fun getAllNormalPrice(): LiveData<List<NormalPrice>>

    @Delete
    suspend fun deleteNormalPrice(normalPrice: NormalPrice)

}
