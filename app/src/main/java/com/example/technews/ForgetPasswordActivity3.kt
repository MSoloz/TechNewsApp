package com.example.technews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ForgetPasswordActivity3 : AppCompatActivity() {

    private lateinit var passwordLayout: TextInputLayout
    private lateinit var passwordText: TextInputEditText

    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var confirmPasswordText: TextInputEditText

    private  lateinit var saveButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password3)

        passwordLayout = findViewById(R.id.forget_password_layout)
        passwordText = findViewById(R.id.forget_password_text)
        saveButton = findViewById(R.id.forget_password_save_button)



        saveButton.setOnClickListener {


            if(validation()){


            }


        }

    }

    private fun validation():Boolean{


        passwordLayout.error = null
        confirmPasswordLayout.error = null


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