package com.example.technews

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.technews.model.Login
import com.example.technews.network.RetrofitInterface
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import android.util.Patterns.EMAIL_ADDRESS


const val USER_ID = "GENDER"
const val NAME = "NAME"
const val USER_EMAIL = "USER_EMAIL"
const val PASSWORD ="PASSWORD"
const val IMAGE = "IMAGE"
const val PREF_NAME = "LOGIN_PREF_LOL"

const val USER_LOGGED = "USER_LOGGED"

class LoginActivity : AppCompatActivity() {


    private lateinit var emailLayout: TextInputLayout
    private lateinit var emailText: TextInputEditText

    private lateinit var passwordLayout: TextInputLayout
    private lateinit var passwordText: TextInputEditText

    private  lateinit var signInButton: MaterialButton

    private lateinit var forgetPasswordButton : TextView

    private lateinit var signUpButton : TextView

    private  lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        emailLayout = findViewById(R.id.email_layout)
        emailText=findViewById(R.id.email_text)
        passwordLayout = findViewById(R.id.password_layout)
        passwordText = findViewById(R.id.password_text)

        signInButton = findViewById(R.id.sign_in_button)

        forgetPasswordButton = findViewById(R.id.forget_password_button)

        signUpButton = findViewById(R.id.sign_up_button)

        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE)



        signInButton.setOnClickListener {



            if(validation()) {

                val email = emailText.text.toString()
                val password = passwordText.text.toString()

                val login = Login(email, password)

                signIn(login)

            }



        }


        signUpButton.setOnClickListener {

            val intent = Intent(this,RegistrationActivity1::class.java)

            startActivity(intent)

        }

        forgetPasswordButton.setOnClickListener {


            val intent = Intent(this,ForgetPasswordActivity1::class.java)

            startActivity(intent)



        }


     /*     if (sharedPreferences.getBoolean(USER_LOGGED,false)){

              navigate()

          }*/


    }




    private fun validation():Boolean{


        emailLayout.error = null
        passwordLayout.error = null

        if(emailText.text!!.isEmpty()){
            emailLayout.error ="Must not be empty"
            return false
        }


        if(passwordText.text!!.isEmpty()){
            passwordLayout.error ="Must not be empty"
            return false
        }

   /*     if (!EMAIL_ADDRESS.matcher(emailText?.text!!).matches()) {
            emailLayout!!.error = getString(R.string.checkYourEmail)
            return false
        }*/

        return true
    }



    private  fun signIn(login : Login){


        CoroutineScope(Dispatchers.Main).launch{

            val response = RetrofitInterface.apiInterface.signIn(login)

            if(response.code()==200){

                val user = response.body()!!

                sharedPreferences.edit().apply {

                    putString(USER_ID,user._id)
                    putString(NAME,user.name)
                    putString(USER_EMAIL,user.email)
                    putString(PASSWORD,user.password)
                    putString(IMAGE,user.image)
                    putBoolean(USER_LOGGED,true)



                }.apply()

                navigate()

            }

            if(response.code()==404){

                Toast.makeText(this@LoginActivity,"User not found", Toast.LENGTH_SHORT).show()
            }



        }

    }


    private fun navigate(){

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}