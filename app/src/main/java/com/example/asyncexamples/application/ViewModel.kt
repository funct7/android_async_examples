package com.example.asyncexamples.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class ViewModel {

    val isSignInButtonVisible: LiveData<Boolean>
        get() = mIsSignInButtonVisible
    private val mIsSignInButtonVisible = MutableLiveData<Boolean>().apply { value = true }

    val isUserViewVisible: LiveData<Boolean>
        get() = mIsUserViewVisible
    private val mIsUserViewVisible = MutableLiveData<Boolean>().apply { value = false }

    val user: LiveData<User?>
        get() = mUser
    private val mUser = MutableLiveData<User?>().apply { value = null }

    val feed: LiveData<List<Post>>
        get() = mFeed
    private val mFeed = MutableLiveData<List<Post>>().apply { value = listOf() }

    val signInMessage: LiveData<String?>
        get() = mSignInMessage
    private val mSignInMessage = MutableLiveData<String?>().apply { value = null }

    val alertMessage: LiveData<String?>
        get() = mAlertMessage
    private val mAlertMessage = MutableLiveData<String?>().apply { value = null }

    val isLoading: LiveData<Boolean>
        get() = mIsLoading
    private val mIsLoading = MutableLiveData<Boolean>().apply { value = false }

    abstract fun tapButton()
    abstract fun signIn()
    abstract fun signOut()

    var loadingCount: Int = 0
        private set(value) {
            field = value
            mIsLoading.value = loadingCount > 0
        }

    fun incrementLoading() {
        loadingCount++
    }

    fun decrementLoading() {
        loadingCount--
    }

}