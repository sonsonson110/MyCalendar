package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Event

interface EventRepository {
    suspend fun getEventById(eventId: Int): Event
}