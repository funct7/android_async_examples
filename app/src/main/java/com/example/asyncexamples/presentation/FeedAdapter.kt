package com.example.asyncexamples.presentation

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.asyncexamples.application.Post
import kotlinx.android.synthetic.main.layout_post.view.*

class FeedAdapter(
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Post, PostViewHolder>(PostDiff) {

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder.create(parent)

    override
    fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.apply {
            item.userImage.observe(lifecycleOwner) { userImage ->
                iv_post_user.setImageDrawable(BitmapDrawable(resources, userImage))
            }
            item.username.observe(lifecycleOwner) { username ->
                tv_post_username.text = username
            }
            tv_content.text = item.content
        }
    }

}

private object PostDiff : DiffUtil.ItemCallback<Post>() {
    
    override
    fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id

    override
    fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.userID == newItem.userID
                && oldItem.username.value == newItem.username.value
                && isSameBitmap(oldItem.userImage.value, newItem.userImage.value)
                && oldItem.content == newItem.content

}

private fun isSameBitmap(lhs: Bitmap?, rhs: Bitmap?): Boolean {
    if (lhs == null && rhs == null) return true
    if (lhs != null && rhs != null) return lhs.sameAs(rhs)
    return false
}