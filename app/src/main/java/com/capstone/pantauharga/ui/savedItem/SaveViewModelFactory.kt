package com.capstone.pantauharga.ui.savedItem

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.pantauharga.repository.PredictInflationRepository

class SaveViewModelFactory(
    private val application: Application,
    private val repository: PredictInflationRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaveViewModel::class.java)) {
            return SaveViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
