package com.example.asyncexamples.application.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.asyncexamples.domain.repository.ImageRepository

object ImageRepositoryImpl : BaseRepository(), ImageRepository {

    override
    fun resolveImage(
        uri: Uri,
        onSuccess: (Bitmap) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        @Suppress("UNCHECKED_CAST")
        (returnTable[::resolveImage.name] as Map<Uri, Any>).let {
            when (val result = it[uri]) {
                is Bitmap -> performAfterDelay { onSuccess(result) }
                is Throwable -> performAfterDelay { onFailure(result) }
                else -> error("invalid type: $it")
            }
        }
    }

}