package com.vitali.coboxtestapp

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
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

object RssRepo {

    private val mAppService by lazy {
        AppService.create()
    }

    private val business = MutableLiveData<List<RssItem>>()
    private val entertainmentAndEnvironment = MutableLiveData<List<RssItem>>()
    val title = MutableLiveData<String>()


    fun fetchRss1(): LiveData<List<RssItem>>
    {

        mAppService.getRss1("http://feeds.reuters.com/reuters/businessNews").enqueue(object : Callback<RssFeed> {
            override fun onFailure(call: Call<RssFeed>, t: Throwable)
            {
                business.value = null
                t.printStackTrace()
            }

            override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                if (response.isSuccessful)
                {
                    val rssFeed = response.body()
                    business.value = rssFeed?.items
                }
            }
        })

        return business
    }

    @SuppressLint("CheckResult")
    fun fetchRss2() :LiveData<List<RssItem>>
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
            .onErrorReturn {error->
                entertainmentAndEnvironment.value = null
                error.printStackTrace()
                return@onErrorReturn emptyList()
            }
            .subscribe {
                entertainmentAndEnvironment.value = it
            }

        return entertainmentAndEnvironment
    }
}
