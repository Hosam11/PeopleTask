package com.example.peopletask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.peopletask.R
import com.example.peopletask.adapter.PopularPersonAdapter
import com.example.peopletask.databinding.ActivityPopularPersonsBinding
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

        val adapter = PopularPersonAdapter()

        binding.rvPopularPersons.adapter = adapter

    }
}