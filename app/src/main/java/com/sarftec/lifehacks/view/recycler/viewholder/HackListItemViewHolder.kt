package com.sarftec.lifehacks.view.recycler.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sarftec.lifehacks.databinding.LayoutHackItemBinding
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.view.listerner.HackListListener
import com.sarftec.lifehacks.view.viewmodel.HackListViewModel

class HackListItemViewHolder(
    layoutHackItemBinding: LayoutHackItemBinding,
    dependency: ViewHolderDependency
) : BaseHackListViewHolder(layoutHackItemBinding, dependency) {

    override fun onBookmark(hack: Hack) {

    }

    companion object {
        fun getInstance(
            parent: ViewGroup,
            dependency: ViewHolderDependency
        ): HackListItemViewHolder {
            return HackListItemViewHolder(
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
        val viewModel: HackListViewModel,
        onClick: (Hack, Int) -> Unit
    ) : BaseHackListDependency(context, hackListListener, onClick)
}