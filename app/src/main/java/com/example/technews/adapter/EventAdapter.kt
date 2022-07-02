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
import com.example.technews.EventDetailsActivity
import com.example.technews.R
import com.example.technews.model.Event


const val EVENT_NAME = "EVENT_NAME"
const val EVENT_DATE = "EVENT_DATE"
const val EVENT_ADDRESS ="EVENT_ADDRESS"
const val EVENT_IMAGE ="EVENT_IMAGE"
const val EVENT_DESCRIPTION ="EVENT_DESCRIPTION"
const val EVENT_ADDRESS_LATITUDE ="EVENT_ADDRESS_LATITUDE"
const val EVENT_ADDRESS_LONGITUDE ="EVENT_ADDRESS_LONGITUDE"



class EventAdapter(var itemsList: MutableList<Event>):RecyclerView.Adapter<EventViewHolder>(){

    fun setUpdatedData(itemsList: MutableList<Event>){

        this.itemsList = itemsList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_card,parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        var event = itemsList[position]

        holder.eventName.text = event.name

        holder.eventTime.text = event.date

        holder.eventLocation.text = event.address

        Glide.with(holder.eventImage).load("http://192.168.157.1:3000/img/" + event.image)
            .into(holder.eventImage)

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, EventDetailsActivity::class.java).apply {


                putExtra(EVENT_NAME,event.name)
                putExtra(EVENT_ADDRESS,event.address)
                putExtra(EVENT_DATE,event.date)
                putExtra(EVENT_DESCRIPTION,event.description)
                putExtra(EVENT_IMAGE,event.image)
                putExtra(EVENT_ADDRESS_LATITUDE,event.latitude)
                putExtra(EVENT_ADDRESS_LONGITUDE,event.longitude)

            }


            holder.itemView.context.startActivity(intent)

        }





    }

    override fun getItemCount(): Int {

        return itemsList.size
    }

}

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    val eventImage : ImageView = itemView.findViewById<ImageView>(R.id.event_image)
    val eventName : TextView = itemView.findViewById<TextView>(R.id.event_name)
    val eventTime : TextView = itemView.findViewById<TextView>(R.id.event_time)
    val eventLocation : TextView = itemView.findViewById<TextView>(R.id.event_location)

}