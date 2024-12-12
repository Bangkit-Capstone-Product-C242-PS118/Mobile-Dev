package com.capstone.pantauharga.ui.provinsi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pantauharga.data.response.DataItemDaerah
import com.capstone.pantauharga.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProvinceViewModel : ViewModel() {
    private val _provinsi = MutableLiveData<List<DataItemDaerah>>()
    val provinsi: LiveData<List<DataItemDaerah>> get() = _provinsi

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun getProvinces(commodityId: String) {
        _loading.value = true

        viewModelScope.launch {
            try {
                val responseProvinsi = ApiConfig.getApiService().getProvincesByCommodity(commodityId)
                _loading.value = false
                if (responseProvinsi.data.isEmpty()) {
                    setError(true)
                    Log.d("KomoditasViewModel", "Empty data received from API")
                } else {
                    setError(false)
                    _provinsi.postValue(responseProvinsi.data)
                }
            } catch (e: IOException) {
                _loading.value = false
                setError(true)
                Log.d("ProvinceViewModel", "Network error: Unable to reach the server. Check your internet connection. Exception: ${e.message}")
                e.printStackTrace()
            } catch (e: HttpException) {
                _loading.value = false
                setError(true)
                Log.d("ProvinceViewModel", "HTTP error: Received HTTP status ${e.code()}. Message: ${e.message()}")
                e.printStackTrace()
            } catch (e: Exception) {
                _loading.value = false
                setError(true)
                Log.d("ProvinceViewModel", "Unexpected error occurred: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun setError(value: Boolean) {
        _error.value = value
    }
}