package com.sarftec.lifehacks.view.recycler.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sarftec.lifehacks.databinding.LayoutHackItemBinding
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.view.listerner.HackListListener
import com.sarftec.lifehacks.view.viewmodel.BookmarkViewModel

class BookmarkItemViewHolder private constructor(
    layoutBinding: LayoutHackItemBinding,
    private val holderDependency: ViewHolderDependency
) : BaseHackListViewHolder(layoutBinding, holderDependency) {

    override fun onBookmark(hack: Hack) {
       holderDependency.viewModel.bookmark(hack)
    }

    companion object {
        fun getInstance(
            parent: ViewGroup,
            dependency: ViewHolderDependency
        ): BookmarkItemViewHolder {
            return BookmarkItemViewHolder(
                LayoutHackItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                dependency
            )
        }
    }

    class ViewHolderDependency(
        context: Context,
        hackListListener: HackListListener,
        val viewModel: BookmarkViewModel,
        onClick: (Hack, Int) -> Unit
    ) : BaseHackListViewHolder.BaseHackListDependency(context, hackListListener, onClick)
}