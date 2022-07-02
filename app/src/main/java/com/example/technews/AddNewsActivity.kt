package com.example.technews

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.technews.network.RetrofitInterface
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddNewsActivity : AppCompatActivity() {


    private lateinit var currentPhotoPath: String
    private var selectedImageUri: Uri? = null
    private  var photoFile : File? = null

    private  lateinit var saveButton: MaterialButton
    private  lateinit var newsImageView: ImageView
    private  lateinit var addDescription: EditText
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)



        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        newsImageView = findViewById(R.id.add_news_image)
        saveButton = findViewById(R.id.add_news_button)
        addDescription = findViewById(R.id.add_news_description)



        newsImageView.setOnClickListener {

            openGallery()

        }

        saveButton.setOnClickListener {

            addNews()


        }


    }


    private fun addNews(){

        CoroutineScope(Dispatchers.Main).launch {

            val desc = addDescription.text.toString()

            val userId = sharedPreferences.getString(USER_ID, "").toString()

            /*      val requestFile: RequestBody =
                      photoFile!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())


                   val body = MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile)

                   val description = desc.toRequestBody("multipart/form-data".toMediaTypeOrNull())

                   val creator = userId.toRequestBody("multipart/form-data".toMediaTypeOrNull())*/

            val requestFile: RequestBody =
                photoFile!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())


            val body = MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile)

            val description = desc.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val creator = userId.toRequestBody("multipart/form-data".toMediaTypeOrNull())




            val response = RetrofitInterface.apiInterface.createNews(description,creator,body)

            if(response.isSuccessful){


                val intent = Intent(this@AddNewsActivity,MainActivity::class.java)

                startActivity(intent)



            }
        }
    }




    private fun validation():Boolean{




     return   true
    }



    private fun openGallery(){


        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)



    }



    @Throws(IOException::class)
    fun copyStream(input: InputStream, output: OutputStream) {
        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (input.read(buffer).also { bytesRead = it } != -1) {
            output.write(buffer, 0, bytesRead)
        }
    }




    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }


    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            selectedImageUri = result.data!!.data


            newsImageView.setImageURI(selectedImageUri)


            try {


                try {

                    photoFile = createImageFile()!!

                    Log.d("jjjj", photoFile.toString())

                } catch (ex: IOException) {
                    Log.d("Helloo", "Error occurred while creating the file")
                }
                val inputStream = contentResolver.openInputStream(selectedImageUri!!)

                val fileOutputStream = FileOutputStream(photoFile)
                // Copying
                copyStream(
                    inputStream!!,
                    fileOutputStream!!
                )
                fileOutputStream.close()
                inputStream!!.close()
            } catch (e: Exception) {
                Log.d("", "onActivityResult: $e")
            }
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }



}