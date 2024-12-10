package com.capstone.pantauharga.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.pantauharga.data.retrofit.ApiService
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.repository.PredictInflationRepository

class DetailPricesViewModelFactory(
    private val repository: PredictInflationRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailPricesViewModel::class.java)) {
            return DetailPricesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}