package com.example.jetpackapplication

import androidx.lifecycle.ViewModel

class MainViewModel(countReserved: Int) : ViewModel() {
    var counter = countReserved
}