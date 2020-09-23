package com.example.asyncexamples.application.repository

import com.example.asyncexamples.domain.entity.UserModel
import com.example.asyncexamples.domain.repository.UserRepository

object UserRepositoryImpl : BaseRepository(), UserRepository {

    override
    fun signIn(onSuccess: (UserModel) -> Unit, onFailure: (Throwable) -> Unit) {
        returnTable[::signIn.name].let {
            when (it) {
                is UserModel -> performAfterDelay { onSuccess(it) }
                is Throwable -> performAfterDelay { onFailure(it) }
                else -> error("invalid type $it")
            }
        }
    }

    override
    fun fetchUser(
        userId: String,
        onSuccess: (UserModel) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        @Suppress("UNCHECKED_CAST")
        (returnTable[::fetchUser.name] as Map<String, Any>).let {
            when (val result = it[userId]) {
                is UserModel -> performAfterDelay { onSuccess(result) }
                is Throwable -> performAfterDelay { onFailure(result) }
                else -> error("invalid type $it")
            }
        }
    }

}