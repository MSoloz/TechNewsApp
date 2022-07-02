package com.example.technews.adapter

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technews.CommentActivity
import com.example.technews.PREF_NAME
import com.example.technews.R
import com.example.technews.USER_ID
import com.example.technews.model.News
import com.example.technews.model.NotificationRequestBody
import com.example.technews.network.RetrofitInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException


const val POST_ID ="POST_ID"

const val NEWS_OWNER_ID ="NEWS_OWNER_ID"

class NewsAdapter(var itemsList: MutableList<News>) : RecyclerView.Adapter<NewsViewHolder>() {



    lateinit var sharedPreferences: SharedPreferences

    fun setUpdatedData(itemsList: MutableList<News>){

        this.itemsList = itemsList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_card ,parent, false)
        return NewsViewHolder(view)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val news = itemsList[position]

        holder.userName.text = news.creator.name

        holder.description.text = news.description


        Glide.with(holder.newsImage).load("http://192.168.157.1:3000/img/" + news.image)
            .into(holder.newsImage)


        Glide.with(holder.userImage).load("http://192.168.157.1:3000/img/" + news.creator.image)
            .into(holder.userImage)


        Log.d("post id",news._id)


        holder.commentButton.setOnClickListener {




            val intent = Intent(holder.itemView.context, CommentActivity::class.java)

            intent.apply {

                putExtra(POST_ID,news._id)

                putExtra(NEWS_OWNER_ID,news.creator._id)

                Log.d("post id 1",news._id)


            }

            holder.itemView.context.startActivity(intent)


        }




        holder.favoriteButton.setOnClickListener(View.OnClickListener{


            sharedPreferences = holder.itemView.context.getSharedPreferences(PREF_NAME,MODE_PRIVATE)


            val userId = sharedPreferences.getString(USER_ID,"").toString()

            val newsOwnerId =  news.creator._id


            val notificationText ="Liked your post"

            val notification = NotificationRequestBody(newsOwnerId,notificationText,userId)

           try {


               CoroutineScope(Dispatchers.IO).launch {

                   val response = RetrofitInterface.apiInterface.createNotification(notification)


                   if (response.isSuccessful) {

                   }

               }

           }catch (e : JSONException){

               e.printStackTrace()

           }


        })

    }

    override fun getItemCount(): Int {

      return  itemsList.size
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userImage : ImageView = itemView.findViewById<ImageView>(R.id.news_user_image)
    val newsImage : ImageView = itemView.findViewById<ImageView>(R.id.news_image)

    val userName : TextView = itemView.findViewById<TextView>(R.id.news_user_name)
    val description : TextView = itemView.findViewById<TextView>(R.id.news_description)

    val favoriteButton : ImageView = itemView.findViewById<ImageView>(R.id.favoriteButton)
    val commentButton : ImageView = itemView.findViewById<ImageView>(R.id.commentButton)
}