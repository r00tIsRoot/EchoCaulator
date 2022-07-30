package com.isroot.echocaulator

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val _targetEcho = MutableLiveData<Int>()
    val targetEcho: LiveData<Int> get() = _targetEcho

    val echoCalculator = EchoCalculator()

    fun calc(){
        echoCalculator.calc(0)
    }
}