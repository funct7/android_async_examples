package com.example.asyncexamples.presentation

import android.app.Application
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.asyncexamples.R
import com.example.asyncexamples.application.repository.ImageRepositoryImpl
import com.example.asyncexamples.application.repository.PostRepositoryImpl
import com.example.asyncexamples.application.repository.UserRepositoryImpl
import com.example.asyncexamples.domain.entity.PostModel
import com.example.asyncexamples.domain.entity.UserModel
import java.util.*

class MyApplication : Application() {

    // INTERFACE

    fun mockSuccess() {
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
        UserRepositoryImpl.returnTable[UserRepositoryImpl::fetchUser.name] = mapOf(
            user.id to user,
            otherUser.id to otherUser
        )
        ImageRepositoryImpl.returnTable[ImageRepositoryImpl::resolveImage.name] = mapOf(
            user.pictureUrl to BitmapFactory.decodeResource(resources, R.drawable.jim),
            otherUser.pictureUrl to BitmapFactory.decodeResource(resources, R.drawable.dwight)
        )
        PostRepositoryImpl.returnTable[PostRepositoryImpl::fetchPost.name] = listOf(
            object : PostModel {
                override val id: String = UUID.randomUUID().toString()
                override val userId: String = otherUser.id
                override val content: String = "random stuff"
            },
            object : PostModel {
                override val id: String = UUID.randomUUID().toString()
                override val userId: String = user.id
                override val content: String = "anything"
            },
            object : PostModel {
                override val id: String = UUID.randomUUID().toString()
                override val userId: String = otherUser.id
                override val content: String = "foo bar baz"
            },
            object : PostModel {
                override val id: String = UUID.randomUUID().toString()
                override val userId: String = otherUser.id
                override val content: String = "yucky"
            }
        )
    }

    fun mockFailure() {
        UserRepositoryImpl.returnTable[UserRepositoryImpl::signIn.name] = Throwable()
    }

    // INHERITED

    override
    fun onCreate() {
        super.onCreate()
        mockSuccess()
    }

}