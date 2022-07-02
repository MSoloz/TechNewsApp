package com.example.technews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technews.R
import com.example.technews.adapter.ProductAdapter
import com.example.technews.model.Product
import com.example.technews.network.RetrofitInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MobilePhonesFragment : Fragment() {


    lateinit var recyclerView: RecyclerView

    lateinit var adapter: ProductAdapter

    lateinit var itemsList: MutableList<Product>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_mobile_phones, container, false)

        initRecyclerView(view)

        fetchProducts()

        return view
    }


    private fun initRecyclerView(view: View){

        recyclerView = view.findViewById(R.id.mobile_phones_recycler_view)

        recyclerView.layoutManager = GridLayoutManager(context,2)

        itemsList = ArrayList()

       adapter = ProductAdapter(arrayListOf())

        recyclerView.adapter = adapter

    }

    private fun fetchProducts(){


        CoroutineScope(Dispatchers.Main).launch{


            val response = RetrofitInterface.apiInterface.getAllProducts()

            if(response.isSuccessful){

                val products = response.body()!!

                adapter.setUpdatedData(products)

            }
        }
    }

}