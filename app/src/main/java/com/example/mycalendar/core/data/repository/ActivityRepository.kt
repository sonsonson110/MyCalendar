package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.toActivityEntity
import com.example.mycalendar.core.database.dao.ActivityDao
import com.example.mycalendar.core.database.model.ActivityEntity
import com.example.mycalendar.core.database.model.ActivityWithLocationAndUserAndParticipants
import com.example.mycalendar.core.database.model.toActivity
import javax.inject.Inject

interface ActivityRepository {
    suspend fun getAllPlainActivity(): List<Activity>
    suspend fun getActivityDetailById(activityId: Int): Activity
    suspend fun addActivity(activity: Activity)
    suspend fun updateActivity(activity: Activity)
    suspend fun deleteActivity(activity: Activity)
}

class ActivityRepositoryImpl @Inject constructor(private val activityDao: ActivityDao): ActivityRepository {
    override suspend fun getAllPlainActivity(): List<Activity> {
        return activityDao.getAllActivityEntity().map(ActivityEntity::toActivity)
    }

    override suspend fun getActivityDetailById(activityId: Int): Activity {
        return activityDao.getActivityWithLocationAndUserAndParticipantsById(activityId).toActivity()
    }

    override suspend fun addActivity(activity: Activity) {
        activityDao.addActivity(activity.toActivityEntity())
    }

    override suspend fun updateActivity(activity: Activity) {
        activityDao.updateActivity(activity.toActivityEntity())
    }

    override suspend fun deleteActivity(activity: Activity) {
        activityDao.deleteActivity(activity.toActivityEntity())
    }
}