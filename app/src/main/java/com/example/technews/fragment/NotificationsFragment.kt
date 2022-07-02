package com.example.technews.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technews.PREF_NAME
import com.example.technews.R
import com.example.technews.USER_ID
import com.example.technews.adapter.NotificationAdapter
import com.example.technews.model.Notification
import com.example.technews.network.RetrofitInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotificationsFragment : Fragment() {


    lateinit var recyclerView: RecyclerView

    lateinit var adapter: NotificationAdapter

    lateinit var itemsList: MutableList<Notification>

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_notifications, container, false)


        sharedPreferences = requireActivity().applicationContext.getSharedPreferences(PREF_NAME,MODE_PRIVATE)

        val id = sharedPreferences.getString(USER_ID,"").toString()

        fetchNotifications(id)


        initRecyclerView(view)

        return view
    }


    private fun initRecyclerView(view: View) {

        recyclerView = view.findViewById(R.id.notifications_recycler_view)

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        itemsList = ArrayList()

        adapter = NotificationAdapter(arrayListOf())

        recyclerView.adapter = adapter

    }

    private fun fetchNotifications(id: String) {

        CoroutineScope(Dispatchers.Main).launch {

            val response = RetrofitInterface.apiInterface.getNotifications(id)

            if (response.isSuccessful) {

                val notifications = response.body()!!

                adapter.setUpdatedData(notifications)

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()



    }
}