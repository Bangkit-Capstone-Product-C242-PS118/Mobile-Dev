package com.capstone.pantauharga.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [PredictInflation::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun predictInflationDao(): PredictInflationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic

        fun getDatabase(context: Context): AppDatabase {

            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "predict_inflation"
                    ).build()
                }
            }
            return INSTANCE as AppDatabase

        }
    }
}