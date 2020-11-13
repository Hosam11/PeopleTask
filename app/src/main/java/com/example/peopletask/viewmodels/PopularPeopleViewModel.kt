package com.example.peopletask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peopletask.domain.PersonResult
import com.example.peopletask.network.PersonsNetwork
import com.example.peopletask.util.Util
import kotlinx.coroutines.launch
import timber.log.Timber


class PopularPersonsViewModel : ViewModel() {

    private val _persons = MutableLiveData<List<PersonResult>>()

    val persons: LiveData<List<PersonResult>>
        get() = _persons

    init {
        getPopularPersons()
    }

    private fun getPopularPersons() {
        viewModelScope.launch {
            _persons.value = PersonsNetwork.personsService.getPopularPeople(Util.API_KEY).results
            Timber.i("getPopularPersons ${_persons.value?.size}")
        }

    }

}