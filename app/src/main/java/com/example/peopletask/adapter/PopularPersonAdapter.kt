package com.example.peopletask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.peopletask.adapter.PopularPersonAdapter.PersonsViewHolder
import com.example.peopletask.databinding.PopularPeopleListItemBinding
import com.example.peopletask.domain.PersonResult


class PopularPersonAdapter
    : PagedListAdapter<PersonResult, PersonsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsViewHolder {
        return PersonsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {
        val person = getItem(position)
        person?.let { holder.bind(it) }
    }


    class PersonsViewHolder(private val binding: PopularPeopleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): PersonsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularPeopleListItemBinding.inflate(layoutInflater, parent, false)
                return PersonsViewHolder(binding)
            }
        }

        fun bind(person: PersonResult) {
            binding.person = person
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }


    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [PersonResult]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<PersonResult>() {
        override fun areItemsTheSame(oldItem: PersonResult, newItem: PersonResult) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: PersonResult, newItem: PersonResult) =
            newItem == oldItem
    }

}

