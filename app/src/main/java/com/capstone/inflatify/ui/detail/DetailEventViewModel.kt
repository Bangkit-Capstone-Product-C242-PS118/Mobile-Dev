package com.capstone.inflatify.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.inflatify.data.response.DetailResponse
import com.capstone.inflatify.data.retrofit.ApiConfig
import com.capstone.inflatify.database.FavoriteEvents
import com.capstone.inflatify.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailEventViewModel(private val repository: EventsRepository) : ViewModel() {
    private val _eventDetail = MutableLiveData<DetailResponse>()
    val eventDetail: LiveData<DetailResponse> = _eventDetail

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error


    fun fetchEventDetail(id: String) {
        _loading.value = true
        val apiService = ApiConfig.getApiService()

        viewModelScope.launch {
            try {
                val response = apiService.getDetailEvent(id)
                _loading.value = false
                _eventDetail.postValue(response)
            } catch (e: Exception) {
                _loading.value = false
                setError(true)
            }
        }
    }

    fun getFavoriteEventById(id: String): LiveData<FavoriteEvents?> {
        return repository.getFavoriteEventById(id)
    }

    fun insertFavoriteEvent(event: FavoriteEvents) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(event)
        }
    }

    fun deleteFavoriteEvent(event: FavoriteEvents) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(event)
        }
    }

    fun setError(value: Boolean) {
        _error.value = value
    }
}
