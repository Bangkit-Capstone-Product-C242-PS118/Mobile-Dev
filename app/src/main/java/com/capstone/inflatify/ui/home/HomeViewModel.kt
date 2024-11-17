package com.capstone.inflatify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.inflatify.data.response.ListEventsItem
import com.capstone.inflatify.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> get() = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> get() = _finishedEvents

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    init {
        eventsUpcoming()
        eventsFinished()
    }

    private fun eventsUpcoming() {
        _loading.value = true

        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(1)
                _loading.value = false
                _upcomingEvents.postValue(response.listEvents)
            } catch (e: Exception) {
                _loading.value = false
                setError(true)
            }
        }
    }

    private fun eventsFinished() {
        _loading.value = true

        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(0)
                _loading.value = false
                _finishedEvents.postValue(response.listEvents)
            } catch (e: Exception) {
                _loading.value = false
                setError(true)
            }
        }
    }

    fun setError(value: Boolean) {
        _error.value = value
    }
}