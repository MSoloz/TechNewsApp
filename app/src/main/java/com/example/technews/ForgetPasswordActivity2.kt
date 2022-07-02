package com.example.technews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ForgetPasswordActivity2 : AppCompatActivity() {

    private lateinit var codeConfirmationLayout: TextInputLayout
    private lateinit var codeConfirmationText: TextInputEditText

    private  lateinit var nextButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password2)


        codeConfirmationLayout = findViewById(R.id.forget_password_code_layout)
        codeConfirmationText = findViewById(R.id.forget_password_code_text)

        nextButton = findViewById(R.id.forget_password_next_button2)




        nextButton.setOnClickListener {


            if(validation()) {

                val intent = Intent(this, ForgetPasswordActivity3::class.java)

                startActivity(intent)


            }

        }
    }

    private fun validation():Boolean{


        codeConfirmationLayout.error = null


        if(codeConfirmationText.text!!.isEmpty()){
            codeConfirmationLayout.error ="Must not be empty"
            return false
        }

        return true
    }
}