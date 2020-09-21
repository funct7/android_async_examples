package com.example.asyncexamples.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asyncexamples.R

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parentView: ViewGroup): PostViewHolder = LayoutInflater
            .from(parentView.context)
            .inflate(R.layout.layout_post, parentView, false)
            .let { PostViewHolder(it) }
    }

}