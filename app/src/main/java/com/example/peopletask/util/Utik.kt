package com.example.peopletask.util

import android.R
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AlertDialog
import timber.log.Timber


object Util {
    const val API_KEY = "d48385912e9ef27b0552871f8b253a6e"
    const val PERSON_ID_KEY = "person_id"
    const val PERSON_IMAGE_KEY = "person_image"
    const val CHANNEL_ID = "download_notification"
    const val CHANNEL_NAME = "downloading"

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

    fun showAlert(context: Context) {
        Timber.i("show alert")
        AlertDialog.Builder(context)
            .setTitle(context.getString(com.example.peopletask.R.string.internet_error_title))
            .setMessage(context.getString(com.example.peopletask.R.string.internet_error_message))
            .setPositiveButton(
                R.string.yes
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }

}