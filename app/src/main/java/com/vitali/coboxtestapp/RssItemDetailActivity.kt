package com.vitali.coboxtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_rss_item_detail.*
import me.toptas.rssconverter.RssItem

class RssItemDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rss_item_detail)

        val rssItem:RssItem? = intent.extras?.getSerializable(RSS_ITEM_EXTRA) as? RssItem

        rss_item_wv.apply{
            webViewClient = WebViewClient()
            loadUrl(rssItem?.link)
        }
    }
}
