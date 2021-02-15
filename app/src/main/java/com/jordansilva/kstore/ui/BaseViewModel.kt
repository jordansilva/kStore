package com.jordansilva.kstore.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        handleCoroutineException(coroutineContext, throwable)
    }

    fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(coroutineExceptionHandler) { block() }
    }

    open fun handleCoroutineException(coroutineContext: CoroutineContext, throwable: Throwable) {
        Log.e("CoroutineException", "Coroutine crashed: ${throwable.message}")
    }
}