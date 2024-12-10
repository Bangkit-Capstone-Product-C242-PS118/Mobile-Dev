package com.capstone.pantauharga.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [HargaKomoditas::class, NormalPrice::class, Inflasi::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun predictInflationDao(): HargaKomoditasDao
    abstract fun normalPriceDao(): NormalPriceDao
    abstract fun inflasiDao(): InflationDao

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