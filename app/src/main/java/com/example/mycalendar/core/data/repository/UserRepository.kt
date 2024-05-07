package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.User
import com.example.mycalendar.core.data.model.toUserEntity
import com.example.mycalendar.core.data.model.toUserMap
import com.example.mycalendar.core.database.dao.UserDao
import com.example.mycalendar.core.database.model.toUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

interface UserRepository {
    fun getCurrentUser(): User

    suspend fun setCurrentUser(user: User)

    suspend fun createLocalUser(user: User)

    fun createRemoteUser(user: User)
}

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao,
): UserRepository {
    override fun getCurrentUser(): User {
        return userDao.getCurrentUserEntity().toUser()
    }

    override suspend fun setCurrentUser(user: User) {
        userDao.updateUserEntity(user.toUserEntity())
    }

    override suspend fun createLocalUser(user: User) {
        userDao.addUserEntity(user.toUserEntity())
    }

    override fun createRemoteUser(user: User) {
        firestore
            .collection("user")
            .document(user.uid!!)
            .set(user.toUserMap())
    }
}