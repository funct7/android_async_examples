package com.example.asyncexamples.application.viewmodel

import com.example.asyncexamples.application.repository.UserRepositoryImpl
import com.example.asyncexamples.domain.repository.UserRepository

// TODO: Use Dagger
class CallbackViewModel(
    private val userRepository: UserRepository = UserRepositoryImpl()
) : BaseViewModel() {

    override
    fun signIn() {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

}