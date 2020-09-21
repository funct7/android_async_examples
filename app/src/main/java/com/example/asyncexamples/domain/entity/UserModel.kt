package com.example.asyncexamples.domain.entity

import android.net.Uri

interface UserModel {
    val id: String
    val username: String
    var pictureUrl: Uri
}