package com.example.peopletask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.peopletask.databinding.PersonImagesGridItemBinding
import com.example.peopletask.domain.PersonImage
import com.example.peopletask.domain.Person
import timber.log.Timber

class PersonImagesAdapter(private val imageClickListener: ImageClickListener) : ListAdapter<PersonImage, PersonImagesAdapter.PersonImagesViewHolder>
    (DiffCallback) {

    class PersonImagesViewHolder(private val binding: PersonImagesGridItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup):  PersonImagesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PersonImagesGridItemBinding.inflate(layoutInflater, parent, false)
                return  PersonImagesViewHolder(binding)
            }
        }

        fun bind(personImage: PersonImage) {
            binding.personImage = personImage
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonImagesViewHolder {
        return PersonImagesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PersonImagesViewHolder, position: Int) {
        val personImage = getItem(position)
        holder.bind(personImage)

        holder.itemView.setOnClickListener {
            Timber.i("imageClickListener >> $personImage")
            imageClickListener.onImageClick(personImage)
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [PersonImage]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<PersonImage>() {
        override fun areItemsTheSame(oldItem: PersonImage, newItem: PersonImage) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: PersonImage, newItem: PersonImage) =
            newItem == oldItem
    }


    /**
     * Custom listener that handles clicks on [RecyclerView] items. Passes the [Person]
     * associated with the current item to the [onPersonClick] function.
     *
     * @param clickListener lambda that will be called with the current [Person]
     */
    class ImageClickListener(private val clickListener: (personImage: PersonImage) -> Unit) {
        fun onImageClick(personImage: PersonImage) = clickListener(personImage)
    }
}