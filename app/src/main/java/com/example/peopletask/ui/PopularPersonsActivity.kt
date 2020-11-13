package com.example.peopletask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.peopletask.R
import com.example.peopletask.adapter.PopularPersonAdapter
import com.example.peopletask.databinding.ActivityPopularPersonsBinding
import com.example.peopletask.viewmodels.PopularPersonsViewModel


class PopularPersonsActivity : AppCompatActivity() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val personsViewModel: PopularPersonsViewModel by lazy {
        ViewModelProvider(this).get(PopularPersonsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_persons)

        val binding = DataBindingUtil
            .setContentView<ActivityPopularPersonsBinding>(this, R.layout.activity_popular_persons)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.viewModel = personsViewModel

        val adapter = PopularPersonAdapter()
        binding.rvPopularPersons.adapter = adapter


    }
}