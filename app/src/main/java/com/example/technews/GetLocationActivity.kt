package com.example.technews

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.button.MaterialButton
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.search.*
import com.mapbox.search.result.SearchResult


const val LATITUDE ="LATITUDE"
const val LONGITUDE ="LONGITUDE"
const val LOCATION ="LOCATION"
const val EVENT_NAME_2 ="EVENT_NAME_2"
const val EVENT_DATE_2 ="EVENT_DATE_2"

class GetLocationActivity : AppCompatActivity(),OnMapClickListener {


    private lateinit var mapView: MapView

    lateinit var mapboxMap: MapboxMap
    lateinit var textView: TextView

    lateinit var saveButton:MaterialButton

    lateinit var latitude:String
    lateinit var longitude :String


    private lateinit var mReverseGeocoding: ReverseGeocodingSearchEngine
    private lateinit var searchRequestTask: SearchRequestTask

    private val searchCallback = object : SearchCallback {

        override fun onResults(results: List<SearchResult>, responseInfo: ResponseInfo) {
            if (results.isEmpty()) {
                Log.i("SearchApiExample", "No reverse geocoding results")
            } else {
                Log.i("SearchApiExample", "Reverse geocoding results: $results")


                val place = results[0].address!!.place

                Log.i("SearchApiExample", "Reverse geocoding results: $place")

                textView.text = place


            }
        }

        override fun onError(e: Exception) {
            Log.i("SearchApiExample", "Reverse geocoding error", e)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_location)

        textView = findViewById(R.id.text_view)
        mapView = findViewById(R.id.map_view)
        saveButton = findViewById(R.id.save_location_button)


        MapboxSearchSdk.initialize(
            application = application,
            accessToken = getString(R.string.mapbox_access_token),
            locationEngine = LocationEngineProvider.getBestLocationEngine(this)
        )



        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS
        ) { }

        mapboxMap = mapView.getMapboxMap().apply {

            loadStyleUri(Style.MAPBOX_STREETS) {

                addOnMapClickListener(this@GetLocationActivity)

            }
        }



        saveButton.setOnClickListener {


            val intent = Intent(this,AddEventActivity::class.java).apply {

                putExtra(LOCATION,textView.text)
                putExtra(LONGITUDE,longitude)
                putExtra(LATITUDE,latitude)
                putExtra(EVENT_NAME,intent.getStringExtra(EVENT_NAME).toString())
                putExtra(EVENT_DATE,intent.getStringExtra(EVENT_DATE).toString())




            }

            startActivity(intent)



        }



    }


    private fun addAnnotationToMap(point: Point)  {
// Create an instance of the Annotation API and get the PointAnnotationManager.

        bitmapFromDrawableRes(
            this@GetLocationActivity,
            R.drawable.red_marker
        )?.let {

            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)

// Set options for the resulting symbol layer.
            val    pointAnnotationOptions : PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(point)
// Define a geographic coordinate.
                //  .withPoint(Point.fromLngLat(-74.0060, 40.7128))
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
                .withIconImage(it)

              pointAnnotationManager?.create(pointAnnotationOptions)


// Add the resulting pointAnnotation to the map.

        }


    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }


    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()


    }

    override fun onMapClick(point: Point): Boolean {


        latitude = point.latitude().toString()
        longitude = point.longitude().toString()


        Log.d("latitude", latitude)
        Log.d("longitude", longitude)


        mReverseGeocoding = MapboxSearchSdk.getReverseGeocodingSearchEngine()
        val options = ReverseGeoOptions(
            center = Point.fromLngLat(point.longitude(), point.latitude()),
            limit = 1
        )
        mReverseGeocoding.search(options,searchCallback)

        addAnnotationToMap(point)

        return true
    }

}