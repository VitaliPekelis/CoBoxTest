package com.vitali.coboxtestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.toptas.rssconverter.RssItem

class MainViewModel: ViewModel()
{
    private val rssRepo:RssRepo by lazy{
        RssRepo()
    }

    var businessNews: MutableLiveData<List<RssItem>> = MutableLiveData()
    var entertainmentAndEnvironmentNews:MutableLiveData<List<RssItem>> = MutableLiveData()

    fun fetchBusinessNews()
    {
        rssRepo.getRss1(businessNews)
    }

    fun fetchEntertainmentAndEnvirNews()
    {
        rssRepo.getRss2(entertainmentAndEnvironmentNews)
    }


    override fun onCleared() {
        super.onCleared()
    }

}