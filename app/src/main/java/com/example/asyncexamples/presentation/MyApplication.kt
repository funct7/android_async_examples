package com.example.asyncexamples.presentation

import android.app.Application
import android.net.Uri
import com.example.asyncexamples.application.repository.UserRepositoryImpl
import com.example.asyncexamples.domain.entity.UserModel
import java.util.*

class MyApplication : Application() {

    override
    fun onCreate() {
        super.onCreate()

        val user = object : UserModel {
            override val id: String = UUID.randomUUID().toString()
            override val username: String = "Jim Halpert"
            override var pictureUrl: Uri = Uri.parse("content://foo")
        }

        val otherUser = object : UserModel {
            override val id: String = UUID.randomUUID().toString()
            override val username: String = "Dwight Howard"
            override var pictureUrl: Uri = Uri.parse("content://bar")
        }

        UserRepositoryImpl.returnTable[UserRepositoryImpl::signIn.name] = user
        UserRepositoryImpl.returnTable[UserRepositoryImpl::fetchUser.name] = otherUser
    }

}