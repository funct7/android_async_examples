package com.example.asyncexamples.application.repository

import com.example.asyncexamples.domain.entity.PostModel
import com.example.asyncexamples.domain.repository.PostRepository

object PostRepositoryImpl : BaseRepository(), PostRepository {

    override
    fun fetchPost(
        userId: String,
        onSuccess: (List<PostModel>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        returnTable[::fetchPost.name].let {
            when (it) {
                is List<*> -> performAfterDelay {
                    @Suppress("UNCHECKED_CAST")
                    onSuccess(it as List<PostModel>)
                }
                is Throwable -> performAfterDelay { onFailure(it) }
                else -> error("invalid type: $it")
            }
        }
    }

}