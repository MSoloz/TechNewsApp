package com.example.technews

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.technews.network.RetrofitInterface
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddCommunityActivity : AppCompatActivity() {


    private lateinit var currentPhotoPath: String

    private var selectedImageUri: Uri? = null

    private  var photoFile : File? = null

    lateinit var communityImageView: ImageView

    lateinit var saveButton:MaterialButton

    lateinit var communityNameLayout : TextInputLayout
    lateinit var communityDescriptionLayout : TextInputLayout

    lateinit var communityNameText : TextInputEditText
    lateinit var communityDescriptionText : TextInputEditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_community)


        saveButton = findViewById(R.id.save_community_button)

        communityImageView = findViewById(R.id.add_community_image)


        communityNameLayout = findViewById(R.id.add_community_name_layout)
        communityDescriptionLayout = findViewById(R.id.add_about_community_layout)

        communityNameText = findViewById(R.id.add_community_name_text)
        communityDescriptionText = findViewById(R.id.add_about_community_text)


        communityImageView.setOnClickListener {

            openGallery()

        }


        saveButton.setOnClickListener {

            addCommunity()

        }







    }



    private fun addCommunity(){


        CoroutineScope(Dispatchers.Main).launch{


            val communityName = communityNameText.text.toString()

            val  communityDescription = communityDescriptionText.text.toString()



            /*      val requestFile: RequestBody =
                      photoFile!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())


                  val body = MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile)

                  val name = eventName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                  val description = eventDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                  val address = eventAddress.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                  val description = eventDescription.toRequestBody("multipart/form-data".toMediaTypeOrNull())*/



            val requestFile: RequestBody =
                photoFile!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())


            val body = MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile)

            val name = communityName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val description = communityDescription.toRequestBody("multipart/form-data".toMediaTypeOrNull())


            val response = RetrofitInterface.apiInterface.createCommunity(name,body)

            if(response.isSuccessful){


                val intent = Intent(this@AddCommunityActivity,MainActivity::class.java)

                startActivity(intent)


            }
        }



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

            communityImageView.setImageURI(selectedImageUri)

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
}