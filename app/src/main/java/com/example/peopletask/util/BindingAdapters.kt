package com.example.peopletask.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.peopletask.adapter.PopularPersonAdapter
import com.example.peopletask.domain.PersonResult
import timber.log.Timber

/**
 * data: must declared  as null receiver
 * Use a binding adapter to initialize our PopularPerson adapter with list data.
 * Using a binding adapter to set the RecyclerView data will cause data binding to
 * automatically observe the LiveData for the list of Mars properties on our behalf.
 * Therefore, this adapter will be called automatically when the PersonResult list changes
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<PersonResult>?) {
    val adapter = recyclerView.adapter as PopularPersonAdapter
    adapter.submitList(data)
    Timber.i("bindRecyclerView()")

}