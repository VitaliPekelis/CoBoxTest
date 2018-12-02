package com.vitali.coboxtestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import me.toptas.rssconverter.RssItem

class SecondViewModel: ViewModel()
{


    val businessNews: LiveData<List<RssItem>> by lazy {
        Transformations.switchMap(RssRepo.fetchRss1()) {
            val result = MutableLiveData<List<RssItem>>()
            result.value = it
            return@switchMap result
        }
    }

    val entertainmentAndEnvironmentNews: LiveData<List<RssItem>> by lazy {
        Transformations.switchMap(RssRepo.fetchRss2()) {
            val result = MutableLiveData<List<RssItem>>()
            result.value = it
            return@switchMap result
        }
    }

    fun fetchBusinessNews()
    {
        RssRepo.fetchRss1()
    }

    fun fetchEntertainmentAndEnvirNews()
    {
        RssRepo.fetchRss2()
    }

    fun onRssItemClick(title: String) {
        RssRepo.title.value = title
    }

    override fun onCleared() {
        super.onCleared()
    }



}