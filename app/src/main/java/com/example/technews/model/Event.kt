package com.example.technews.model

import com.google.gson.annotations.SerializedName

data class Event (

    val name:String,
    val image:String,
    val address:String,
    @SerializedName("eventDate")
    val date :String,
    val description:String,
    val latitude:String,
    val longitude:String,

        )