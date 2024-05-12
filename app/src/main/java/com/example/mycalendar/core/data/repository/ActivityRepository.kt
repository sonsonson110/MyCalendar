package com.example.mycalendar.core.data.repository

import android.util.Log
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.toActivityEntity
import com.example.mycalendar.core.data.model.toHashMap
import com.example.mycalendar.core.database.dao.ActivityDao
import com.example.mycalendar.core.database.model.ActivityEntity
import com.example.mycalendar.core.database.model.toActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ActivityRepository {
    fun getAllPlainLocalActivities(): Flow<List<Activity>>
    suspend fun getLocalActivityDetailById(activityId: Int): Activity
    suspend fun addLocalActivity(activity: Activity): Int
    suspend fun updateLocalActivity(activity: Activity)
    suspend fun deleteLocalActivity(activity: Activity)
    fun addRemoteActivity(activity: Activity)
    suspend fun updateRemoteActivity(activity: Activity)

    fun deleteRemoteActivity(activityId: Int)
}

private const val TAG = "ActivityRepositoryImpl"
class ActivityRepositoryImpl @Inject constructor(
    private val activityDao: ActivityDao,
    private val firestore: FirebaseFirestore,
) :
    ActivityRepository {
    override fun getAllPlainLocalActivities(): Flow<List<Activity>> {
        return activityDao.getPlainList().map { it.map(ActivityEntity::toActivity) }
    }

    override suspend fun getLocalActivityDetailById(activityId: Int): Activity {
        return activityDao.getDetailOneById(activityId)
            .toActivity()
    }

    override suspend fun addLocalActivity(activity: Activity): Int {
        return activityDao.insert(activity.toActivityEntity()).toInt()
    }

    override suspend fun updateLocalActivity(activity: Activity) {
        activityDao.update(activity.toActivityEntity())
    }

    override suspend fun deleteLocalActivity(activity: Activity) {
        activityDao.delete(activity.toActivityEntity())
    }

    override fun addRemoteActivity(activity: Activity) {
        firestore.collection("activity").add(activity.toHashMap())
    }

    override suspend fun updateRemoteActivity(activity: Activity) {
        val task = firestore
            .collection("activity")
            .whereEqualTo("id", activity.id)
            .whereEqualTo("createdUser.uid", activity.createdUser!!.uid)
            .get().await()

        try {
            val document = task.documents[0].id
            // Update the document with the new data
            firestore.collection("activity").document(document)
                .set(activity.toHashMap(), SetOptions.merge())
        } catch (e: Exception) {
            // no remote no data found
            Log.e(TAG, e.toString())
        }
    }

    override fun deleteRemoteActivity(activityId: Int) {
        firestore
            .collection("activity")
            .document(activityId.toString())
            .delete()
    }
}