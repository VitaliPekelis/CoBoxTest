package com.vitali.coboxtestapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SecondFragment.OnFragmentInteractionListener {


    private var firstFragment:FirstFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                /*replaceFragment(fragment_container.id, fragment = firstFragment ?: FirstFragment.newInstance().also {
                    firstFragment = it
                })*/

                if(firstFragment == null)
                {
                    firstFragment = FirstFragment.newInstance()

                }

                replaceFragment(fragment_container.id, firstFragment!!)


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                SecondFragment.newInstance().also {
                    replaceFragment(fragment_container.id, it)
                }
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

    //------------------------------------------------------------------------------------------------------------------
    // SecondFragment.OnFragmentInteractionListener - implementation
    //------------------------------------------------------------------------------------------------------------------
    override fun onRssItemClick(title: String) {
        firstFragment?.lastRssTitle = title
    }
    override fun onUpdateContent(number:Int) {
        Toast.makeText(this@MainActivity, "$number Content Updated", Toast.LENGTH_SHORT).show()
    }
}
