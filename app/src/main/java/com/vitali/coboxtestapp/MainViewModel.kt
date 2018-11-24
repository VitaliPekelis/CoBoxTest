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

    var businessNews: LiveData<List<RssItem>>? = null
    var entertainmentAndEnvironmentNews:LiveData<List<RssItem>>? = null

    fun fetchBusinessNews()
    {
        val news = rssRepo.getRss1()
        businessNews = news
    }

    fun fetchEntertainmentAndEnvirNews()
    {
        entertainmentAndEnvironmentNews = rssRepo.getRss2()
    }

}