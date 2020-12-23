package com.key_key.instagramaccessibilityservice.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.key_key.instagramaccessibilityservice.Observable
import com.key_key.instagramaccessibilityservice.Observer


@Database(entities = [RoomEntity::class], version = 1, exportSchema = false)
abstract class IasDataBase : RoomDatabase(), Observable {
    abstract val entityDao : EntityDao?

    private val observers = mutableListOf<Observer>()

    override fun addObserver(observer: Observer) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    override fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o.update()
        }
    }
}