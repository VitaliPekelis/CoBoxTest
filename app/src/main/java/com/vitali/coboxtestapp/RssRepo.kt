package com.vitali.coboxtestapp

import android.annotation.SuppressLint
import me.toptas.rssconverter.RssItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.toptas.rssconverter.RssFeed
import io.reactivex.functions.BiFunction

class RssRepo {

    private val mAppService by lazy {
        AppService.create()
    }

    fun getRss1(data:MutableLiveData<List<RssItem>>)
    {
        mAppService.getRss1("http://feeds.reuters.com/reuters/businessNews").enqueue(object : Callback<RssFeed> {
            override fun onFailure(call: Call<RssFeed>, t: Throwable)
            {
                data.value = null
            }

            override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                if (response.isSuccessful)
                {
                    val rssFeed = response.body()
                    data.value = rssFeed?.items
                }
            }
        })
    }

    @SuppressLint("CheckResult")
    fun getRss2(data:MutableLiveData<List<RssItem>>)
    {
        val first = mAppService.getRss2("http://feeds.reuters.com/reuters/entertainment").subscribeOn(Schedulers.io())
        val second = mAppService.getRss2("http://feeds.reuters.com/reuters/environment").subscribeOn(Schedulers.io())

        val observer = Observable.zip(
            first,
            second,
            BiFunction<RssFeed, RssFeed, List<RssItem>>()
            { rss1, rss2 ->

                return@BiFunction mutableListOf<RssItem>().apply {
                    addAll(rss1.items as MutableList)
                    addAll(rss2.items as MutableList)
                }
            })

        observer.observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                data.value = null
                return@onErrorReturn mutableListOf<RssItem>()
            }
            .subscribe {
                data.value = it
            }
    }
}
