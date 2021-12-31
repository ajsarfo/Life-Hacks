package com.sarftec.lifehacks.view.recycler.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.view.recycler.viewholder.BaseHackListViewHolder

abstract class BaseHackListAdapter : RecyclerView.Adapter<BaseHackListViewHolder>() {

    protected abstract val dependency: BaseHackListViewHolder.BaseHackListDependency

    protected var items = listOf<Hack>()

    override fun onBindViewHolder(holder: BaseHackListViewHolder, position: Int) {
       holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    fun submitData(items: List<Hack>) {
        this.items = items
        notifyDataSetChanged()
    }
}