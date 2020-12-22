package com.key_key.instagramaccessibilityservice.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomEntity (
    var userName: String,
    @PrimaryKey
    var primaryKey: Int = 1
    )
