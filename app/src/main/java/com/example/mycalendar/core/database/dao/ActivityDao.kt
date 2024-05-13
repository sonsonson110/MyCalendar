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
    // use flow to notify new data to consumer when data changes
    @Query("SELECT * FROM Activity ORDER BY start_time")
    fun getPlainList(): Flow<List<ActivityEntity>>

    // only get it oneshot, mark it as `suspend`
    @Query("SELECT * FROM Activity WHERE id = :activityId")
    suspend fun getDetailOneById(activityId: Int): ActivityWithLocationAndUserAndParticipants

    // filter activities by their title name contain a query string
    @Query("SELECT * FROM Activity WHERE title LIKE :query")
    fun getPlainListByQuery(query: String): Flow<List<ActivityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activityEntity: ActivityEntity): Long

    @Update
    suspend fun update(activityEntity: ActivityEntity)

    @Delete
    suspend fun delete(activityEntity: ActivityEntity)
}