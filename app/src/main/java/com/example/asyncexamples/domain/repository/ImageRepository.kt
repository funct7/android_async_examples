package com.example.asyncexamples.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface ImageRepository {

    fun resolveImage(uri: Uri, onSuccess: (Bitmap) -> Unit, onFailure: (Throwable) -> Unit)

}