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
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

const val EVENT_NAME ="EVENT_NAME"
const val EVENT_DATE ="EVENT_DATE"

class AddEventActivity : AppCompatActivity() {


    private lateinit var currentPhotoPath: String

    private var selectedImageUri: Uri? = null

    private  var photoFile : File? = null

    lateinit var eventNameLayout : TextInputLayout
    lateinit var eventDateLayout : TextInputLayout
    lateinit var eventAddressLayout : TextInputLayout
    lateinit var eventDescriptionLayout : TextInputLayout

    lateinit var eventNameText : TextInputEditText
    lateinit var eventDateText : TextInputEditText
    lateinit var eventAddressText : TextInputEditText
    lateinit var eventDescriptionText : TextInputEditText

    lateinit var eventImageView: ImageView

    lateinit var saveButton : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)




        eventNameLayout = findViewById(R.id.add_event_name_layout)
        eventNameText = findViewById(R.id.add_event_name_text)

        eventDateLayout = findViewById(R.id.add_event_date_layout)
        eventDateText = findViewById(R.id.add_event_date_text)

        eventAddressLayout = findViewById(R.id.add_event_address_layout)
        eventAddressText = findViewById(R.id.add_event_address_text)


        eventDescriptionLayout = findViewById(R.id.add_event_description_layout)
        eventDescriptionText = findViewById(R.id.add_event_description_text)

        eventImageView = findViewById(R.id.add_event_image)

        saveButton = findViewById(R.id.save_event_button)


        eventAddressText.isEnabled = true
        eventAddressText.setTextIsSelectable(true)
        eventAddressText.isFocusable = false
        eventAddressText.isFocusableInTouchMode = false
        eventAddressText.setOnClickListener {

            val intent = Intent(this,GetLocationActivity::class.java).apply {

                putExtra(EVENT_NAME,eventNameText.text.toString())
                putExtra(EVENT_DATE,eventDateText.text.toString())

            }

            startActivity(intent)

            }



        eventAddressText.setText( intent.getStringExtra(LOCATION))

        eventNameText.setText(intent.getStringExtra(EVENT_NAME))

        eventDateText.setText(intent.getStringExtra(EVENT_DATE))




        val eventDatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select event date")
            .build()

        eventDatePicker.addOnPositiveButtonClickListener {
            eventDateText.setText(eventDatePicker.headerText.toString())
        }

        eventDatePicker.addOnPositiveButtonClickListener {
            eventDateText.setText(eventDatePicker.headerText.toString())
        }

        eventDateText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus){
                eventDatePicker.show(supportFragmentManager, "START_DATE")
            }else{
                eventDatePicker.dismiss()
            }
        }




        eventImageView.setOnClickListener {

            openGallery()
        }


        saveButton.setOnClickListener {

            addEvent()

        }


    }


    private fun addEvent(){


        CoroutineScope(Dispatchers.Main).launch{


            val eventName = eventNameText.text.toString()

            val eventDate = eventDateText.text.toString()

            val eventAddress = eventAddressText.text.toString()

            val eventDescription = eventDescriptionText.text.toString()

            val addressLatitude = intent.getStringExtra(LATITUDE).toString()

            val addressLongitude = intent.getStringExtra(LONGITUDE).toString()


            /*      val requestFile: RequestBody =
                      photoFile!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())


                  val body = MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile)

                  val name = eventName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                  val date = eventDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                  val address = eventAddress.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                  val description = eventDescription.toRequestBody("multipart/form-data".toMediaTypeOrNull())*/



            val requestFile: RequestBody = photoFile!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile)

            val name = eventName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val date = eventDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val address = eventAddress.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val description = eventDescription.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val latitude = addressLatitude.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val longitude = addressLongitude.toRequestBody("multipart/form-data".toMediaTypeOrNull())


            val response = RetrofitInterface.apiInterface.createEvent(name,date,address,description,latitude, longitude,body)

            if(response.isSuccessful){


                val intent = Intent(this@AddEventActivity,MainActivity::class.java)

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

            eventImageView.setImageURI(selectedImageUri)

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