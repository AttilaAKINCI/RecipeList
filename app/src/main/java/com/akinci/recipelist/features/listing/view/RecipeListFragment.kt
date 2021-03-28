package com.akinci.recipelist.features.listing.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.akinci.recipelist.R
import com.akinci.recipelist.common.components.SnackBar
import com.akinci.recipelist.common.components.adapter.ShimmerAdapter
import com.akinci.recipelist.common.helper.Resource
import com.akinci.recipelist.databinding.FragmentRecipeListBinding
import com.akinci.recipelist.features.listing.component.RecipeListAdapter
import com.akinci.recipelist.features.listing.viewmodel.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private lateinit var binding : FragmentRecipeListBinding
    private val viewModel : RecipeListViewModel by activityViewModels() // use activity lifecycle

    private val shimmerAdapter = ShimmerAdapter()
    private lateinit var recipeListAdapter : RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        /** Initialization of ViewBinding not need for DataBinding here **/
        binding = FragmentRecipeListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        //shows appbar on listing screen
        (activity as AppCompatActivity).supportActionBar?.show()
        // set title for first fragment. Probably there is a bug during first fragment initiation.
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_recipe_list)

        recipeListAdapter = RecipeListAdapter(clickListener = { data, view ->
            Timber.d("Navigation to recipe detail fragment")
            val extras = FragmentNavigatorExtras(
                view to data.imageUrl
            )

            NavHostFragment.findNavController(this).navigate(
                RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(data.contentId, data.imageUrl),
                extras
            )
        })

        Timber.d("RecipeListFragment created..")
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //fetch recipes
        viewModel.fetchAllRecipes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listContent.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    // according to fetch mode followers / friends data is coming here
                    Timber.d("Recipes are displayed")
                    binding.recyclerList.adapter = recipeListAdapter
                    recipeListAdapter.submitList(it.data)
                }
                is Resource.Loading -> {
                    Timber.d("Shimmer activated")
                    binding.recyclerList.adapter = shimmerAdapter
                }
                is Resource.Error -> {
                    // show error message on snackBar
                    SnackBar.makeLarge(binding.root, it.message, SnackBar.LENGTH_LONG).show()
                }
            }
        })
    }
}