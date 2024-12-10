package com.capstone.pantauharga.ui.savedItem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.pantauharga.database.HargaKomoditas
import com.capstone.pantauharga.database.NormalPrice
import com.capstone.pantauharga.repository.PredictInflationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveViewModel(private val repository: PredictInflationRepository) : ViewModel() {


    val commodityNames: LiveData<List<String>> = repository.getAllCommodityNames()
    val provinceNames: LiveData<List<String>> = repository.getAllProvinceNames()

    val commodityNamesNormal: LiveData<List<String>> = repository.getAllCommodityNamesNormal()
    val provinceNamesNormal: LiveData<List<String>> = repository.getAllProvinceNamesNormal()

    fun getPredictionByCommodityAndProvince(commodityName: String, provinceName: String): LiveData<HargaKomoditas?> {
        return repository.getPredictionByCommodityAndProvince(commodityName, provinceName)
    }

    fun getNormalPriceByCommodityAndProvince(commodityName: String, provinceName: String): LiveData<NormalPrice?> {
        return repository.getNormalPriceByCommodityAndProvince(commodityName, provinceName)
    }


    fun deleteByCommodityAndProvince(commodityName: String, provinceName: String) {
        viewModelScope.launch {
            repository.deleteByCommodityAndProvince(commodityName, provinceName)
        }
    }


    fun deleteNormalByCommodityAndProvince(commodityName: String, provinceName: String) {
        viewModelScope.launch {
            repository.deleteNormalByCommodityAndProvince(commodityName, provinceName)
        }
    }

    val normalPrice: LiveData<List<NormalPrice>> = repository.getAllNormalPrices()
    val hargaKomoditas: LiveData<List<HargaKomoditas>> = repository.getAllPrediction()



    fun getWaktu(komoditasId: String, provinceId: String, timeRange: Int): LiveData<HargaKomoditas?> {
        return repository.getWaktu(komoditasId, provinceId, timeRange)
    }

    fun getWaktuNormal(komoditasId: String, provinceId: String, timeRange: Int): LiveData<NormalPrice?> {
        return repository.getWaktuNormal(komoditasId, provinceId, timeRange)
    }


}