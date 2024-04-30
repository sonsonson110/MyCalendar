package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Event
import com.example.mycalendar.core.database.dao.EventDao
import com.example.mycalendar.core.database.model.toEvent
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val eventDao: EventDao): EventRepository {
    override suspend fun getEventById(eventId: Int): Event {
        return eventDao.getEventById(eventId).toEvent()
    }

}