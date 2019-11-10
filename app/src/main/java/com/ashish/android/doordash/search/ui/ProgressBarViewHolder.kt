package com.ashish.android.doordash.search.ui

import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashish.android.doordash.databinding.ProgressBarBinding
import com.ashish.android.doordash.search.domain.State

class ProgressBarViewHolder(val binding: ProgressBarBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(status: State?) {
        if (status == State.LOADING) {
            binding.progressBar.visibility = VISIBLE
        } else {
            binding.progressBar.visibility = INVISIBLE
        }
    }

    companion object {
        fun create(parent: ViewGroup): ProgressBarViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProgressBarBinding.inflate(inflater)
            return ProgressBarViewHolder(binding)
        }
    }
}