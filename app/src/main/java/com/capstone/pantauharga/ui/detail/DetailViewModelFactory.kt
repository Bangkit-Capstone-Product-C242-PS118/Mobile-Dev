package com.capstone.pantauharga.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.pantauharga.data.retrofit.ApiService
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.repository.PredictInflationRepository

class DetailViewModelFactory(
    private val apiService: ApiService,
    private val database: AppDatabase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val repository = PredictInflationRepository(apiService, database)
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}