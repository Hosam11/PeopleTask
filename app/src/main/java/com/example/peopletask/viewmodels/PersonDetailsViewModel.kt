package com.example.peopletask.viewmodels

import androidx.lifecycle.*
import com.example.peopletask.domain.PersonDetails
import com.example.peopletask.domain.PersonImage
import com.example.peopletask.network.PersonsNetwork
import com.example.peopletask.util.Util
import kotlinx.coroutines.launch

class PersonDetailsViewModel(personId: Long) : ViewModel() {

    // The internal MutableLiveData that stores the personDetails of the most recent PopularPersons
    private val _personDetails = MutableLiveData<PersonDetails>()

    // The external immutable LiveData for the request personDetails
    val personDetails: LiveData<PersonDetails>
        get() = _personDetails

    // The internal MutableLiveData that stores the PersonImages of the single person
    private val _personImages = MutableLiveData<List<PersonImage>>()

    // The external immutable LiveData for the request PersonImages
    val personImages:LiveData<List<PersonImage>>
        get() = _personImages

    init {
        getPersonDetails(personId)
        getPersonImages(personId)
    }

    private fun getPersonDetails(personId: Long) {
        viewModelScope.launch {
            _personDetails.value =
                PersonsNetwork.personsService.getPersonDetails( personId, Util.API_KEY)
        }
    }

    /**
     * Transform list of strings so i can display it on TextView
     */
    val otherNames = Transformations.map(personDetails){
        personDetails.value?.otherNames?.joinToString("\n")
    }


    private fun getPersonImages(personId: Long) {
        viewModelScope.launch {
            _personImages.value = PersonsNetwork.personsService.getPersonImages(personId, Util.API_KEY).personImages
        }
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

