package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.toActivityEntity
import com.example.mycalendar.core.database.dao.ActivityDao
import com.example.mycalendar.core.database.model.ActivityEntity
import com.example.mycalendar.core.database.model.toActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ActivityRepository {
    fun getAllPlainLocalActivities(): Flow<List<Activity>>
    suspend fun getLocalActivityDetailById(activityId: Int): Activity
    suspend fun addLocalActivity(activity: Activity): Int
    suspend fun updateLocalActivity(activity: Activity)
    suspend fun deleteLocalActivity(activity: Activity)
    fun saveRemoteActivity(activity: Activity)

    fun deleteRemoteActivity(activityId: Int)
}

class ActivityRepositoryImpl @Inject constructor(
    private val activityDao: ActivityDao,
    private val firestore: FirebaseFirestore,
) :
    ActivityRepository {
    override fun getAllPlainLocalActivities(): Flow<List<Activity>> {
        return activityDao.getAllActivityEntity().map{ it.map(ActivityEntity::toActivity) }
    }

    override suspend fun getLocalActivityDetailById(activityId: Int): Activity {
        return activityDao.getActivityWithLocationAndUserAndParticipantsById(activityId).toActivity()
    }

    override suspend fun addLocalActivity(activity: Activity): Int {
        return activityDao.addActivity(activity.toActivityEntity()).toInt()
    }

    override suspend fun updateLocalActivity(activity: Activity) {
        activityDao.updateActivity(activity.toActivityEntity())
    }

    override suspend fun deleteLocalActivity(activity: Activity) {
        activityDao.deleteActivity(activity.toActivityEntity())
    }

    override fun saveRemoteActivity(activity: Activity) {
        firestore.collection("activity").document(activity.id.toString()).set(activity)
    }

    override fun deleteRemoteActivity(activityId: Int) {
        firestore.collection("activity").document(activityId.toString()).delete()
    }
}