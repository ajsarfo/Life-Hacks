package com.sarftec.lifehacks.view.recycler.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifehacks.R
import com.sarftec.lifehacks.databinding.LayoutHackItemBinding
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.view.listerner.HackListListener

abstract class BaseHackListViewHolder(
    private val layoutBinding: LayoutHackItemBinding,
    private val dependency: BaseHackListDependency
) : RecyclerView.ViewHolder(layoutBinding.root) {

    private var hack: Hack? = null

    abstract fun onBookmark(hack: Hack)

    fun bind(hack: Hack, position: Int) {
        this.hack = hack
        layoutBinding.imageView.setBackgroundColor(
            dependency.colorCache.getColor(hack)
        )
        setBookmark(hack.isFavorite)
        layoutBinding.message.text = hack.message
        layoutBinding.upperLayout.setOnClickListener { dependency.onClick(hack, position) }
    }

    protected fun setBookmark(isBookmark: Boolean) {
        layoutBinding.bookmarkIcon.setImageResource(
            if(isBookmark) R.drawable.ic_bookmark else R.drawable.ic_un_bookmark
        )
    }

    private fun setupButtonListeners() {

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