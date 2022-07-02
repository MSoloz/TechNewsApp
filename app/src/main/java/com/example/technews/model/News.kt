package com.example.technews.model

data class News (

    val _id :String,
    val description :String,
    val image:String,
    val comments : MutableList<Comment>,
    val likes : MutableList<Like>,
    val creator:User


)