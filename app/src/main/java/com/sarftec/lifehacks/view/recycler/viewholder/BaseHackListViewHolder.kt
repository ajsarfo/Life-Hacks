package com.sarftec.lifehacks.view.recycler.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifehacks.R
import com.sarftec.lifehacks.databinding.LayoutHackItemBinding
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.view.file.copy
import com.sarftec.lifehacks.view.file.share
import com.sarftec.lifehacks.view.file.toBitmap
import com.sarftec.lifehacks.view.file.toast
import com.sarftec.lifehacks.view.listerner.HackListListener

abstract class BaseHackListViewHolder(
    private val layoutBinding: LayoutHackItemBinding,
    private val dependency: BaseHackListDependency
) : RecyclerView.ViewHolder(layoutBinding.root) {

    protected var hack: Hack? = null

    abstract fun onBookmark(hack: Hack)

    fun bind(hack: Hack, position: Int) {
        this.hack = hack
        layoutBinding.imageView.setBackgroundColor(
            dependency.colorCache.getColor(hack)
        )
        setupButtonListeners(hack)
        setBookmark(hack.isFavorite)
        layoutBinding.message.text = hack.message
        layoutBinding.upperLayout.setOnClickListener { dependency.onClick(hack, position) }
    }

    protected fun setBookmark(isBookmark: Boolean) {
        layoutBinding.bookmarkIcon.setImageResource(
            if(isBookmark) R.drawable.ic_bookmark else R.drawable.ic_un_bookmark
        )
    }

    private fun setupButtonListeners(hack: Hack) {
        layoutBinding.copy.setOnClickListener {
            itemView.context.copy(hack.message, "Copy")
            itemView.context.toast("Copied to Clipboard")
        }
        layoutBinding.share.setOnClickListener {
            itemView.context.share(hack.message, "Share")
        }
        layoutBinding.download.setOnClickListener { _ ->
          dependency.listener.showLoadingDialog(true)
           dependency.listener.getRewardVideoManager().showRewardVideo {
               dependency.listener.showLoadingDialog(false)
               layoutBinding.upperLayout.toBitmap {
                   dependency.listener.toolingHandler().saveImage(it)
               }
           }
        }
        layoutBinding.bookmark.setOnClickListener {
            hack.isFavorite = !hack.isFavorite
            setBookmark(hack.isFavorite)
            onBookmark(hack)
        }
    }

    open class BaseHackListDependency(
        val context: Context,
        val listener: HackListListener,
        val onClick: (Hack, Int) -> Unit
    ) {
        val colorCache: ColorCache = ColorCache(context)
    }

    class ColorCache(private val context: Context) {

        private val colorsArray = context.resources.getIntArray(R.array.hack_colors)

        private val colorMap = hashMapOf<Int, Int>()

        fun getColor(hack: Hack) : Int {
           return colorMap.getOrPut(hack.id) {
              colorsArray.random()
           }
        }
    }
}