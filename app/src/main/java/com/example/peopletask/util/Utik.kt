package com.example.peopletask.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


// TODO
//  1- improve the design                                   >>If there is time<<
//  2- handle response failure (network/server down)        ##important##
//  3- add Shimmer library                                  >>If there is time<<

// FixMe
//  1- airplane mode then device becomes online data didn't show unless you press back button (later)


object Util {
    const val API_KEY = "d48385912e9ef27b0552871f8b253a6e"

    const val IMAGE_URL = " http://image.tmdb.org/t/p/w185/"

    const val PERSON_ID_KEY ="person_id"

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}