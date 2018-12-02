package com.vitali.coboxtestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class FirstViewModel :ViewModel(){

    val title: LiveData<String> = Transformations.switchMap(RssRepo.title){
        val result = MutableLiveData<String>()
        result.value = it
        return@switchMap result
    }

}
