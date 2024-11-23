package com.capstone.pantauharga.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.pantauharga.repository.EventsRepository

class DetailEventViewModelFactory(private val repository: EventsRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailEventViewModel::class.java)) {
            return DetailEventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


