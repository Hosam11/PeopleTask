package com.example.peopletask.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.peopletask.R
import com.example.peopletask.adapters.PopularPersonAdapter
import com.example.peopletask.databinding.ActivityPopularPersonsBinding
import com.example.peopletask.util.Util
import com.example.peopletask.viewmodels.PopularPersonsViewModel
import com.example.peopletask.viewmodels.PopularPersonsViewModelFactory
import timber.log.Timber


class PopularPersonsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_persons)

        val binding = DataBindingUtil
            .setContentView<ActivityPopularPersonsBinding>(this, R.layout.activity_popular_persons)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        val personsViewModelFactory = PopularPersonsViewModelFactory(application)

        val personsViewModel = ViewModelProvider(this, personsViewModelFactory)
            .get(PopularPersonsViewModel::class.java)

        binding.viewModel = personsViewModel
        // Sets the adapter of the PopularPersons RecyclerView with clickHandler lambda that
        // tells the viewModel when our PersonResult is clicked
        val adapter = PopularPersonAdapter(PopularPersonAdapter.PersonClickListener { person ->
            Timber.i("personClick name= ${person.name}  id= ${person.id} ")
            val personDetailsIntent = Intent(this, PersonDetailsActivity::class.java)
            personDetailsIntent.putExtra(Util.PERSON_ID_KEY, person.id)
            startActivity(personDetailsIntent)
        })

        binding.rvPopularPersons.adapter = adapter

    }
}