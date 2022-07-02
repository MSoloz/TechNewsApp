package com.example.technews.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technews.R
import com.example.technews.model.User

class PersonAdapter(var itemsList: MutableList<User>):RecyclerView.Adapter<PersonViewHolder>()  {

    fun setUpdatedData(itemsList: MutableList<User>){

        this.itemsList = itemsList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_card,parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {

        val friend = itemsList[position]

        holder.userName.text = friend.name

        Glide.with(holder.userImage).load("http://192.168.157.1:3000/img/" + friend.image)
            .into(holder.userImage)


    }

    override fun getItemCount(): Int {

        return itemsList.size
    }
}

class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val userImage : ImageView = itemView.findViewById<ImageView>(R.id.friend_image)
    val userName : TextView = itemView.findViewById<TextView>(R.id.friend_name)
}
