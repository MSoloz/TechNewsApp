package com.example.technews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.technews.R
import com.example.technews.adapter.EventAdapter


class SettingsFragment : Fragment() {


    lateinit var switch :SwitchCompat

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        initView(view)

        
        switch.setOnCheckedChangeListener { buttonView, isChecked ->

            if(isChecked){

               AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }




        }
        
        

        return view
    }


    private fun initView(view:View){

        switch = view.findViewById(R.id.theme_switcher)



    }



}