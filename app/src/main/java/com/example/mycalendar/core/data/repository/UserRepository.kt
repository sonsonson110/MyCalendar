package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.User
import com.example.mycalendar.core.data.model.toUserEntity
import com.example.mycalendar.core.data.model.toUserMap
import com.example.mycalendar.core.database.dao.UserDao
import com.example.mycalendar.core.database.model.toUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

interface UserRepository {
    suspend fun getCurrentUser(): User

    suspend fun setCurrentUser(user: User)

    suspend fun createLocalUser(user: User)

    fun createRemoteUser(user: User)
}

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao,
): UserRepository {
    override suspend fun getCurrentUser(): User {
        return userDao.getActiveUserEntity().toUser()
    }

    override suspend fun setCurrentUser(user: User) {
        userDao.update(user.toUserEntity())
    }

    override suspend fun createLocalUser(user: User) {
        userDao.insert(user.toUserEntity())
    }

    override fun createRemoteUser(user: User) {
        firestore
            .collection("user")
            .document(user.uid!!)
            .set(user.toUserMap())
    }
}