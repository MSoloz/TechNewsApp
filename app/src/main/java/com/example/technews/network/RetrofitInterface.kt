package com.example.technews.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInterface {

    private const val BASE_URL = "http://192.168.157.1:3000"

    private val retrofit by lazy {


        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val apiInterface : ApiInterface by lazy {

        retrofit.create(ApiInterface::class.java)

    }
}