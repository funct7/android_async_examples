package com.example.asyncexamples.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.asyncexamples.R
import com.example.asyncexamples.application.BaseViewModel
import com.example.asyncexamples.application.CallbackViewModel
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    // INHERITED

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // PRIVATE

    // TODO: Use DI
    private val viewModel: BaseViewModel by lazy {
        ViewModelProvider(this).get(CallbackViewModel::class.java)
    }

}