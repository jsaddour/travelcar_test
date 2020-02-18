package com.example.travelcartest

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources



class TravelCarTestApp : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
    }



    // Singleton
    companion object {
        lateinit var instance: TravelCarTestApp private set

        fun packageName(): String {
            return instance.packageName
        }

        fun appContext(): Context {
            return instance.applicationContext
        }

        fun resources(): Resources {
            return instance.resources
        }

        fun appVersion(): String {
            try {
                val packageInfo = instance.packageManager.getPackageInfo(packageName(), 0)
                return packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return "1.0"
        }

        /**
         * Cache directory
         * absolutePath with "/"
         */
        fun absolutePath(): String {
            return instance.filesDir.absolutePath + "/"
        }
    }
}