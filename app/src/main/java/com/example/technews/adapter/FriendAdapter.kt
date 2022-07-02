package com.example.technews.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technews.ChatActivity
import com.example.technews.R
import com.example.technews.model.User


const val RECEIVER_ID = "RECEIVER_ID"


class FriendAdapter(var itemsList: MutableList<User>):RecyclerView.Adapter<FriendViewHolder>() {

    fun setUpdatedData(itemsList: MutableList<User>){

        this.itemsList = itemsList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_card,parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        val friend = itemsList[position]
        holder.userName.text = friend.name
        Glide.with(holder.userImage).load("http://192.168.157.1:3000/img/" + friend.image)
            .into(holder.userImage)

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, ChatActivity::class.java).apply {

                putExtra(RECEIVER_ID,friend._id)

            }

            holder.itemView.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {

        return itemsList.size
    }

}

class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userImage : ImageView = itemView.findViewById<ImageView>(R.id.friend_chat_image)
    val userName : TextView = itemView.findViewById<TextView>(R.id.friend_chat_name)
}