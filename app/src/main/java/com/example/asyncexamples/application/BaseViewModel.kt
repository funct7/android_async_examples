package com.example.asyncexamples.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val isSignInButtonVisible: LiveData<Boolean>
        get() = mIsSignInButtonVisible
    protected val mIsSignInButtonVisible = MutableLiveData<Boolean>().apply { value = true }

    val isUserViewVisible: LiveData<Boolean>
        get() = mIsUserViewVisible
    protected val mIsUserViewVisible = MutableLiveData<Boolean>().apply { value = false }

    val user: LiveData<User?>
        get() = mUser
    protected val mUser = MutableLiveData<User?>().apply { value = null }

    val feed: LiveData<List<Post>>
        get() = mFeed
    protected val mFeed = MutableLiveData<List<Post>>().apply { value = listOf() }

    val signInMessage: LiveData<String?>
        get() = mSignInMessage
    protected val mSignInMessage = MutableLiveData<String?>().apply { value = null }

    val alertMessage: LiveData<String?>
        get() = mAlertMessage
    protected val mAlertMessage = MutableLiveData<String?>().apply { value = null }

    val isLoading: LiveData<Boolean>
        get() = mIsLoading
    protected val mIsLoading = MutableLiveData<Boolean>().apply { value = false }

    abstract fun tapButton()
    abstract fun signIn()
    abstract fun signOut()

    var loadingCount: Int = 0
        private set(value) {
            field = value
            mIsLoading.value = loadingCount > 0
        }

    protected fun incrementLoading() {
        loadingCount++
    }

    protected fun decrementLoading() {
        loadingCount--
    }

}