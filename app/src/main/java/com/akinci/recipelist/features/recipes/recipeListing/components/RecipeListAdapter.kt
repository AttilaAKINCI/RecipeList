package com.akinci.recipelist.features.recipes.recipeListing.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akinci.recipelist.commons.components.list.BaseViewHolder
import com.akinci.recipelist.commons.components.list.RecyclerViewClickListener
import com.akinci.recipelist.commons.data.model.ListRowDataContract
import com.akinci.recipelist.databinding.RowRecipeBinding
import com.akinci.recipelist.databinding.RowShimmerBinding
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeEntity

class RecipeListAdapter(private val clickListener: RecyclerViewClickListener) :
    ListAdapter<ListRowDataContract, RecyclerView.ViewHolder>(DiffCallBack()) {

    private val RECIPE_ROW = 0
    private val SHIMMER_ROW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            RECIPE_ROW -> RecipeViewHolder(RowRecipeBinding.inflate(layoutInflater, parent, false))
            SHIMMER_ROW -> ShimmerViewHolder(RowShimmerBinding.inflate(layoutInflater, parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is RecipeEntity -> RECIPE_ROW
            else -> SHIMMER_ROW
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if(holder is BaseViewHolder){ holder.bind(item, position, clickListener) }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<ListRowDataContract>() {
    override fun areItemsTheSame(oldItem: ListRowDataContract, newItem: ListRowDataContract): Boolean {
        if(oldItem is RecipeEntity && newItem is RecipeEntity){
            return oldItem.title == newItem.title
        }
        return false;
    }

    override fun areContentsTheSame(oldItem: ListRowDataContract, newItem: ListRowDataContract): Boolean {
        if(oldItem is RecipeEntity && newItem is RecipeEntity){
            return oldItem.title == newItem.title
        }
        return false
    }
}