package com.example.jetpackapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel(countReserved: Int) : ViewModel() {

    private val userLiveData = MutableLiveData<User>()
    val userName: LiveData<String> = Transformations.map(userLiveData)
    { user ->
        "${user.firstName} ${user.lastName}"
    }

    val counter : LiveData<Int>
        get() = _counter

    private val _counter = MutableLiveData<Int>()

    init {
        _counter.value = countReserved
        userLiveData.value = User("John", "Doe", 18)
    }

    fun plusOne() {
        _counter.value = counter.value?.plus(1)
    }

    fun clear() {
        _counter.value = 0
    }

}