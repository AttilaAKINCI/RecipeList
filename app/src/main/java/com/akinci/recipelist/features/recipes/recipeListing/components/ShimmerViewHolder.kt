package com.akinci.recipelist.features.recipes.recipeListing.components

import com.akinci.recipelist.commons.components.list.BaseViewHolder
import com.akinci.recipelist.commons.components.list.RecyclerViewClickListener
import com.akinci.recipelist.databinding.RowShimmerBinding
import com.akinci.recipelist.features.recipes.recipeListing.data.model.ShimmerModel

class ShimmerViewHolder constructor(val binding: RowShimmerBinding) : BaseViewHolder(binding.root){
    override fun bind(item : Any, position: Int, clickListener: RecyclerViewClickListener?) {
        if(item is ShimmerModel){
            binding.shimmerViewContainer.startShimmer()
        }
    }
}