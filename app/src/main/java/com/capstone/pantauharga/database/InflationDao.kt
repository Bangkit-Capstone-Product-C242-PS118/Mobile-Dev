package com.capstone.pantauharga.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InflationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInflationData(inflasi: Inflasi)

    @Query("SELECT * FROM Inflasi WHERE id = :id")
    fun getInflationDataById(id: Int): LiveData<Inflasi?>


    @Query("SELECT * FROM Inflasi")
    fun getAllNormalPrice(): LiveData<List<Inflasi>>

    @Delete
    suspend fun deleteNormalPrice(inflasi: Inflasi)
}