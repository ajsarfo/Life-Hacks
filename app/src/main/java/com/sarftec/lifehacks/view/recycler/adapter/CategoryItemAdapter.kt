package com.sarftec.lifehacks.view.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifehacks.domain.model.Category
import com.sarftec.lifehacks.view.recycler.viewholder.CategoryItemViewHolder
import com.sarftec.lifehacks.view.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope

class CategoryItemAdapter(
    coroutineScope: CoroutineScope,
    viewModel: HomeViewModel,
    onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryItemViewHolder>() {

    private var items = listOf<Category>()

    private val dependency = CategoryItemViewHolder.ViewHolderDependency(
        coroutineScope,
        viewModel,
        onClick
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder.getInstance(
            parent,
            dependency
        )
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
       holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitData(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }
}