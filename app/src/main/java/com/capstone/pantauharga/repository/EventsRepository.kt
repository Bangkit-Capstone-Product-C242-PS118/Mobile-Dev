package com.capstone.pantauharga.repository

import androidx.lifecycle.LiveData
import com.capstone.pantauharga.database.FavoriteEvents
import com.capstone.pantauharga.database.FavoriteEventsDao

class EventsRepository(private val favoriteEventsDao: FavoriteEventsDao) {

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvents>> {
        return favoriteEventsDao.getAllFavoriteEvents()
    }

    fun getFavoriteEventById(id: String): LiveData<FavoriteEvents?> {
        return favoriteEventsDao.getFavoriteEventById(id)
    }

    suspend fun insert(event: FavoriteEvents) {
        favoriteEventsDao.insert(event)
    }

    suspend fun delete(event: FavoriteEvents) {
        favoriteEventsDao.delete(event)
    }

}
