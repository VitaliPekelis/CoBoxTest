package com.vitali.coboxtestapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.toptas.rssconverter.RssItem


class EntertainmentAndEnvironmentNewsAdapter(val listener: IAdapterListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var currentData: ArrayList<RssItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

       return  NewsCardViewHolder(layoutInflater.inflate(R.layout.item_rss, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return currentData.size
    }

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, position: Int)
    {
        val item = currentData[h.adapterPosition]

        if(h is NewsCardViewHolder)
        {
            onNewsRssItemViewHolder(h, item)
        }

    }

    private fun onNewsRssItemViewHolder(h: NewsCardViewHolder, item: RssItem?)
    {
        item?.let {
            h.rssTitle.setText(item.title)
            h.rssDate.setText(item.publishDate)
            h.updatePage(it)
        }
    }

    class NewsCardViewHolder(itemView: View, listener: IAdapterListener) : RecyclerView.ViewHolder(itemView)
    {
        val rssTitle: TextView = itemView.findViewById(R.id.rss_item_title_tv)
        val rssDate: TextView = itemView.findViewById(R.id.rss_item_date_tv)
        val separator: View = itemView.findViewById(R.id.rss_item_separator)


        private var currentPage: RssItem? = null

        init
        {
            separator.setBackgroundResource(R.color.colorPrimary)
            itemView.setOnClickListener { _ ->
                currentPage?.let {
                    listener.clickOnItem(it)
                }
            }
        }

        fun updatePage(page: RssItem?)
        {
            currentPage = page
        }
    }
}