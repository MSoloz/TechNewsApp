package com.example.technews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technews.R

import com.example.technews.adapter.FriendAdapter

import com.example.technews.model.Event
import com.example.technews.network.RetrofitInterface

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MessagesFragment : Fragment() {


    lateinit var recyclerView: RecyclerView

    lateinit var adapter: FriendAdapter

    lateinit var itemsList: MutableList<Event>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_messages, container, false)

        initRecyclerView(view)

        fetchUsers()

        return  view
    }



    private fun initRecyclerView(view: View){

        recyclerView = view.findViewById(R.id.messages_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        itemsList = ArrayList()

        adapter = FriendAdapter(arrayListOf())

        recyclerView.adapter = adapter

    }

    private   fun fetchUsers() {

        CoroutineScope(Dispatchers.Main).launch {

            val response = RetrofitInterface.apiInterface.getAllUsers()

            if(response.isSuccessful){

                val  users = response.body()!!

                adapter.setUpdatedData(users)

            }
        }
    }

}