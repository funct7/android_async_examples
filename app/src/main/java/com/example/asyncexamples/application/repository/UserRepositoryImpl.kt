package com.example.asyncexamples.application.repository

import com.example.asyncexamples.domain.entity.UserModel
import com.example.asyncexamples.domain.repository.UserRepository

class UserRepositoryImpl : BaseRepository(), UserRepository {

    override
    fun signIn(onSuccess: (UserModel) -> Unit, onFailure: (Throwable) -> Unit) {
        returnTable[javaClass.enclosingMethod!!.name].let {
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
        returnTable[javaClass.enclosingMethod!!.name].let {
            when (it) {
                is UserModel -> performAfterDelay { onSuccess(it) }
                is Throwable -> performAfterDelay { onFailure(it) }
                else -> error("invalid type $it")
            }
        }
    }

}