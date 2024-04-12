package ru.netology.shelqyamaps

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.Map.CameraCallback
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class MainActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private val newMarkTapListener = MapObjectTapListener { _, point ->
        Toast.makeText(
            this@MainActivity,
            "Координаты  (${point.longitude}, ${point.latitude})",
            Toast.LENGTH_SHORT

        ).show()

        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.map)
        val map = mapView.mapWindow.map

        val cameraCallback = CameraCallback { return@CameraCallback }
        map.move(
            CameraPosition(
                Point(60.077022, 30.341428),
                13f, 0f, 0f,
            ),
            Animation(Animation.Type.LINEAR, 1f),
            cameraCallback
        )


        val inputListener = object : InputListener {
            override fun onMapTap(map: Map, point: Point) {
                //Добавления метки
                addNewMark(map, point)
            }

            override fun onMapLongTap(map: Map, point: Point) {
                //Удаление при долгом нажатии
                map.mapObjects.remove(map.mapObjects)
            }
        }
        map.addInputListener(inputListener)
    }

    private fun addNewMark(map: Map, point: Point) {
        map.mapObjects.addPlacemark().apply {
            geometry = point
            setIcon(ImageProvider.fromResource(this@MainActivity, R.drawable.mark_ic_24dp))
        }.addTapListener(newMarkTapListener)
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


