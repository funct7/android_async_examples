package com.example.asyncexamples.application.repository

import android.os.Handler
import android.os.Looper
import kotlin.random.Random

open class BaseRepository {

    /**
     * Max delay
     */
    var delay: Long = 2000L

    /**
     * `$METHOD_NAME : $RETURN_VALUE` 테이블.
     */
    var returnTable: MutableMap<String, Any> = mutableMapOf()

    fun performAfterDelay(block: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(block, Random.nextLong(delay))
    }

}