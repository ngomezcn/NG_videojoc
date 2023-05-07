package com.example.ng_videojoc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.*

class ViewModel : ViewModel() {
    val score = MutableLiveData<Int>(0)
    val destroyed = MutableLiveData<Boolean>(false)

}