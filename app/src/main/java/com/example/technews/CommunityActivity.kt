package com.example.technews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.technews.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class CommunityActivity : AppCompatActivity() {



    private lateinit var  bottomNavigation :BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)



        bottomNavigation = findViewById(R.id.bottom_navigation_view)


        bottomNavigation.setOnItemSelectedListener {


            when (it.itemId) {
                R.id.navigation_home -> {
                    changeFragment(CommunityHomeFragment(),"")
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_group -> {
                    changeFragment(CommunityGroupFragment(),"")
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_message -> {
                   changeFragment(CommunityChatFragment(),"")
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_account -> {
                    changeFragment(CommunityAccountFragment(),"")
                    return@setOnItemSelectedListener true
                }
            }
            false


        }




        supportFragmentManager.beginTransaction().add(R.id.fragment_container_view, CommunityHomeFragment()).commit()

    }



    private fun changeFragment(fragment: Fragment, name: String) {

        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment).addToBackStack("").commit()

    }



}