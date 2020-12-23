package com.key_key.instagramaccessibilityservice

import android.app.Application
import androidx.room.Room
import com.key_key.instagramaccessibilityservice.room.IasDataBase

class IasApp : Application() {

    companion object {
        lateinit var instance: IasApp
    }

    lateinit var iasDataBase: IasDataBase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        iasDataBase = Room.databaseBuilder(
            this, IasDataBase::class.java,
            "instagram_accessibility_service.db")
            .allowMainThreadQueries()
            .build()
    }

}
