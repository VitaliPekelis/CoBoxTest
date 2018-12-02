package com.vitali.coboxtestapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SecondFragment.OnFragmentInteractionListener {


    private var mFirstFragment:FirstFragment? = null
    private var mSecondFragment:SecondFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                if(mFirstFragment == null)
                {
                    mFirstFragment = FirstFragment.newInstance()

                }

                replaceFragment(fragment_container.id, mFirstFragment!!)


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

                if(mSecondFragment == null)
                {
                    mSecondFragment = SecondFragment.newInstance()

                }

                addFragment(fragment_container.id, mSecondFragment!!)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home

    }

    override fun onBackPressed() {
        val isEmpty = isFragmentStackEmpty()
        if(!isEmpty) popFragmentBackStack()
        super.onBackPressed()
    }

    //------------------------------------------------------------------------------------------------------------------
    // SecondFragment.OnFragmentInteractionListener - implementation
    //------------------------------------------------------------------------------------------------------------------
    override fun onUpdateContent(number:Int) {
        Toast.makeText(this@MainActivity, "$number Content Updated", Toast.LENGTH_SHORT).show()
    }
}
