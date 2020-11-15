package com.example.peopletask.data

import android.content.Context
import androidx.paging.DataSource
import com.example.peopletask.domain.Person
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber


/**
 * Using Data Source we need DataSource.Factory which extends DataSource.Factory.
 * Basically it just a factory for DataSources. So first create an instance of data source
 * so that we can use it in our RecyclerView.
 */
class PopularDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val appContext: Context
) : DataSource.Factory<Int, Person>() {

    override fun create(): DataSource<Int, Person> {
        Timber.i(" From create() ")
        return PopularDataSource(coroutineScope, appContext)
    }

}
