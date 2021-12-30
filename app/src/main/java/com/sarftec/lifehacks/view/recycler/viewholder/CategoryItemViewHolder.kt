package com.sarftec.lifehacks.view.recycler.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifehacks.databinding.LayoutCategoryItemBinding
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.view.file.loadImage
import com.sarftec.lifehacks.view.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CategoryItemViewHolder private constructor(
    private val layoutBinding: LayoutCategoryItemBinding,
    private val dependency: ViewHolderDependency
) : RecyclerView.ViewHolder(layoutBinding.root) {

    fun bind(category: Category) {
        dependency.coroutineScope.launch {
            layoutBinding.category.text = category.category
            dependency.viewModel.getImage(category).let {
                if(it.isSuccess()) layoutBinding.imageView.loadImage(itemView, it.data!!)
                else {
                    layoutBinding.imageView.setImageBitmap(null)
                    Log.v("TAG", "${it.message}")
                }
            }
        }
    }

    companion object {
        fun getInstance(parent: ViewGroup, dependency: ViewHolderDependency) : CategoryItemViewHolder {
            return CategoryItemViewHolder(
                LayoutCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                dependency
            )
        }
    }

    class ViewHolderDependency(
        val coroutineScope: CoroutineScope,
        val viewModel: HomeViewModel,
        val onClick: (Category) -> Unit
    )
}