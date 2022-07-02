package com.example.technews

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technews.adapter.MessageAdapter
import com.example.technews.adapter.RECEIVER_ID
import com.example.technews.model.Message
import com.example.technews.model.MessageBody
import com.example.technews.network.SocketHandler
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import io.socket.client.Socket
import io.socket.emitter.Emitter


class ChatActivity : AppCompatActivity() {


    lateinit var adapter: MessageAdapter

    lateinit var recyclerView: RecyclerView

    private lateinit var sendMessageEditText: TextInputEditText

    private lateinit var sendMessageButton: ImageView

    lateinit var itemsList: MutableList<Message>

    private lateinit var socket: Socket

    private val gson: Gson = Gson()

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        sendMessageEditText = findViewById(R.id.message_text)

        sendMessageButton = findViewById(R.id.send_message_button)

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        initRecyclerView()


        SocketHandler.setSocket()

        socket = SocketHandler.getSocket()



        socket.connect()


        socket.emit("addUser",sharedPreferences.getString(USER_ID,""))


        sendMessageButton.setOnClickListener {

            sendMessage()

        }


        socket.on("getMessage",onMessage)




    }



    private fun initRecyclerView(){


        recyclerView = findViewById(R.id.chat_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        itemsList = ArrayList()

        adapter = MessageAdapter(arrayListOf())

        recyclerView.adapter = adapter

    }




    private fun sendMessage() {

        val message: String = sendMessageEditText.text.toString()

        sendMessageEditText.setText("")

        val id = sharedPreferences.getString(USER_ID,"").toString()

        socket.emit("addUser",id)


        val messageBody = MessageBody(id,intent.getStringExtra(RECEIVER_ID).toString(),message)

        val jsonData = gson.toJson(messageBody)

        socket.emit("sendMessage",jsonData)

        val m = Message("atef",message,0)

        addItemToRecyclerView(m)


    }


    private val onMessage = Emitter.Listener { args ->

        if (args[0] != null) {

            val data = args[0] as String


            Log.i("hello ", data)

            runOnUiThread {

                val m = Message("atef", data,1)

                addItemToRecyclerView(m)

            }

        }
    }



    private fun addItemToRecyclerView(message: Message) {

        //Since this function is inside of the listener,
        // You need to do it on UIThread!

        itemsList.add(message)

        adapter.setUpdatedData(itemsList)
        sendMessageEditText.setText("")
        recyclerView.scrollToPosition(itemsList.size - 1) //move focus on last message


    }


}