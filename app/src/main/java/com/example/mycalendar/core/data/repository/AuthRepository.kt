package com.example.mycalendar.core.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    fun getCurrentAuthUser(): FirebaseUser?

    // only called on sign up successfully
    suspend fun updateAthUserDisplayName(displayName: String, authResult: AuthResult)

    fun signInUserWithEmailAndPassword(email: String, password: String): Flow<AuthResult>

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Flow<AuthResult>

    fun signOutUser()
}

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override fun getCurrentAuthUser(): FirebaseUser? {
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        return currentUser
    }

    override suspend fun updateAthUserDisplayName(displayName: String, authResult: AuthResult) {
        val changeRequest = userProfileChangeRequest { this.displayName = displayName }
        authResult.user!!.updateProfile(changeRequest).await()
    }

    override fun signInUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult> = flow {
        val response = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(response)
    }

    override fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Flow<AuthResult> = flow {
        val response = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        emit(response)
    }

    override fun signOutUser() {
        firebaseAuth.signOut()
    }
}