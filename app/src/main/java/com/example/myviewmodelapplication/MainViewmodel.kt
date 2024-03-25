package com.example.myviewmodelapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap

class MainViewmodel(countReserved: Int): ViewModel() {
    var counter=MutableLiveData<Int>()

    val userLiveData=MutableLiveData<User>()
    val userName: LiveData<String> =  userLiveData.map {
        "${it.firstname} ${it.lastname}"
    }

    val userIdLiveData=MutableLiveData<String>()
    val user: LiveData<User> = userIdLiveData.switchMap {
        Repository.getUser(it)
    }

    init {
        counter.value=countReserved
        userLiveData.value=User("Tom", "Jerry", 5)
    }

    fun plusOne(){
        val count=counter.value?:0
        counter.value=count+1
    }

    fun clear(){
        counter.value=0
    }

    object Repository {
        fun getUser(userId: String): LiveData<User> {
            val liveData = MutableLiveData<User>()
            liveData.value = User(userId, userId, 0)
            return liveData
        }
    }

}