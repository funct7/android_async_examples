package com.example.asyncexamples.application

import android.graphics.Bitmap
import androidx.lifecycle.LiveData

data class User(
    val id: String,
    /**
     * Initial value 는 빈 string.
     */
    val username: LiveData<String>,
    val userImage: LiveData<Bitmap?>
)