package com.example.mycalendar.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mycalendar.core.database.model.ActivityEntity
import com.example.mycalendar.core.database.model.ActivityWithLocationAndUserAndParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM Activity ORDER BY start_time")
    fun getAllActivityEntity(): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM Activity WHERE id = :activityId")
    fun getActivityWithLocationAndUserAndParticipantsById(activityId: Int): Flow<ActivityWithLocationAndUserAndParticipants>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActivity(activityEntity: ActivityEntity)

    @Update
    suspend fun updateActivity(activityEntity: ActivityEntity)

    @Delete
    suspend fun deleteActivity(activityEntity: ActivityEntity)
}