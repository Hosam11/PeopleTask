package com.example.peopletask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.peopletask.adapters.PopularPersonAdapter.PersonsViewHolder
import com.example.peopletask.databinding.PopularPeopleListItemBinding
import com.example.peopletask.domain.PersonResult


class PopularPersonAdapter(private val personClickListener: PersonClickListener) :
    PagedListAdapter<PersonResult, PersonsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsViewHolder {
        return PersonsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {
        val person = getItem(position)
        person?.let { holder.bind(it) }

        holder.itemView.setOnClickListener {
            person?.let { personClickListener.onPersonClick(it) }
        }
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

    /**
     * Custom listener that handles clicks on [RecyclerView] items. Passes the [PersonResult]
     * associated with the current item to the [onPersonClick] function.
     *
     * @param clickListener lambda that will be called with the current [PersonResult]
     */
    class PersonClickListener(private val clickListener: (personResult: PersonResult) -> Unit) {
        fun onPersonClick(person: PersonResult) = clickListener(person)
    }

}

