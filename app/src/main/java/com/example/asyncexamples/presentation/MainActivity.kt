package com.example.asyncexamples.presentation

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asyncexamples.R
import com.example.asyncexamples.application.viewmodel.BaseViewModel
import com.example.asyncexamples.application.viewmodel.CallbackViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // INHERITED

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp()
    }

    // PRIVATE

    // TODO: Use DI
    // TODO: Use data binding
    private lateinit var viewModel: BaseViewModel
    private val feedAdapter by lazy { FeedAdapter(this) }

    private fun setUp() {
        viewModel = ViewModelProvider(this).get(CallbackViewModel::class.java)

        list_feed.layoutManager = LinearLayoutManager(this).apply {
            orientation = RecyclerView.VERTICAL
        }
        list_feed.adapter = feedAdapter

        btn_sign_in.setOnClickListener { viewModel.tapButton() }
        btn_sign_out.setOnClickListener { viewModel.signOut() }
        toggle_response.setOnCheckedChangeListener { _, isChecked -> toggleResponse(isChecked) }

        viewModel.isLoading.observe(this) { isLoading ->
            view_progress.isVisible = isLoading
        }
        viewModel.isSignInButtonVisible.observe(this) { isSignInButtonVisible ->
            btn_sign_in.isVisible = isSignInButtonVisible
        }
        viewModel.user.observe(this) { user ->
            tv_username.text = user?.username
            iv_user.setImageDrawable(BitmapDrawable(resources, user?.userImage))
        }
        viewModel.isUserViewVisible.observe(this) { isUserViewVisible ->
            view_user.isVisible = isUserViewVisible
        }
        viewModel.feed.observe(this) { feed ->
            feedAdapter.submitList(feed)
        }
        viewModel.alertMessage.observe(this) { alertMessage ->
            if (alertMessage.isNullOrBlank()) return@observe
            displayAlert(alertMessage)
        }
        viewModel.confirmSignIn.observe(this) { confirmSignIn ->
            if (!confirmSignIn) return@observe
            displaySignInDialog()
        }
    }

    private fun displaySignInDialog() {
        AlertDialog.Builder(this)
            .setMessage(R.string.alert_confirm_sign_in)
            .setPositiveButton(R.string.alert_button_confirm) { _, _ -> viewModel.signIn() }
            .setNegativeButton(R.string.alert_button_cancel, null)
            .show()
    }

    private fun displayAlert(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setNeutralButton(R.string.alert_button_confirm, null)
            .show()
    }

    private fun toggleResponse(isChecked: Boolean) {
        if (isChecked) {
            toggle_response.text = getString(R.string.success)
            (application as MyApplication).mockSuccess()
        } else {
            toggle_response.text = getString(R.string.failure)
            (application as MyApplication).mockFailure()
        }
    }

}
