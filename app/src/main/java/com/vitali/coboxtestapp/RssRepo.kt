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

class RssRepo {

    private val appService by lazy {
        AppService.create()
    }

    fun getRss1(): LiveData<List<RssItem>> {

        val data = MutableLiveData<List<RssItem>>()

        appService.getRssLiveData("http://feeds.reuters.com/reuters/businessNews").enqueue(object : Callback<RssFeed> {
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

        return data
    }


    /*BiFunction<RssFeed, RssFeed, LiveData<List<RssItem>>>()
            { rss1, rss2 ->
                val list1 = rss1.items
                val list2 = rss2.items

                val resultList = mutableListOf<RssItem>().apply {
                    addAll(list1 as MutableList)
                    addAll(list2 as MutableList)
                }

                return@BiFunction MutableLiveData<List<RssItem>>().apply {
                    value = resultList
                }
            })*/

    @SuppressLint("CheckResult")
    fun getRss2(): LiveData<List<RssItem>>
    {
        val data = MutableLiveData<List<RssItem>>()

        val first = appService.getRssObservable("http://feeds.reuters.com/reuters/entertainment").subscribeOn(Schedulers.io())
        val second = appService.getRssObservable("http://feeds.reuters.com/reuters/environment").subscribeOn(Schedulers.io())

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

        return data
    }

    /*interface MyCallback<T>{
        fun onSuccess(result: T)
        fun onError(t: Throwable)
    }*/
}
