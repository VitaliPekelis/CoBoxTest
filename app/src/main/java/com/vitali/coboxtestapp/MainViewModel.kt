package com.vitali.coboxtestapp

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
        rssRepo.fetchRss1(businessNews)
    }

    fun fetchEntertainmentAndEnvirNews()
    {
        rssRepo.fetchRss2(entertainmentAndEnvironmentNews)
    }


    override fun onCleared() {
        super.onCleared()
    }

}