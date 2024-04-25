package ru.netology.shelqyamaps.application

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import ru.netology.shelqyamaps.BuildConfig

class MapApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }

}