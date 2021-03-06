package com.example.technews.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.technews.fragment.AccessoriesFragment
import com.example.technews.fragment.ComputersFragment
import com.example.technews.fragment.MobilePhonesFragment

class ViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {



    override fun getItemCount(): Int {

        return  3
    }

    override fun createFragment(position: Int): Fragment {

        return   when (position) {

            0 -> {
                MobilePhonesFragment()
            }
            1 -> {
                ComputersFragment()
            }
            2->{

                AccessoriesFragment()
            }


            else -> {

                MobilePhonesFragment()
            }


        }
    }

}