package com.vitali.coboxtestapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FirstFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */



class FirstFragment : Fragment() {

    /*private var mListener: OnFragmentInteractionListener? = null*/

    var lastRssTitle:String = ""

    private lateinit var  mViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
            }
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        last_rss_title_tv.text = lastRssTitle
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        /*mListener = null*/
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    /*interface OnFragmentInteractionListener {
        fun onSendClick()
    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment FirstFragment.
         */
        @JvmStatic
        fun newInstance() = FirstFragment()

    }
}
