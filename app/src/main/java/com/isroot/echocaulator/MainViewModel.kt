package com.isroot.echocaulator

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val _targetEcho = MutableLiveData<Int>()
    val targetEcho: LiveData<Int> get() = _targetEcho

    val targetEchoStr = MutableLiveData<String>()

    val echoCalculator = EchoCalculator()

    fun calc() = viewModelScope.launch(Dispatchers.IO){
        echoCalculator.calc(targetEchoStr.value!!.toInt())
    }
}