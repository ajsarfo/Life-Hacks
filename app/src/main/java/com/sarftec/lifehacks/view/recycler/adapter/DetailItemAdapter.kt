package com.sarftec.lifehacks.view.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.view.recycler.viewholder.DetailItemViewHolder

class DetailItemAdapter : RecyclerView.Adapter<DetailItemViewHolder>() {

    private var items = listOf<Hack>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {
      return DetailItemViewHolder.getInstance(parent)
    }

    override fun onBindViewHolder(holder: DetailItemViewHolder, position: Int) {
       holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitData(items: List<Hack>) {
        this.items = items
        notifyDataSetChanged()
    }
}