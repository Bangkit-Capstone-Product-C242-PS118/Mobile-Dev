package com.capstone.pantauharga.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pantauharga.data.response.ListCommoditiesItem
import com.capstone.pantauharga.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel : ViewModel() {
//    private val _komoditas = MutableLiveData<List<KomoditasResponseItem>>()
//    val komoditas: LiveData<List<KomoditasResponseItem>> = _komoditas
//
//    private val _loading = MutableLiveData<Boolean>()
//    val loading: LiveData<Boolean> get() = _loading
//
//    private val _error = MutableLiveData<Boolean>()
//    val error: LiveData<Boolean> = _error
//
//    init {
//        komoditas()
//    }
//
//    private fun komoditas() {
//        _loading.value = true
//
//        viewModelScope.launch {
//            try {
//                val responseKomoditas = ApiConfig.getApiService().getCommodities()
//                _loading.value = false
//                if (responseKomoditas.komoditasResponse.isNullOrEmpty()) {
//                    setError(true)
//                    println("Empty data received from API")
//                } else {
//                    _komoditas.postValue(responseKomoditas.komoditasResponse)
//                }
//            } catch (e: Exception) {
//                _loading.value = false
//                setError(true)
//                e.printStackTrace()
//            }
//        }
//    }
//
//
//    fun setError(value: Boolean) {
//        _error.value = value
//    }

    private val _komoditas = MutableLiveData<List<ListCommoditiesItem>>()
    val komoditas: LiveData<List<ListCommoditiesItem>> = _komoditas

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    init {
        komoditas()
    }

    fun komoditas() {
        _loading.value = true

        viewModelScope.launch {
            try {
                val responseKomoditas = ApiConfig.getApiService().getCommodities()
                _loading.value = false
                if (responseKomoditas.listCommodities.isNullOrEmpty()) {
                    setError(true)
                    println("Empty data received from API")
                } else {
                    _komoditas.postValue(responseKomoditas.listCommodities)
                }
            } catch (e: Exception) {
                _loading.value = false
                setError(true)
                e.printStackTrace()
            }
        }
    }

    fun setError(value: Boolean) {
        _error.value = value
    }
}