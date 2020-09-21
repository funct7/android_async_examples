package com.example.asyncexamples.application.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.asyncexamples.domain.repository.ImageRepository

class ImageRepositoryImpl : BaseRepository(), ImageRepository {

    override
    fun resolveImage(
        uri: Uri,
        onSuccess: (Bitmap) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        returnTable[javaClass.enclosingMethod!!.name].let {
            when (it) {
                is Bitmap -> performAfterDelay { onSuccess(it) }
                is Throwable -> performAfterDelay { onFailure(it) }
                else -> error("invalid type: $it")
            }
        }
    }

}