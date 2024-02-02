package com.serefakyuz.tindersample.extension

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import com.afollestad.materialdialogs.MaterialDialog
import com.serefakyuz.tindersample.R

fun Context.isNetworkAvailable(): Boolean{
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun Context.showNoNetworkDialog(){
    MaterialDialog(this).show {
        title(R.string.networkErrorTitle)
        message ( R.string.networkErrorMsg, null, null )
        positiveButton ( R.string.ok ){
                (context as Activity).finish()
        }
        cancelable(false)
    }
}