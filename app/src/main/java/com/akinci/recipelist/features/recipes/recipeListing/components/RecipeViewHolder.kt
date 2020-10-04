package com.akinci.recipelist.features.recipes.recipeListing.components

import com.akinci.recipelist.commons.components.list.BaseViewHolder
import com.akinci.recipelist.commons.components.list.RecyclerViewClickListener
import com.akinci.recipelist.databinding.RowRecipeBinding
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeEntity
import com.bumptech.glide.Glide

class RecipeViewHolder constructor(val binding: RowRecipeBinding) : BaseViewHolder(binding.root){

    override fun bind(item : Any, position: Int, clickListener: RecyclerViewClickListener?) {
        if(item is RecipeEntity){
            binding.rowRecipeCardView.setOnClickListener {
                clickListener?.onClick(item, binding.rowRecipeCardView)
            }

            binding.data = item
            binding.executePendingBindings()

            binding.rowRecipeCardView.transitionName = item.imageUrl
            Glide.with(binding.recipeImg.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.recipeImg)
        }
    }
}
