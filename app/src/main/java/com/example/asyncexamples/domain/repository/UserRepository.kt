package com.example.asyncexamples.domain.repository

import com.example.asyncexamples.domain.entity.UserModel

interface UserRepository {

    fun signIn(onSuccess: (UserModel) -> Unit, onFailure: (Throwable) -> Unit)

    fun fetchUser(userId: String, onSuccess: (UserModel) -> Unit, onFailure: (Throwable) -> Unit)

}