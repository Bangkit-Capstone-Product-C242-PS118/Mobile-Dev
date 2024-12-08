package com.capstone.pantauharga.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PredictInflationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrediction(prediction: PredictInflation)

    @Query("SELECT * FROM PredictInflation WHERE id = :id")
    fun getPredictionById(id: Int): LiveData<PredictInflation?>

    @Query("SELECT * FROM PredictInflation")
    fun getAllPredictions(): LiveData<List<PredictInflation>>

    @Delete
    suspend fun deletePrediction(prediction: PredictInflation)

}
