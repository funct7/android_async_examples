package com.example.asyncexamples.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.asyncexamples.application.Post
import com.example.asyncexamples.application.User

abstract class BaseViewModel : ViewModel() {

    val isSignInButtonVisible: LiveData<Boolean>
        get() = mUser.map { it == null }

    val isUserViewVisible: LiveData<Boolean>
        get() = mUser.map { it != null }

    val user: LiveData<User?>
        get() = mUser
    protected val mUser = MutableLiveData<User?>().apply { value = null }

    val feed: LiveData<List<Post>>
        get() = mFeed
    protected val mFeed = MutableLiveData<List<Post>>().apply { value = listOf() }

    val confirmSignIn: LiveData<Boolean>
        get() = mConfirmSignIn
    private val mConfirmSignIn = MutableLiveData<Boolean>().apply { value = false }

    val alertMessage: LiveData<String?>
        get() = mAlertMessage
    protected val mAlertMessage = MutableLiveData<String?>().apply { value = null }

    val isLoading: LiveData<Boolean>
        get() = mIsLoading
    private val mIsLoading = MutableLiveData<Boolean>().apply { value = false }

    fun tapButton() {
        mConfirmSignIn.value = true
        mConfirmSignIn.postValue(false)
    }

    abstract fun signIn()
    abstract fun signOut()

    private var loadingCount: Int = 0
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