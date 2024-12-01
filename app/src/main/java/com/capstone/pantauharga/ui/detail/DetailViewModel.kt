package com.capstone.pantauharga.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    private val _location = MutableLiveData<String>()
    val location: LiveData<String> get() = _location

    private val _commodityName = MutableLiveData<String>()
    val commodityName: LiveData<String> get() = _commodityName

    private val _activeFragment = MutableLiveData<String>()
    val activeFragment: LiveData<String> = _activeFragment

    fun setLocation(location: String) {
        _location.value = location
    }

    fun setCommodityName(commodityName: String) {
        _commodityName.value = commodityName
    }

    fun setActiveFragment(fragmentTag: String) {
        if (_activeFragment.value != fragmentTag) {
            _activeFragment.value = fragmentTag
        }
    }

}