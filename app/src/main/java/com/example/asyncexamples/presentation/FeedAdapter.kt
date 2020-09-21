package com.example.asyncexamples.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.asyncexamples.application.Post

class FeedAdapter : ListAdapter<Post, PostViewHolder>(PostDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}

private object PostDiff : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        TODO("Not yet implemented")
    }

}
