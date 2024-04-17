package ru.netology.shelqyamaps.activity

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.Map.CameraCallback
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.MapObjectVisitor
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.PolygonMapObject
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.netology.shelqyamaps.BuildConfig
import ru.netology.shelqyamaps.R


class MainActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var collection: MapObjectCollection
    private lateinit var visitor: MapObjectVisitor
    val marksET: EditText = findViewById(R.id.marksET)

    //Координаты
//    private val markTapListener = MapObjectTapListener { _, point ->
//        Toast.makeText(
//            this@MainActivity,
//            "Координаты  (${String.format("%.2f", point.longitude)}, ${
//                String.format(
//                    "%.2f",
//                    point.latitude
//                )
//            })",
//            Toast.LENGTH_SHORT
//        ).show()
//        true
//    }

    //Удаление метки
    private val removeTapListener = MapObjectTapListener { marks, _ ->
        collection.remove(marks)
        true
    }


    private val inputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            //Добавление метки
            addNewMark(point)


        }

        override fun onMapLongTap(map: Map, point: Point) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.map)
        val map = mapView.mapWindow.map
        val cameraCallback = CameraCallback { return@CameraCallback }
        collection = map.mapObjects.addCollection()


        map.move(
            CameraPosition(
                Point(60.077022, 30.341428), 16f, 0f, 0f,
            ),
            Animation(Animation.Type.LINEAR, 1f),
            cameraCallback
        )
        map.addInputListener(inputListener)

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

    private fun addNewMark(point: Point) {
        collection.addPlacemark().apply {
            geometry = point
            setIcon(ImageProvider.fromResource(this@MainActivity, R.drawable.mark_ic_24dp))
        }.addTapListener(removeTapListener)
    }


}




