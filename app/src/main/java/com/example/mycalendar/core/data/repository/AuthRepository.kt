package com.example.mycalendar.core.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?

    suspend fun signInUserWithEmailAndPassword(email: String, password: String): Flow<AuthResult>

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Flow<AuthResult>
}

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override fun getCurrentUser(): FirebaseUser? {
        val currentUser = firebaseAuth.currentUser
        return currentUser
    }

    override suspend fun signInUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult> = flow {
        val response = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(response)
    }.flowOn(Dispatchers.IO)

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Flow<AuthResult> = flow {
        val response = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        emit(response)
    }.flowOn(Dispatchers.IO)
}