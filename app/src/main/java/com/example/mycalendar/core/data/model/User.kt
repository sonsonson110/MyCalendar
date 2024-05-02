package com.example.mycalendar.core.data.model

import com.example.mycalendar.core.database.model.UserEntity

data class User(
    val uid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val isSelf: Boolean? = null,
)

fun User.toUserEntity() = UserEntity(
    uid = this.uid!!,
    name = this.name!!,
    email = this.email!!,
    isSelf = this.isSelf!!
)
