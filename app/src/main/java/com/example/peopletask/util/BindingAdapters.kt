package com.example.peopletask.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.peopletask.R
import com.example.peopletask.adapters.PersonImagesAdapter
import com.example.peopletask.adapters.PopularPersonAdapter
import com.example.peopletask.domain.PersonImage
import com.example.peopletask.domain.Person
import timber.log.Timber


/**
 * data: must declared  as null receiver
 * Use a binding adapter to initialize our PopularPerson adapter with list data.
 * Using a binding adapter to set the RecyclerView data will cause data binding to
 * automatically observe the LiveData for  [PagedList] of [Person] on our behalf.
 * Therefore, this adapter will be called automatically when the [PagedList] list changes
 */
@BindingAdapter("listData")
fun bindPersonsRecyclerView(recyclerView: RecyclerView, data: PagedList<Person>?) {
    val adapter = recyclerView.adapter as PopularPersonAdapter
    adapter.submitList(data)
    Timber.i("bindPersonsRecyclerView() listSize >> ${data?.size}")
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgPath: String?) {
    imgPath?.let {
        // add the path of image to string url so glide can download the image
        val imgUrl = Util.IMAGE_URL + imgPath
        // convert string url to uri
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.no_image_found)
            )
            .into(imgView)
    }
}

@BindingAdapter("listImagesData")
fun RecyclerView.bindImagesRecyclerView(data: List<PersonImage>?){
    val adapter =  this.adapter as PersonImagesAdapter
    adapter.submitList(data)
    Timber.i("bindImagesRecyclerView() listSize >> ${data?.size}")


}