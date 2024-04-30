package com.example.mycalendar.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycalendar.core.data.model.User

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String,
    @ColumnInfo(name = "is_self") val isSelf: Boolean
)

fun UserEntity.toUser() = User(
    uid = this.uid,
    name = this.name,
    email = this.email,
    isSelf = this.isSelf
)
