package com.example.peopletask.data

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.example.peopletask.domain.PersonResult
import com.example.peopletask.network.PersonsNetwork
import com.example.peopletask.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Data Source is Incremental data loader for page-keyed content, where requests return keys for
 * next/previous pages. Implement a DataSource using PageKeyedDataSource
 */
class PopularDataSource(
    private val coroutineScope: CoroutineScope,
    private val appContext: Context
) : PageKeyedDataSource<Int, PersonResult>() {

    companion object {
        const val PAGE_SIZE = 1
        const val FIRST_PAGE = 1
        const val MAX_PAGE_NUMBER = 500
    }

    private val personsService = PersonsNetwork.personsService

    /**
     * it used when to scroll up
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PersonResult>) {

        Timber.i("loadBefore() -  before  coroutineScope.launch key = ${params.key} ")
        if (Util.isNetworkAvailable(appContext)) {
            coroutineScope.launch {
                val body = personsService.getPopularPeople(Util.API_KEY, params.key)
                val persons = body.personsList
                val key = when {
                    params.key > 1 -> params.key - 1
                    else -> 0
                }
                Timber.i("loadBefore() >> key= $key  ")
                callback.onResult(persons, key)
            }
        } else {
            Timber.i("loadBefore() >> No internet found")
        }
    }

    /**
     * is called when natural scroll down
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PersonResult>) {
        Timber.i("loadAfter() - before coroutineScope.launch key = ${params.key}")
        if (Util.isNetworkAvailable(appContext)) {
            coroutineScope.launch {
                try {
                    // when page number becomes greater than MAX_PAGE_NUMBER response return 422
                    if (MAX_PAGE_NUMBER != params.key) {
                        val body = personsService.getPopularPeople(Util.API_KEY, params.key)
                        val persons = body.personsList
                        val key = params.key + 1
                        Timber.i("loadAfter() >> key= $key  ")
                        callback.onResult(persons, key)
                    }
                } catch (e: Exception) {
                    callback.onResult(listOf(), null)
                    Timber.i("Exception happened in loadAfter() >> ${e.printStackTrace()} ")
                }

            }

        } else {
            Timber.i("loadAfter() >> No internet found")
        }
    }


    /**
     * It loads the first page of your data that use to initialize RecyclerView
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PersonResult>
    ) {
        if (Util.isNetworkAvailable(appContext)) {
            coroutineScope.launch {
                try {
                    val body = personsService.getPopularPeople(Util.API_KEY, FIRST_PAGE)
                    val persons = body.personsList
                    callback.onResult(persons, null, FIRST_PAGE + 1)
                    Timber.i("loadInitial() >> key= $FIRST_PAGE")
                } catch (e: Exception) {
                    callback.onResult(listOf(), null, null)
                    Timber.i("Exception happened in loadInitial() >> ${e.printStackTrace()} ")
                }
            }
        } else {
            Timber.i("loadInitial() >> No internet found")
        }
    }

}