package com.vitali.coboxtestapp

import me.toptas.rssconverter.RssItem

interface IAdapterListener {
    fun clickOnItem(rssItem: RssItem)
}