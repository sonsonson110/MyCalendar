package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.User
import com.example.mycalendar.core.data.model.toUserEntity
import com.example.mycalendar.core.data.model.toUserMap
import com.example.mycalendar.core.database.dao.UserDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

interface UserRepository {
    suspend fun createLocalAndRemoteUser(user: User)
}

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao,
): UserRepository {
    override suspend fun createLocalAndRemoteUser(user: User) {
        userDao.addUser(user.toUserEntity())
        firestore
            .collection("user")
            .document(user.uid!!)
            .set(user.toUserMap())
    }
}