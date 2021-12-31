package com.sarftec.lifehacks.view.recycler.adapter

import android.content.Context
import android.view.ViewGroup
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.view.listerner.HackListListener
import com.sarftec.lifehacks.view.recycler.viewholder.BaseHackListViewHolder
import com.sarftec.lifehacks.view.recycler.viewholder.BookmarkItemViewHolder
import com.sarftec.lifehacks.view.viewmodel.BookmarkViewModel

class BookmarkItemAdapter(
    context: Context,
    listener: HackListListener,
    private val viewModel: BookmarkViewModel,
    onClick: (Hack, Int) -> Unit
) : BaseHackListAdapter() {

    override val dependency: BookmarkItemViewHolder.ViewHolderDependency by lazy {
        BookmarkItemViewHolder.ViewHolderDependency(
            context,
            listener,
            viewModel,
            onClick
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHackListViewHolder {
        return BookmarkItemViewHolder.getInstance(parent, dependency)
    }

    fun removeHackAtPosition(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
