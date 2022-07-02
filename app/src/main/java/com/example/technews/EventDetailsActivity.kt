package com.example.technews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.technews.adapter.*
import com.google.android.material.button.MaterialButton

class EventDetailsActivity : AppCompatActivity() {


    lateinit var  eventName : TextView
    lateinit var  eventAddress : TextView
    lateinit var  eventDate : TextView
    lateinit var  eventDescription : TextView

    lateinit var eventImage: ImageView


    lateinit var showLocationButton :MaterialButton




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)


        eventName = findViewById(R.id.event_name_details)
        eventAddress = findViewById(R.id.event_address_details)
        eventDate = findViewById(R.id.event_date_details)
        eventDescription = findViewById(R.id.event_description_details)
        eventImage = findViewById(R.id.event_image_details)
        showLocationButton = findViewById(R.id.show_location_button)


        val name = intent.getStringExtra(EVENT_NAME).toString()
        val address = intent.getStringExtra(EVENT_ADDRESS).toString()
        val date = intent.getStringExtra(EVENT_DATE).toString()
        val description = intent.getStringExtra(EVENT_DESCRIPTION).toString()
        val image = intent.getStringExtra(EVENT_IMAGE).toString()

        eventName.text = name
        eventAddress.text = address
        eventDate.text = date
        eventDescription.text =description


        Glide.with(eventImage).load("http://192.168.157.1:3000/img/" + image)
            .into(eventImage)


        showLocationButton.setOnClickListener {


           navigate()

        }
    }

    private fun navigate(){

       val intent = Intent(this,ShowLocationActivity::class.java).apply {

          putExtra(EVENT_ADDRESS_LATITUDE,intent.getStringExtra(EVENT_ADDRESS_LATITUDE))
           putExtra(EVENT_ADDRESS_LONGITUDE,intent.getStringExtra(EVENT_ADDRESS_LONGITUDE))



       }

        startActivity(intent)

    }
}