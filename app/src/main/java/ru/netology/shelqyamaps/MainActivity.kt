package ru.netology.shelqyamaps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map.CameraCallback
import com.yandex.mapkit.mapview.MapView
import com.yandex.maps.mobile.BuildConfig

class MainActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.map)

        val cameraCallback = object : CameraCallback {
            override fun onMoveFinished(isFinished: Boolean) {
                TODO("Not yet implemented")
            }
        }
        mapView.mapWindow.map.move(
            CameraPosition(
                Point(60.077022, 30.341428),
                13f, 0f, 0f,
            ),
            Animation(Animation.Type.LINEAR, 1f),
            cameraCallback
        )
    }



    override fun onStart() {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}


