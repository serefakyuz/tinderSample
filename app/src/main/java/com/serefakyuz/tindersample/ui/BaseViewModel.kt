package com.serefakyuz.tindersample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class BaseViewModel : ViewModel() {

    val _errorModel = MutableLiveData<Throwable>().apply {
        value = null
    }
    val errorModel: LiveData<Throwable> = _errorModel

    val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading
}