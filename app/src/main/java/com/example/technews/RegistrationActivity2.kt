package com.example.technews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegistrationActivity2 : AppCompatActivity() {


    private lateinit var codeLayout: TextInputLayout
    private lateinit var codeText: TextInputEditText

    private lateinit var nextButton : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration2)


        codeLayout = findViewById(R.id.code_layout)
        codeText = findViewById(R.id.code_text)

        nextButton = findViewById(R.id.next_button2)


        nextButton.setOnClickListener {


            if (validation()) {

                val intent = Intent(this, RegistrationActivity3::class.java).apply {

                    putExtra(EMAIL, intent.getStringExtra(EMAIL))


                }

                startActivity(intent)


            }

        }
    }


    private fun validation():Boolean{


        codeLayout.error = null


        if(codeText.text!!.isEmpty()){
            codeLayout.error ="Must not be empty"
            return false
        }



        return true
    }
}
