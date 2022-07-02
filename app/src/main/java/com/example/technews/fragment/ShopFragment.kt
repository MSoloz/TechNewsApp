package com.example.technews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.technews.R
import com.example.technews.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ShopFragment : Fragment() {


    lateinit var tabLayout : TabLayout

    private lateinit var viewPager: ViewPager2

    lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        tabLayout = view.findViewById(R.id.tab_layout)

        viewPager = view.findViewById(R.id.pager)

        adapter = ViewPagerAdapter(this)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            when (position) {
                0 -> {

                    tab.text ="phones"
                }
                1 -> {

                    tab.text ="computers"
                }

                2->{

                    tab.text="accessories"

                }

                else -> {


                }
            }


        }.attach()

        return view
    }

}