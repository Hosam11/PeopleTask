package com.example.peopletask.viewmodels

import androidx.lifecycle.*
import com.example.peopletask.domain.PersonDetails
import com.example.peopletask.network.PersonsNetwork
import com.example.peopletask.util.Util
import kotlinx.coroutines.launch

class PersonDetailsViewModel(personId: Long) : ViewModel() {

    private val _personDetails = MutableLiveData<PersonDetails>()

    val personDetails: LiveData<PersonDetails>
        get() = _personDetails

    init {
        getPersonDetails(personId)
    }

    private fun getPersonDetails(personId: Long) {
        viewModelScope.launch {
            _personDetails.value =
                PersonsNetwork.personsService.getPersonDetails( personId, Util.API_KEY)
        }
    }

    val otherNames = Transformations.map(personDetails){
        personDetails.value?.otherNames?.joinToString("\n")
    }

}

/**
 * Simple ViewModel factory that provides person id to the ViewModel.
 */
class PersonDetailsViewModelFactory(private val personId: Long) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonDetailsViewModel::class.java)) {
            return PersonDetailsViewModel(personId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}