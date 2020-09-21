package com.example.asyncexamples.application.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.asyncexamples.application.Post
import com.example.asyncexamples.application.User
import com.example.asyncexamples.application.repository.ImageRepositoryImpl
import com.example.asyncexamples.application.repository.PostRepositoryImpl
import com.example.asyncexamples.application.repository.UserRepositoryImpl
import com.example.asyncexamples.domain.repository.ImageRepository
import com.example.asyncexamples.domain.repository.PostRepository
import com.example.asyncexamples.domain.repository.UserRepository

// TODO: Use Dagger
class CallbackViewModel(
    private val userRepository: UserRepository = UserRepositoryImpl,
    private val imageRepository: ImageRepository = ImageRepositoryImpl,
    private val postRepository: PostRepository = PostRepositoryImpl
) : BaseViewModel() {

    override
    fun signIn() {
        incrementLoading()

        userRepository.signIn(
            onSuccess = { userModel ->
                mUser.value = User(userModel.id, userModel.username, null)

                incrementLoading()

                imageRepository.resolveImage(userModel.pictureUrl,
                    onSuccess = { userImage ->
                        mUser.value = mUser.value!!.copy(userImage = userImage)
                        decrementLoading()
                    },
                    onFailure = { error ->
                        mAlertMessage.value = error.localizedMessage
                        decrementLoading()
                    })

                incrementLoading()

                postRepository.fetchPost(userModel.id,
                    onSuccess = { postList ->
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
                            .forEach { userId ->
                                incrementLoading()

                                userRepository.fetchUser(userId,
                                    onSuccess = { postUserModel ->
                                        mFeed.value!!
                                            .filter { it.userID == postUserModel.id }
                                            .map { it.username as MutableLiveData }
                                            .forEach { it.value = postUserModel.username }

                                        incrementLoading()

                                        imageRepository.resolveImage(postUserModel.pictureUrl,
                                            onSuccess = { postUserImage ->
                                                mFeed.value!!
                                                    .filter { it.userID == postUserModel.id }
                                                    .map { it.userImage as MutableLiveData }
                                                    .forEach { it.value = postUserImage }

                                                decrementLoading()
                                            },
                                            onFailure = { error ->
                                                mAlertMessage.value = error.localizedMessage
                                                decrementLoading()
                                            })

                                        decrementLoading()
                                    },
                                    onFailure = { error ->
                                        mAlertMessage.value = error.localizedMessage
                                        decrementLoading()
                                    })
                            }

                        decrementLoading()
                    },
                    onFailure = { error ->
                        mAlertMessage.value = error.localizedMessage
                        decrementLoading()
                    })

                decrementLoading()
            },
            onFailure = { error ->
                mAlertMessage.value = error.localizedMessage
                decrementLoading()
            })
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

}