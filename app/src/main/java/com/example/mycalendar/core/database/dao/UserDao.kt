package com.example.mycalendar.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mycalendar.core.database.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE is_self = 1")
    suspend fun getActiveUserEntity(): UserEntity
}
