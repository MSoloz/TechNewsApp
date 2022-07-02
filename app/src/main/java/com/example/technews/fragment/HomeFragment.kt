package com.example.technews.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technews.AddNewsActivity
import com.example.technews.R
import com.example.technews.adapter.EventAdapter
import com.example.technews.adapter.NewsAdapter
import com.example.technews.model.Event
import com.example.technews.model.News
import com.example.technews.network.RetrofitInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {


    lateinit var recyclerView: RecyclerView

    lateinit var adapter: NewsAdapter

    lateinit var itemsList: MutableList<News>

    lateinit var createButton : FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initView(view)

        initRecyclerView(view)

        fetchNews()

        createButton.setOnClickListener { navigate() }


        return view
    }


    private fun initView(view: View){

        createButton = view.findViewById(R.id.add_news_button)

    }


    private fun initRecyclerView(view: View){

        recyclerView = view.findViewById(R.id.home_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        itemsList = ArrayList()

        adapter = NewsAdapter(arrayListOf())

        recyclerView.adapter = adapter

    }

    private   fun fetchNews() {

        CoroutineScope(Dispatchers.Main).launch {

            val response = RetrofitInterface.apiInterface.getAllNews()

            if(response.isSuccessful){

                val  news = response.body()!!

                adapter.setUpdatedData(news)

            }
        }
    }

    private fun navigate(){

       val intent = Intent(context, AddNewsActivity::class.java)

        startActivity(intent)

    }
}