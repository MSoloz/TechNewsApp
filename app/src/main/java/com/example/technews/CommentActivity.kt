package com.example.technews

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technews.adapter.*
import com.example.technews.model.AddCommentJsonBody
import com.example.technews.model.Notification
import com.example.technews.model.NotificationRequestBody
import com.example.technews.model.Product
import com.example.technews.network.RetrofitInterface
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentActivity : AppCompatActivity() {



    lateinit var recyclerView: RecyclerView

    lateinit var adapter: CommentAdapter

    lateinit var itemsList: MutableList<Product>

    lateinit var sendButton : ImageView

    lateinit var commentText : TextInputEditText

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE)

        commentText = findViewById(R.id.comment_text)

        sendButton = findViewById(R.id.send_comment_button)


        initRecyclerView()



        val newsId = intent.getStringExtra(POST_ID).toString()

        fetchComments(newsId)


        sendButton.setOnClickListener {

            val comment = commentText.text.toString()

            val userId = sharedPreferences.getString(USER_ID,"").toString()

            val newsOwnerId = intent.getStringExtra(NEWS_OWNER_ID).toString()


            val notificationText ="Commented your post"

            val notification = NotificationRequestBody(newsOwnerId,notificationText,userId)

            Log.d("userId",userId)

            sendComment(newsId,userId,comment)

            addNotification(notification)



        }



    }


    private fun initRecyclerView(){

        recyclerView = findViewById(R.id.comments_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        itemsList = ArrayList()

        adapter = CommentAdapter(arrayListOf())

        recyclerView.adapter = adapter

    }

    private fun fetchComments(id :String){

        CoroutineScope(Dispatchers.Main).launch {

            val response = RetrofitInterface.apiInterface.getAllComments(id)

            if (response.isSuccessful) {

                val comments = response.body()!!

                adapter.setUpdatedData(comments)

            }

        }
    }

    private fun sendComment(newsId:String,userId:String,comment:String){


        CoroutineScope(Dispatchers.Main).launch {

            val request = AddCommentJsonBody(newsId, userId, comment)

            val response = RetrofitInterface.apiInterface.createComment(request)


            if (response.isSuccessful){


                val intent = Intent(this@CommentActivity,MainActivity::class.java)

                startActivity(intent)

            }



        }

    }


    private fun addNotification(notification: NotificationRequestBody) {

        CoroutineScope(Dispatchers.Main).launch {

            val response = RetrofitInterface.apiInterface.createNotification(notification)

            if(response.isSuccessful){


                Log.d("success", response.body().toString())


            }


        }


    }


    override fun onDestroy() {
        super.onDestroy()

        finish()
    }

}