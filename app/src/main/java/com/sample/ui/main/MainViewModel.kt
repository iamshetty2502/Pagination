package com.sample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.models.Businesses
import com.sample.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getRestaurantsInProvidedRadius(radius: Int): LiveData<PagingData<Businesses>> {
        return repository.getNearbyRestaurants(radius).cachedIn(viewModelScope)
    }

    fun convertMeterToKilometer(meter: Float): Float {
        return (meter * 0.001).toFloat()
    }
}