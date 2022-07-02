package com.example.technews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

const val EMAIL ="EMAIL"

class RegistrationActivity1 : AppCompatActivity() {


    private lateinit var emailLayout: TextInputLayout
    private lateinit var emailText: TextInputEditText

    private lateinit var nextButton : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration1)



        emailLayout = findViewById(R.id.my_email_layout)
        emailText = findViewById(R.id.my_email_text)

        nextButton = findViewById(R.id.next_button)


        nextButton.setOnClickListener {


            if(validation()) {

                val intent = Intent(this, RegistrationActivity2::class.java).apply {

                    putExtra(EMAIL, emailText.text.toString())


                }

                startActivity(intent)
            }

        }
    }


    private fun validation():Boolean{


        emailLayout.error = null


        if(emailText.text!!.isEmpty()){

            emailLayout.error="Must not be empty"

            return false
        }

        return true
    }
}