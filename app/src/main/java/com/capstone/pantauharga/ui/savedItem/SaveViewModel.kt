package com.capstone.pantauharga.ui.savedItem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.capstone.pantauharga.database.PredictInflation
import com.capstone.pantauharga.repository.PredictInflationRepository
import kotlinx.coroutines.launch

class SaveViewModel(application: Application, private val repository: PredictInflationRepository) : AndroidViewModel(application) {

    val allPredictions: LiveData<List<PredictInflation>> = repository.getAllPrediction()

    fun deletePrediction(prediction: PredictInflation) {
        viewModelScope.launch {
            repository.deletePrediction(prediction)
        }
    }
}
