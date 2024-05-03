package com.example.mycalendar.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mycalendar.core.database.model.ActivityEntity

@Dao
interface ActivityDao {
    @Query("SELECT * FROM Activity")
    suspend fun getAllActivityEntity(): List<ActivityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActivity(activityEntity: ActivityEntity)

    @Update
    suspend fun updateActivity(activityEntity: ActivityEntity)

    @Delete
    suspend fun deleteActivity(activityEntity: ActivityEntity)
}