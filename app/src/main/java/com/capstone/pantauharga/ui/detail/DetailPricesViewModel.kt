package com.capstone.pantauharga.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pantauharga.data.response.Data
import com.capstone.pantauharga.data.response.DataInflasi
import com.capstone.pantauharga.data.response.DataItem
import com.capstone.pantauharga.data.response.DataItemDaerah
import com.capstone.pantauharga.data.response.HargaNormalResponse
import com.capstone.pantauharga.data.response.InflasiResponse
import com.capstone.pantauharga.data.response.InflationDataResponse
import com.capstone.pantauharga.data.response.LastPrice
import com.capstone.pantauharga.data.response.PredictInflationDataResponse
import com.capstone.pantauharga.data.response.PricesKomoditasItem
import com.capstone.pantauharga.data.response.PricesNormalItem
import com.capstone.pantauharga.database.NormalPrice
import com.capstone.pantauharga.database.HargaKomoditas
import com.capstone.pantauharga.repository.PredictInflationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch


class DetailPricesViewModel(private val repository: PredictInflationRepository) : ViewModel() {
    private val _location = MutableLiveData<String>()
    val location: LiveData<String> get() = _location

    private val _commodityName = MutableLiveData<String>()
    val commodityName: LiveData<String> get() = _commodityName


    private val _activeFragment = MutableLiveData<String>()
    val activeFragment: LiveData<String> = _activeFragment

    private val _hargaKomoditas = MutableLiveData<InflasiResponse>()
    val hargaKomoditas: LiveData<InflasiResponse> get() = _hargaKomoditas

    private val _inflationDataPredict = MutableLiveData<Data>()
    val inflationDataPredict: LiveData<Data> get() = _inflationDataPredict

    private val _lastPrice = MutableLiveData<LastPrice>()
    val lastPrice: LiveData<LastPrice> get() = _lastPrice

    private val _inflation = MutableLiveData<DataInflasi>()
    val inflation: LiveData<DataInflasi> get() = _inflation

    private val _normalPrice = MutableLiveData<HargaNormalResponse>()
    val normalPriceData: LiveData<HargaNormalResponse> get() = _normalPrice

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private val timeRanges = listOf(1, 2, 3, 4, 5)


    fun deleteHargaKomoditasByIds(commodityId: String, provinceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHargaKomoditasByIds(commodityId, provinceId)
        }
    }

    fun deleteHargaNormalByIds(commodityId: String, provinceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHargaNormalByIds(commodityId, provinceId)
        }
    }



    fun saveAllPredictions(commodityId: String, provinceId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true

                val predictions = timeRanges.map { timeRange ->
                    async(Dispatchers.IO) {
                        repository.getHargaKomoditas(commodityId, provinceId, timeRange)
                    }
                }.awaitAll()

                predictions.forEach { response ->
                        savePrediction(
                            daerahId = provinceId,
                            komoditasId = commodityId,
                            description = response.description,
                            predictions = response.prices,
                            commodityName = commodityName.value ?: "",
                            provinceName = location.value ?: "",
                            timeRange = response.prices.size
                        )
                }

                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }


    fun saveAllNormalPrice(commodityId: String, provinceId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true

                val predictions = timeRanges.map { timeRange ->
                    async(Dispatchers.IO) {
                        repository.getNormalPrices(commodityId, provinceId, timeRange)
                    }
                }.awaitAll()

                predictions.forEach { response ->
                    saveNormalPrice(
                        daerahId = provinceId,
                        komoditasId = commodityId,
                        description = response.description,
                        normalPrice = response.prices,
                        commodityName = commodityName.value ?: "",
                        provinceName = location.value ?: "",
                        timeRange = response.prices.size
                    )
                }

                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }


    fun getPredictionByCommodityAndProvince(commodityName: String, provinceName: String): LiveData<HargaKomoditas?> {
        return repository.getPredictionByCommodityAndProvince(commodityName, provinceName)
    }


    fun savePrediction(
        komoditasId: String,
        daerahId: String,
        description: String,
        predictions: List<PricesKomoditasItem>,
        commodityName: String,
        provinceName: String,
        timeRange: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveHargaKomoditas(komoditasId, daerahId, description, predictions, commodityName, provinceName, timeRange)
        }
    }


    fun getNormalPriceByCommodityAndProvince(
        commodityName: String,
        provinceName: String,
    ): LiveData<NormalPrice?> {
        return repository.getNormalPriceByCommodityAndProvince(commodityName, provinceName)
    }


    fun saveNormalPrice(
        komoditasId: String,
        daerahId: String,
        commodityName: String,
        provinceName: String,
        normalPrice: List<PricesNormalItem>,
        description: String,
        timeRange: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveNormalPrice( komoditasId, daerahId, commodityName, provinceName, normalPrice, description, timeRange)
        }
    }


    fun setLocation(provinsi: DataItemDaerah) {
        _location.value = provinsi.namaDaerah
    }

    fun setCommodityName(komoditas: DataItem) {
        _commodityName.value = komoditas.namaKomoditas
    }

    fun setActiveFragment(fragmentTag: String) {
        if (_activeFragment.value != fragmentTag) {
            _activeFragment.value = fragmentTag
        }
    }

    fun fetchInflationPrediction(daerahId: String, komoditasId: String, timeRange: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getHargaKomoditas(daerahId, komoditasId, timeRange)
                _hargaKomoditas.value = response
                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchNormalPrices(commodityId: String, provinceId: String, timeRange: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getNormalPrices(commodityId, provinceId, timeRange)
                _normalPrice.value = response
                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchPredictInflation(idDaerah: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPredictInflation(idDaerah)
                _inflationDataPredict.value = response.data
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchDataInflation(idDaerah: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDataInflation(idDaerah)
                _inflation.value = response.data
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchLastPrice(daerahId: String, komoditasId: String){
        viewModelScope.launch {
            try {
                val response = repository.getLastPrice(daerahId, komoditasId)
                _lastPrice.value = response.lastPrice
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

}