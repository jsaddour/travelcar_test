package com.example.travelcartest.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyAccountViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my account Fragment"
    }
    val text: LiveData<String> = _text
}