package com.sarftec.lifehacks.view.recycler.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifehacks.databinding.LayoutDetailItemBinding
import com.sarftec.lifehacks.domain.model.Hack

class DetailItemViewHolder private constructor(
    private val layoutBinding: LayoutDetailItemBinding
) : RecyclerView.ViewHolder(layoutBinding.root) {

    fun bind(hack: Hack) {
        layoutBinding.message.text = hack.message
    }

    companion object {
        fun getInstance(parent: ViewGroup) : DetailItemViewHolder {
            return DetailItemViewHolder(
                LayoutDetailItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}