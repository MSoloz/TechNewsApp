package com.example.technews.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technews.AddCommunityActivity
import com.example.technews.R
import com.example.technews.adapter.CommunityAdapter
import com.example.technews.adapter.EventAdapter
import com.example.technews.model.Community
import com.example.technews.model.Event
import com.example.technews.network.RetrofitInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CommunitiesFragment : Fragment() {


    lateinit var recyclerView: RecyclerView

    lateinit var adapter: CommunityAdapter

    lateinit var itemsList: MutableList<Community>

    lateinit var createButton : FloatingActionButton


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_communities, container, false)

        initView(view)

         initRecyclerView(view)

        fetchCommunities()


        createButton.setOnClickListener {

            val intent = Intent(context,AddCommunityActivity::class.java)

            startActivity(intent)

        }



        return view
    }



    private fun initView(view: View){

        createButton = view.findViewById(R.id.add_community_button)

    }


    private  fun fetchCommunities(){


        CoroutineScope(Dispatchers.Main).launch{

            val response = RetrofitInterface.apiInterface.getAllCommunities()


            if(response.isSuccessful){

                val communities = response.body()!!

                Log.d("time",communities.toString())

                adapter.setUpdatedData(communities)

            }
        }
    }

    private fun initRecyclerView(view: View){

        recyclerView = view.findViewById(R.id.communities_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        itemsList = ArrayList()

        adapter = CommunityAdapter(arrayListOf())

        recyclerView.adapter = adapter

    }



}