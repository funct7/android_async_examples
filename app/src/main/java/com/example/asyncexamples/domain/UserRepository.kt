package com.example.asyncexamples.domain

interface UserRepository {

    fun signIn(onSuccess: (UserModel) -> Unit, onFailure: (Throwable) -> Unit)

    fun fetchUser(userId: String, onSuccess: (UserModel) -> Unit, onFailure: (Throwable) -> Unit)

}