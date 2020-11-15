package com.example.peopletask.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.peopletask.data.PopularDataSource
import com.example.peopletask.data.PopularDataSourceFactory
import com.example.peopletask.domain.Person

class PopularPersonsViewModel(app: Application) : AndroidViewModel(app) {

    var popularPagedList: LiveData<PagedList<Person>>

    init {
        val dataSourceFactory = PopularDataSourceFactory(viewModelScope, app.applicationContext)
        val config = PagedList.Config.Builder()
            .setPageSize(PopularDataSource.PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        popularPagedList = LivePagedListBuilder(dataSourceFactory, config)
            .build()

    }

}

class PopularPersonsViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularPersonsViewModel::class.java)) {
            return PopularPersonsViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}