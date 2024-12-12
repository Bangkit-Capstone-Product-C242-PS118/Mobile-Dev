package com.capstone.pantauharga.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pantauharga.data.response.DataItem
import com.capstone.pantauharga.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

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

    private val _komoditas = MutableLiveData<List<DataItem>>()
    val komoditas: LiveData<List<DataItem>> = _komoditas

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
                if (responseKomoditas.data.isEmpty()) {
                    setError(true)
                    Log.d("KomoditasViewModel", "Empty data received from API")
                } else {
                    setError(false)
                    _komoditas.postValue(responseKomoditas.data)
                }
            } catch (e: IOException) {
                _loading.value = false
                setError(true)
                Log.d("KomoditasViewModel", "Network error: Unable to reach the server. Check your internet connection. Exception: ${e.message}")
                e.printStackTrace()
            } catch (e: HttpException) {
                _loading.value = false
                setError(true)
                Log.d("KomoditasViewModel", "HTTP error: Received HTTP status ${e.code()}. Message: ${e.message()}")
                e.printStackTrace()
            } catch (e: Exception) {
                _loading.value = false
                setError(true)
                Log.d("KomoditasViewModel", "Unexpected error occurred: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun setError(value: Boolean) {
        _error.value = value
    }
}