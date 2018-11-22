package com.vitali.coboxtestapp

import me.toptas.rssconverter.RssFeed
import retrofit2.Call

object NetworkHandler {

    private val appService by lazy {
        AppService.create()
    }

    fun getRss(url: String) : Call<RssFeed> {
        return appService.getRss(url)
    }
}