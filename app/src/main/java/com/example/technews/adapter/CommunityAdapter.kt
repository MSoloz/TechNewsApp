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
import com.example.technews.CommentActivity
import com.example.technews.CommunityActivity
import com.example.technews.R
import com.example.technews.model.Community

class CommunityAdapter(var itemsList: MutableList<Community>):RecyclerView.Adapter<CommunityViewHolder>() {

    fun setUpdatedData(itemsList: MutableList<Community>){

        this.itemsList = itemsList

        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.community_card,parent, false)
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {

        var community = itemsList[position]

        holder.communityName.text = community.name


        Glide.with(holder.communityImage).load("http://192.168.157.1:3000/img/" + community.image)
            .into(holder.communityImage)


        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, CommunityActivity::class.java)


            holder.itemView.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {

      return  itemsList.size
    }
}

class CommunityViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val communityImage : ImageView = itemView.findViewById<ImageView>(R.id.community_image)
    val communityName : TextView = itemView.findViewById<TextView>(R.id.community_name)
}
