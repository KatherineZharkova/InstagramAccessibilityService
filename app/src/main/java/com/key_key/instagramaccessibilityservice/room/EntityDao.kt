package com.key_key.instagramaccessibilityservice.room

import androidx.room.*

@Dao
interface EntityDao {

    @Query("SELECT * FROM RoomEntity WHERE primaryKey LIKE :primaryKey")
    fun getUserName(primaryKey: Int = 1): RoomEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(entities: List<RoomEntity>)

    @Update
    fun update(entity: RoomEntity)

    @Delete
    fun delete(entity: RoomEntity)

}
