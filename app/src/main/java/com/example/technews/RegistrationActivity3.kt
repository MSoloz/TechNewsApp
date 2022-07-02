package com.example.technews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.technews.model.User
import com.example.technews.network.RetrofitInterface
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationActivity3 : AppCompatActivity() {


    private lateinit var nameLayout: TextInputLayout
    private lateinit var nameText: TextInputEditText

    private lateinit var passwordLayout: TextInputLayout
    private lateinit var passwordText: TextInputEditText

    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var confirmPasswordText: TextInputEditText


    private lateinit var saveButton : MaterialButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration3)

        nameLayout = findViewById(R.id.name_layout)
        nameText = findViewById(R.id.name_text)

        passwordLayout = findViewById(R.id.my_password_layout)
        passwordText = findViewById(R.id.my_password_text)


        confirmPasswordLayout = findViewById(R.id.confirm_password_layout)
        confirmPasswordText = findViewById(R.id.confirm_password_text)


        saveButton = findViewById(R.id.save_button)



        saveButton.setOnClickListener {

            if (validation()) {

                val email = intent.getStringExtra(EMAIL).toString()

                val name = nameText.text.toString()

                val password = passwordText.text.toString()

                val user = User("",name, email, password,"user.jpg")

                signUp(user)

            }


        }


    }

    private  fun signUp(user : User){



        CoroutineScope(Dispatchers.Main).launch {


            val response = RetrofitInterface.apiInterface.signUp(user)


            if (response.isSuccessful) {


                val intent = Intent(this@RegistrationActivity3, MainActivity::class.java)

                startActivity(intent)


            }

        }

    }





    private fun validation():Boolean{


        nameLayout.error = null
        passwordLayout.error = null
        confirmPasswordLayout.error = null

        if(nameText.text!!.isEmpty()){
            nameText.error ="Must not be empty"
            return false
        }


        if(passwordText.text!!.isEmpty()){
            passwordLayout.error ="Must not be empty"
            return false
        }

        if(confirmPasswordText.text!!.isEmpty()){
            confirmPasswordLayout.error ="Must not be empty"
            return false
        }


        return true
    }
}