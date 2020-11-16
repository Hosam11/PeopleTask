package com.example.peopletask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.peopletask.adapters.PopularPersonAdapter.PersonsViewHolder
import com.example.peopletask.databinding.PopularPeopleListItemBinding
import com.example.peopletask.domain.Person
import com.example.peopletask.util.Util


class PopularPersonAdapter(private val personClickListener: PersonClickListener) :
    PagedListAdapter<Person, PersonsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsViewHolder {
        return PersonsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {
        val person = getItem(position)
        person?.let { holder.bind(it) }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            if (Util.isNetworkAvailable(context)) {
                person?.let { personClickListener.onPersonClick(it) }
            } else {
                Util.showAlert(context)
            }
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

        fun bind(person: Person) {
            binding.person = person
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }


    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Person]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Person, newItem: Person) =
            newItem == oldItem
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items. Passes the [Person]
     * associated with the current item to the [onPersonClick] function.
     *
     * @param clickListener lambda that will be called with the current [Person]
     */
    class PersonClickListener(private val clickListener: (person: Person) -> Unit) {
        fun onPersonClick(person: Person) = clickListener(person)
    }

}

