package com.example.asyncexamples.domain

import android.net.Uri

interface UserModel {
    var id: String
    var username: String
    var pictureUrl: Uri
}