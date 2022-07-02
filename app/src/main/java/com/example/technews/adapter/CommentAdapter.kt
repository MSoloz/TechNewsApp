package com.example.technews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technews.R
import com.example.technews.model.Comment
import com.example.technews.model.Community


class CommentAdapter(var itemsList: MutableList<Comment>):RecyclerView.Adapter<CommentViewHolder>(){



    fun setUpdatedData(itemsList: MutableList<Comment>){

        this.itemsList = itemsList

        notifyDataSetChanged()

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_card,parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val comment = itemsList[position]

        holder.userName.text = comment.user.name

        holder.comment.text  = comment.comment

        Glide.with(holder.userImage).load("http://192.168.157.1:3000/img/" + comment.user.image)
            .into(holder.userImage)

    }

    override fun getItemCount(): Int {

        return itemsList.size
    }


}

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    val userImage : ImageView = itemView.findViewById<ImageView>(R.id.comment_user_image)

    val userName : TextView = itemView.findViewById<TextView>(R.id.comment_user_name)
    val comment : TextView = itemView.findViewById<TextView>(R.id.news_user_comment)
}