package com.example.technews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.technews.R
import com.example.technews.model.Message

class MessageAdapter(var itemsList :MutableList<Message>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun setUpdatedData(itemsList: MutableList<Message>){

        this.itemsList = itemsList

        notifyDataSetChanged()

    }


    class ChatUserViewHolder(itemView : View):  RecyclerView.ViewHolder(itemView) {

        val message: TextView = itemView.findViewById<TextView>(R.id.user_message)

    }


    class ChatPartnerViewHolder(itemView : View):  RecyclerView.ViewHolder(itemView) {

        val message: TextView = itemView.findViewById<TextView>(R.id.partner_message)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return if (viewType == 0) {


            val  view = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_user_card, parent, false)
            ChatUserViewHolder(view)


        }else {

            val  view = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_partner_card, parent, false)
            ChatPartnerViewHolder(view)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItemViewType(position)==0){

            holder as ChatUserViewHolder

            holder.message.text = itemsList[position].text

        }else {

            holder as ChatPartnerViewHolder

            holder.message.text = itemsList[position].text

        }

    }

    override fun getItemCount(): Int {

        return itemsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return itemsList[position].viewType
    }


}