package com.example.asyncexamples.application.viewmodel

import com.example.asyncexamples.application.repository.ImageRepositoryImpl
import com.example.asyncexamples.application.repository.PostRepositoryImpl
import com.example.asyncexamples.application.repository.UserRepositoryImpl
import com.example.asyncexamples.domain.repository.ImageRepository
import com.example.asyncexamples.domain.repository.PostRepository
import com.example.asyncexamples.domain.repository.UserRepository

// TODO: Use Dagger
class CallbackViewModel(
    private val userRepository: UserRepository = UserRepositoryImpl(),
    private val imageRepository: ImageRepository = ImageRepositoryImpl(),
    private val postRepository: PostRepository = PostRepositoryImpl()
) : BaseViewModel() {

    override
    fun signIn() {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

}