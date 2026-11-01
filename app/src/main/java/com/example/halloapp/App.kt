package com.example.halloapp

import android.app.Application
import org.osmdroid.config.Configuration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().apply {
            userAgentValue = "Locations/1.5 (Android; OSM tiles)"
        }
    }
}
