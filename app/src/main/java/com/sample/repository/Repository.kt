package com.sample.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.sample.network.INetwork
import com.sample.paging.Paginator
import javax.inject.Inject

class Repository @Inject constructor(private val iNetwork: INetwork) {

    fun getNearbyRestaurants(radius: Int) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { Paginator(iNetwork,radius) }
    ).liveData
}