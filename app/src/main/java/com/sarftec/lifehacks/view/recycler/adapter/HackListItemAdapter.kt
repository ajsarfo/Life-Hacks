package com.sarftec.lifehacks.view.recycler.adapter

import android.content.Context
import android.view.ViewGroup
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.view.listerner.HackListListener
import com.sarftec.lifehacks.view.recycler.viewholder.BaseHackListViewHolder
import com.sarftec.lifehacks.view.recycler.viewholder.HackListItemViewHolder
import com.sarftec.lifehacks.view.viewmodel.HackListViewModel

class HackListItemAdapter(
    context: Context,
    listener: HackListListener,
    viewModel: HackListViewModel,
    onClick: (Hack, Int) -> Unit
) : BaseHackListAdapter() {

    private val viewHolders = hashSetOf<HackListItemViewHolder>()

    override val dependency: HackListItemViewHolder.ViewHolderDependency by lazy {
        HackListItemViewHolder.ViewHolderDependency(
            context,
            listener,
            viewModel,
            onClick
        )
    }

    override fun onBindViewHolder(holder: BaseHackListViewHolder, position: Int) {
        viewHolders.add(holder as HackListItemViewHolder)
        super.onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHackListViewHolder {
        return HackListItemViewHolder.getInstance(parent, dependency = dependency)
    }

    fun rebindHacks(hacks: Collection<Hack>) {
        hacks.forEach { hack ->
            viewHolders.forEach { it.rebind(hack) }
        }
    }
}