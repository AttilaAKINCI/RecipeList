package com.akinci.recipelist.features.listing.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akinci.recipelist.common.components.GlideApp
import com.akinci.recipelist.databinding.RowRecipeBinding
import com.akinci.recipelist.features.acommon.data.output.RecipeResponse

class RecipeListAdapter(private val clickListener: (RecipeResponse, View) -> Unit) : ListAdapter<RecipeResponse, RecipeListAdapter.RecipeViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecipeViewHolder(RowRecipeBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class RecipeViewHolder(val binding: RowRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : RecipeResponse, clickListener: (RecipeResponse, View) -> Unit) {
            binding.recipeImg.transitionName = item.imageUrl
            binding.rowRecipeCardView.setOnClickListener { clickListener.invoke(item, binding.recipeImg) }
            binding.data = item

            GlideApp.with(binding.recipeImg.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.recipeImg)

            binding.executePendingBindings()
        }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<RecipeResponse>() {
    override fun areItemsTheSame(oldItem: RecipeResponse, newItem: RecipeResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeResponse, newItem: RecipeResponse): Boolean {
        return oldItem == newItem
    }
}