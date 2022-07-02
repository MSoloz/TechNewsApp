package com.example.technews

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.technews.network.RetrofitInterface
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

class UpdateProfileActivity : AppCompatActivity() {




    private lateinit var currentPhotoPath: String

    private var selectedImageUri: Uri? = null

    private  var photoFile : File? = null

    private  lateinit var sharedPreferences: SharedPreferences

    private lateinit var profileNameText : TextInputEditText

    private lateinit var profileImage: ImageView

    private lateinit var saveButton: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)



        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)


        val id = sharedPreferences.getString(USER_ID,"").toString()

        val name = sharedPreferences.getString(NAME,"").toString()

        val image = sharedPreferences.getString(IMAGE,"").toString()


        profileImage  = findViewById(R.id.change_image)

        profileNameText = findViewById(R.id.change_name_text)

        saveButton = findViewById(R.id.save_changes_button)


        Glide.with(profileImage).load("http://192.168.157.1:3000/img/" + image)
            .fitCenter()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(profileImage)






        profileImage.setOnClickListener {

            openGallery()

        }

        saveButton.setOnClickListener {

            updateProfile()

        }



    }


    private fun updateProfile(){


        /*   val map : MutableMap<String,RequestBody> = HashMap<String,RequestBody>()*/



        CoroutineScope(Dispatchers.Main).launch {


            val newName = profileNameText.text.toString()




            /*      val idRequestBody = sharedPreferences.getString(USER_ID,"").toString()
                      .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                  val  nameRequestBody = newName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                  val emailRequestBody = sharedPreferences.getString(
                      EMAIL_ADDRESS,"").toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                  val passwordRequestBody = sharedPreferences.getString(
                  PASSWORD,"").toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())


                 /* map["_id"] = idRequestBody
                  map["name"] = nameRequestBody
                  map["email"] = emailRequestBody
                  map["password"] = passwordRequestBody*/



                  val requestFile: RequestBody =
                      photoFile!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                  val file = MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile)*/




            val idRequestBody = sharedPreferences.getString(USER_ID,"").toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val  nameRequestBody = newName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val emailRequestBody = sharedPreferences.getString(
            USER_EMAIL,"").toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val passwordRequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),sharedPreferences.getString(
                PASSWORD,"").toString())


            val requestFile: RequestBody =
                photoFile!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            val file = MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile)


            val response = RetrofitInterface.apiInterface.updateUser(idRequestBody,nameRequestBody,emailRequestBody,passwordRequestBody,file)

            if (response.isSuccessful){


                val intent = Intent(this@UpdateProfileActivity,ProfileActivity::class.java)

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


            profileImage.setImageURI(selectedImageUri)


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