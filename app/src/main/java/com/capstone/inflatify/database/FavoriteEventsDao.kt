package com.capstone.inflatify.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteEventsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: FavoriteEvents)

    @Delete
    suspend fun delete(event: FavoriteEvents)

    @Query("SELECT * FROM FavoriteEvents WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvents?>

    @Query("SELECT * FROM FavoriteEvents")
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvents>>

}