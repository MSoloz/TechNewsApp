package com.example.technews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technews.R
import com.example.technews.model.Notification

class NotificationAdapter(var itemsList: MutableList<Notification>):RecyclerView.Adapter<NotificationViewHolder>() {

    fun setUpdatedData(itemsList: MutableList<Notification>){

        this.itemsList = itemsList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_card,parent, false)
        return NotificationViewHolder(view)

    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {


        val notification = itemsList[position]

        holder.userName.text = notification.creator.name
        holder.description.text = notification.description

        Glide.with(holder.userImage).load("http://192.168.157.1:3000/img/" + notification.creator.image)
            .into(holder.userImage)
    }

    override fun getItemCount(): Int {

        return itemsList.size
    }

}

class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userImage : ImageView = itemView.findViewById<ImageView>(R.id.notification_user_image)

    val userName : TextView = itemView.findViewById<TextView>(R.id.notification_user_name)
    val description : TextView = itemView.findViewById<TextView>(R.id.notification_text)
}