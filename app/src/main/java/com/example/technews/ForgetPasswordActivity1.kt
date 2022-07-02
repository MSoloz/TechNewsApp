package com.example.technews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ForgetPasswordActivity1 : AppCompatActivity() {


    private lateinit var emailLayout: TextInputLayout
    private lateinit var emailText: TextInputEditText

    private  lateinit var nextButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password1)



        emailLayout = findViewById(R.id.forget_password_email_layout)
        emailText = findViewById(R.id.forget_password_email_text)

        nextButton = findViewById(R.id.forget_password_next_button1)


        nextButton.setOnClickListener {


            if(validation()) {

                val intent = Intent(this, ForgetPasswordActivity2::class.java)

                startActivity(intent)

            }
        }

    }


    private fun validation():Boolean{


        emailLayout.error = null


        if(emailText.text!!.isEmpty()){
            emailLayout.error ="Must not be empty"
            return false
        }

        return true
    }



}