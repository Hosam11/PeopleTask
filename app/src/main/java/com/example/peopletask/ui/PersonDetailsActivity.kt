package com.example.peopletask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.peopletask.R
import com.example.peopletask.databinding.ActivityPersonDetailsBinding
import com.example.peopletask.util.Util
import com.example.peopletask.viewmodels.PersonDetailsViewModel
import com.example.peopletask.viewmodels.PersonDetailsViewModelFactory
import timber.log.Timber

class PersonDetailsActivity : AppCompatActivity() {
    var personID: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)
        val binding = DataBindingUtil.setContentView<ActivityPersonDetailsBinding>(
            this,
            R.layout.activity_person_details
        )
        binding.lifecycleOwner = this
        val intent = intent
        personID =  intent.getLongExtra(Util.PERSON_ID_KEY, -1)

        val viewModelFactory = PersonDetailsViewModelFactory(personID)

        val personsDetailsViewModel =  ViewModelProvider(this, viewModelFactory)
            .get(PersonDetailsViewModel::class.java)

        binding.viewModel = personsDetailsViewModel

    }
}