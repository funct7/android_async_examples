package com.example.asyncexamples.domain.repository

import com.example.asyncexamples.domain.entity.PostModel

interface PostRepository {

    fun fetchPost(
        userId: String,
        onSuccess: (List<PostModel>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

}