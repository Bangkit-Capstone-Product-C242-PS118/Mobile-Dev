package com.capstone.pantauharga.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pantauharga.data.response.PredictInflationResponse
import com.capstone.pantauharga.data.response.ListCommoditiesItem
import com.capstone.pantauharga.data.response.ListProvincesItem
import com.capstone.pantauharga.data.response.NormalPriceResponse
import com.capstone.pantauharga.data.response.PredictionsItem
import com.capstone.pantauharga.data.retrofit.ApiService
import com.capstone.pantauharga.database.AppDatabase
import com.capstone.pantauharga.database.PredictInflation
import com.capstone.pantauharga.repository.PredictInflationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class DetailViewModel(private val apiService: ApiService, private val database: AppDatabase) : ViewModel() {
//
//    private val _location = MutableLiveData<String>()
//    val location: LiveData<String> get() = _location
//
//    private val _commodityName = MutableLiveData<String>()
//    val commodityName: LiveData<String> get() = _commodityName
//
//    private val _activeFragment = MutableLiveData<String>()
//    val activeFragment: LiveData<String> = _activeFragment
//
//    private val _inflationData = MutableLiveData<PredictInflationResponse>()
//    val inflationData: LiveData<PredictInflationResponse> get() = _inflationData
//
//    private val _normalPrice = MutableLiveData<NormalPriceResponse>()
//    val normalPricData: LiveData<NormalPriceResponse> get() = _normalPrice
//
//    private val _loading = MutableLiveData<Boolean>()
//    val loading: LiveData<Boolean> get() = _loading
//
//    private val _error = MutableLiveData<Boolean>()
//    val error: LiveData<Boolean> get() = _error
//
//
//    fun savePrediction(description: String, predictions: List<PredictionsItem>, commodityName: String,
//                       provinceName: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val predictionEntity = PredictInflation(
//                description = description,
//                predictions = predictions,
//                commodityName = commodityName,
//                provinceName = provinceName)
//            database.predictInflationDao().insertPrediction(predictionEntity)
//        }
//    }
//
//    fun getPredictionById(id: Int): LiveData<PredictInflation?> {
//        return database.predictInflationDao().getPredictionById(id)
//    }
//
//    fun deletePrediction(prediction: PredictInflation) {
//        viewModelScope.launch(Dispatchers.IO) {
//            database.predictInflationDao().deletePrediction(prediction)
//        }
//    }
//
//    fun setLocation(provinsi: ListProvincesItem) {
//        _location.value = provinsi.name
//    }
//
//    fun setCommodityName(komoditas: ListCommoditiesItem) {
//        _commodityName.value = komoditas.title
//    }
//
//    fun setActiveFragment(fragmentTag: String) {
//        if (_activeFragment.value != fragmentTag) {
//            _activeFragment.value = fragmentTag
//        }
//    }
//
//    fun fetchInflationPrediction(commodityId: String, provinceId: String, timeRange: Int) {
//        _loading.value = true
//
//        viewModelScope.launch {
//            try {
//                val response: PredictInflationResponse = apiService.getInflationPredictions(commodityId, provinceId, timeRange)
//                _inflationData.value = response
//                _error.value = false
//            } catch (e: Exception) {
//                _error.value = true
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//
//    fun fetchNormalPrices(commodityId: String, provinceId: String) {
//        _loading.value = true
//
//        viewModelScope.launch {
//            try {
//                val response: NormalPriceResponse = apiService.getNormalPrices(commodityId, provinceId)
//                _normalPrice.value = response
//                _error.value = false
//            } catch (e: Exception) {
//                _error.value = true
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//}
class DetailViewModel(private val repository: PredictInflationRepository) : ViewModel() {
    private val _location = MutableLiveData<String>()
    val location: LiveData<String> get() = _location

    private val _commodityName = MutableLiveData<String>()
    val commodityName: LiveData<String> get() = _commodityName

    private val _activeFragment = MutableLiveData<String>()
    val activeFragment: LiveData<String> = _activeFragment

    private val _inflationData = MutableLiveData<PredictInflationResponse>()
    val inflationData: LiveData<PredictInflationResponse> get() = _inflationData

    private val _normalPrice = MutableLiveData<NormalPriceResponse>()
    val normalPricData: LiveData<NormalPriceResponse> get() = _normalPrice

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun getPredictionById(id: Int): LiveData<PredictInflation?> {
        return repository.getPredictionById(id)
    }

    fun getAllPrediction(): LiveData<List<PredictInflation>> {
        return repository.getAllPrediction()
    }

    fun savePrediction(description: String, predictions: List<PredictionsItem>, commodityName: String, provinceName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePrediction(description, predictions, commodityName, provinceName)
        }
    }

    fun deletePrediction(prediction: PredictInflation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePrediction(prediction)
        }
    }

    fun setLocation(provinsi: ListProvincesItem) {
        _location.value = provinsi.name
    }

    fun setCommodityName(komoditas: ListCommoditiesItem) {
        _commodityName.value = komoditas.title
    }

    fun setActiveFragment(fragmentTag: String) {
        if (_activeFragment.value != fragmentTag) {
            _activeFragment.value = fragmentTag
        }
    }

    fun fetchInflationPrediction(commodityId: String, provinceId: String, timeRange: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getInflationPredictions(commodityId, provinceId, timeRange)
                _inflationData.value = response
                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchNormalPrices(commodityId: String, provinceId: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getNormalPrices(commodityId, provinceId)
                _normalPrice.value = response
                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }
}