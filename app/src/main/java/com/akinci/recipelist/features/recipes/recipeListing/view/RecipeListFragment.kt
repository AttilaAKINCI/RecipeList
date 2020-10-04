package com.akinci.recipelist.features.recipes.recipeListing.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import com.akinci.recipelist.R
import com.akinci.recipelist.commons.components.fragment.BaseFragment
import com.akinci.recipelist.commons.components.list.RecyclerViewClickListener
import com.akinci.recipelist.commons.data.coroutines.Status
import com.akinci.recipelist.commons.data.model.ListRowDataContract
import com.akinci.recipelist.databinding.FragmentRecipeListBinding
import com.akinci.recipelist.features.recipes.recipeListing.components.RecipeListAdapter
import com.akinci.recipelist.features.recipes.recipeListing.data.local.RecipeEntity
import com.akinci.recipelist.features.recipes.recipeListing.data.model.ShimmerModel
import com.akinci.recipelist.features.recipes.recipeListing.viewmodel.RecipeListViewModel
import timber.log.Timber

class RecipeListFragment : BaseFragment() {

    private lateinit var binding : FragmentRecipeListBinding
    private val viewModel : RecipeListViewModel by navGraphViewModels(R.id.navigation_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_list, container, false)

        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, RecipeListViewModel.Factory(activity.application)).get(
            RecipeListViewModel::class.java)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = RecipeListAdapter(RecyclerViewClickListener { data, view ->
            /** click actions for position and data **/
            if (data is RecipeEntity) {
                Toast.makeText(
                    activity,
                    "recipe detail is listing",
                    Toast.LENGTH_LONG
                ).show()

                val extras = FragmentNavigatorExtras(
                    view to data.imageUrl
                )

                NavHostFragment.findNavController(this).navigate(
                    RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(data.recordId, data.imageUrl),
                    extras
                )
            }
        })

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver (){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if(positionStart == 0) {
                    binding.recyclerRecipeList.layoutManager!!.scrollToPosition(0)
                }
            }
        })

        viewModel.listContent.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Timber.i("Loading recipes")
                        resource.data?.let { recipes ->
                            adapter.submitList(recipes as List<ListRowDataContract>?)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        Timber.e(it.message)
                    }
                    Status.LOADING -> {
                        Timber.i("Loading shimmer views")
                        val dummyShimmerList = mutableListOf<ShimmerModel>()
                        for(i in 1..3){ dummyShimmerList.add(ShimmerModel()) }
                        adapter.submitList(dummyShimmerList as List<ListRowDataContract>?)
                    }
                }
            }
        })

        binding.recyclerRecipeList.adapter = adapter
        return binding.root
    }
}