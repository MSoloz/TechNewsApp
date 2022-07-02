package com.example.technews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.example.technews.adapter.EVENT_ADDRESS_LATITUDE
import com.example.technews.adapter.EVENT_ADDRESS_LONGITUDE
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.search.MapboxSearchSdk

class ShowLocationActivity : AppCompatActivity() {


    private lateinit var mapView: MapView

    lateinit var mapboxMap: MapboxMap




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_location)


        mapView = findViewById(R.id.map_view1)




        MapboxSearchSdk.initialize(
            application = application,
            accessToken = getString(R.string.mapbox_access_token),
            locationEngine = LocationEngineProvider.getBestLocationEngine(this)
        )



        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS
        ) { }

        mapView.getMapboxMap().apply {

            loadStyleUri(Style.MAPBOX_STREETS) {

                   addAnnotationMap()

            }
        }




    }

    private fun addAnnotationMap() {


       val latitude = intent.getStringExtra(EVENT_ADDRESS_LATITUDE)!!.toDouble()

      val longitude = intent.getStringExtra(EVENT_ADDRESS_LONGITUDE)!!.toDouble()


        Log.d("latitude",latitude.toString())


        bitmapFromDrawableRes(
            this@ShowLocationActivity,
            R.drawable.red_marker
        )?.let {

            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)

// Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude,latitude))
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


    }





