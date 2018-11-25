package com.vitali.coboxtestapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_second.*
import me.toptas.rssconverter.RssItem

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FirstFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */


const val RSS_ITEM_EXTRA= "rss_item_extra"

class SecondFragment : Fragment(), IAdapterListener {

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var  mViewModel : MainViewModel
    private val mAdapterB = BusinessNewsAdapter(this)
    private val mAdapterE = EntertainmentAndEnvironmentNewsAdapter(this)

    private val mDelay = 5000L //milliseconds
    private var mHandler:Handler? = Handler(Looper.getMainLooper())
    private var mRunnable:Runnable? = null


    init{
        mRunnable = Runnable {
            mViewModel.apply {
                fetchEntertainmentAndEnvirNews()
                fetchBusinessNews()
            }

            mHandler?.postDelayed(this.mRunnable, mDelay)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            mViewModel = ViewModelProviders.of(this@SecondFragment).get(MainViewModel::class.java).apply {

                fetchEntertainmentAndEnvirNews()
                fetchBusinessNews()

                businessNews.observe(this@SecondFragment, Observer {
                    //update UI
                    mAdapterB.apply {
                        currentData.clear()
                        currentData = it as ArrayList<RssItem>
                        notifyDataSetChanged()
                        mListener?.onUpdateContent(1)
                    }
                })


                entertainmentAndEnvironmentNews.observe(this@SecondFragment, Observer {
                    //update UI
                    mAdapterE.apply {
                        currentData.clear()
                        currentData = it as ArrayList<RssItem>
                        notifyDataSetChanged()
                        mListener?.onUpdateContent(2)
                    }
                })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        business_news_rcv.adapter = this.mAdapterB
        entert_and_envirom_rcv.adapter = this.mAdapterE
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        mHandler = null
        mRunnable = null
    }

    override fun onResume() {
        super.onResume()
        mHandler?.postDelayed(mRunnable, mDelay)
    }

    override fun onPause() {
        mHandler?.removeCallbacks(mRunnable)
        super.onPause()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {
        fun onRssItemClick(title:String)
        fun onUpdateContent(number:Int)
    }


    //----------------------------------------------------------------------------------------------
    // IAdapterListener - implementation
    //----------------------------------------------------------------------------------------------
    override fun clickOnItem(rssItem: RssItem)
    {
        mListener?.onRssItemClick(rssItem.title ?: "")

        val intent = Intent(context, RssItemDetailActivity::class.java)
        intent.putExtra(RSS_ITEM_EXTRA, rssItem)
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment FirstFragment.
         */
        @JvmStatic
        fun newInstance() = SecondFragment()

    }
}
