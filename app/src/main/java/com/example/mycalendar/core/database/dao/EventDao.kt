package com.example.mycalendar.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mycalendar.core.database.model.EventAndTaskAndLocationAndParticipants

@Dao
interface EventDao {
    @Query("SELECT * FROM Event WHERE id = :eventId")
    suspend fun getEventById(eventId: Int): EventAndTaskAndLocationAndParticipants
}