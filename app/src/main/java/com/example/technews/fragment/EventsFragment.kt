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
import com.example.technews.AddEventActivity
import com.example.technews.R
import com.example.technews.adapter.EventAdapter
import com.example.technews.model.Event
import com.example.technews.network.RetrofitInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EventsFragment : Fragment() {



    lateinit var recyclerView: RecyclerView

    lateinit var adapter: EventAdapter

    lateinit var itemsList: MutableList<Event>

    lateinit var createButton : FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_events, container, false)


        initView(view)

        initRecyclerView(view)

        fetchEvents()



        createButton.setOnClickListener { navigate() }



        return view
    }






    private fun initView(view: View){

        createButton = view.findViewById(R.id.add_event_button)

    }


    private fun initRecyclerView(view: View){

        recyclerView = view.findViewById(R.id.events_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        itemsList = ArrayList()

        adapter = EventAdapter(arrayListOf())

        recyclerView.adapter = adapter

    }



    private  fun fetchEvents(){


        CoroutineScope(Dispatchers.Main).launch{

            val response = RetrofitInterface.apiInterface.getAllEvents()


            if(response.isSuccessful){

                val events = response.body()!!

                Log.d("time",events.toString())

                adapter.setUpdatedData(events)

            }
        }
    }

    private fun navigate(){

        val intent = Intent(context, AddEventActivity::class.java)

       startActivity(intent)


    }




}