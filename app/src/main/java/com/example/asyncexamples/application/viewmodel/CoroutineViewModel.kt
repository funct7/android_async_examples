package com.example.asyncexamples.application.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.asyncexamples.application.Post
import com.example.asyncexamples.application.User
import com.example.asyncexamples.application.repository.ImageRepositoryImpl
import com.example.asyncexamples.application.repository.PostRepositoryImpl
import com.example.asyncexamples.application.repository.UserRepositoryImpl
import com.example.asyncexamples.domain.entity.PostModel
import com.example.asyncexamples.domain.entity.UserModel
import com.example.asyncexamples.domain.repository.ImageRepository
import com.example.asyncexamples.domain.repository.PostRepository
import com.example.asyncexamples.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CoroutineViewModel : BaseViewModel() {

    override
    fun signIn() = viewModelScope.launch {
        incrementLoading()

        try {
            val userModel = model.signInAsync().await()
            mUser.value = User(userModel.id, userModel.username, null)

            mUser.value = model.resolveImageAsync(userModel.pictureUrl).await()
                .let { mUser.value!!.copy(userImage = it) }

            val postList = model.fetchPostAsync(userModel.id).await()
            mFeed.value = postList.map {
                Post(
                    it.id,
                    it.userId,
                    MutableLiveData(""),
                    MutableLiveData(null),
                    it.content
                )
            }

            postList
                .fold(mutableSetOf<String>()) { set, postModel ->
                    set.add(postModel.userId)
                    return@fold set
                }
                .map { userId -> model.fetchUserAsync(userId) }
                .map { deferredUser ->
                    val postUserModel = deferredUser.await()

                    mFeed.value!!
                        .filter { it.userID == postUserModel.id }
                        .map { it.username as MutableLiveData }
                        .forEach { it.value = postUserModel.username }

                    return@map postUserModel.id to model.resolveImageAsync(postUserModel.pictureUrl)
                }
                .forEach { (userId, postUserImage) ->
                    mFeed.value!!
                        .filter { it.userID == userId }
                        .map { it.userImage as MutableLiveData }
                        .forEach { it.value = postUserImage.await() }
                }
        } catch (e: Throwable) {
            showAlertMessage(e.localizedMessage)
        }

        decrementLoading()
    }.let { Unit }

    // PRIVATE

    private val model = CoroutineModel(viewModelScope)

}

private class CoroutineModel(private val scope: CoroutineScope) {

    private val userRepository: UserRepository = UserRepositoryImpl
    private val imageRepository: ImageRepository = ImageRepositoryImpl
    private val postRepository: PostRepository = PostRepositoryImpl

    fun signInAsync(): Deferred<UserModel> = scope.async {
        suspendCoroutine<UserModel> { continuation ->
            userRepository.signIn(
                onSuccess = { continuation.resume(it) },
                onFailure = { continuation.resumeWithException(it) })
        }
    }

    fun fetchUserAsync(userId: String): Deferred<UserModel> = scope.async {
        suspendCoroutine<UserModel> { continuation ->
            userRepository.fetchUser(userId,
                onSuccess = { continuation.resume(it) },
                onFailure = { continuation.resumeWithException(it) })
        }
    }

    fun resolveImageAsync(uri: Uri): Deferred<Bitmap> = scope.async {
        suspendCoroutine<Bitmap> { continuation ->
            imageRepository.resolveImage(uri,
                onSuccess = { continuation.resume(it) },
                onFailure = { continuation.resumeWithException(it) })
        }
    }

    fun fetchPostAsync(userId: String): Deferred<List<PostModel>> = scope.async {
        suspendCoroutine<List<PostModel>> { continuation ->
            postRepository.fetchPost(userId,
                onSuccess = { continuation.resume(it) },
                onFailure = { continuation.resumeWithException(it) })
        }
    }

}