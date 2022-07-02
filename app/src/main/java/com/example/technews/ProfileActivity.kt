package com.example.technews

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.technews.adapter.NewsAdapter
import com.example.technews.model.News
import com.example.technews.network.RetrofitInterface
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    lateinit var adapter : NewsAdapter

    private lateinit var itemsList : MutableList<News>

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var profileImage: ImageView

    lateinit var modifyProfileButton: MaterialButton

    lateinit var profileName : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        profileImage = findViewById(R.id.profile_image)

        modifyProfileButton = findViewById(R.id.modify_profile_button)

        profileName = findViewById(R.id.profile_name)

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val id = sharedPreferences.getString(USER_ID,"").toString()

        val name = sharedPreferences.getString(NAME,"").toString()

        val image = sharedPreferences.getString(IMAGE,"").toString()



        Glide.with(profileImage).load("http://192.168.157.1:3000/img/" + image)
            .fitCenter()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(profileImage)

        profileName.text = name


        initRecyclerView()

        fetchUserNews(id)

        modifyProfileButton.setOnClickListener {

            val intent = Intent(this,UpdateProfileActivity::class.java)

            startActivity(intent)

        }

    }



    private fun initRecyclerView(){


        recyclerView = findViewById(R.id.profile_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        itemsList = ArrayList()

        adapter = NewsAdapter(arrayListOf())

        recyclerView.adapter = adapter

    }

    private   fun fetchUserNews(id:String) {

        CoroutineScope(Dispatchers.Main).launch {

            val response = RetrofitInterface.apiInterface.getNewsByUserId(id)

            if(response.isSuccessful){

                val  news = response.body()!!

                adapter.setUpdatedData(news)

            }
        }
    }
}