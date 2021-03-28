package com.akinci.recipelist.features.detail.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.akinci.recipelist.R
import com.akinci.recipelist.common.components.GlideApp
import com.akinci.recipelist.common.components.Tag
import com.akinci.recipelist.databinding.FragmentRecipeDetailBinding
import com.akinci.recipelist.features.detail.viewmodel.RecipeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    private lateinit var binding : FragmentRecipeDetailBinding
    private val recipeDetailViewModel : RecipeDetailViewModel by viewModels()

    /** fragment args. **/
    private val recipeDetailFragmentArgs by lazy { RecipeDetailFragmentArgs.fromBundle(requireArguments()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** Initialization of ViewBinding not need for DataBinding here **/
        binding = FragmentRecipeDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        // used for imageView transition.
        binding.recipeImg.transitionName = recipeDetailFragmentArgs.transitionString
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // fetch selected recipe
        recipeDetailViewModel.fetchRecipe(recipeDetailFragmentArgs.contentId)

        Handler(Looper.getMainLooper()).postDelayed({
            /** delayed content view **/
            binding.recipeInfoView.animate()
                .alpha(1.0f)
                .setDuration(200)
                .start()

            // fix card elevation here
            binding.rowRecipeCardView.cardElevation = resources.getDimension(R.dimen.cardview_default_elevation)

            // fix card title
            binding.recipeDetailTitleBackground.visibility = View.VISIBLE

        }, 400)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlideApp.with(binding.recipeImg.context)
            .load(recipeDetailFragmentArgs.transitionString)
            .centerCrop()
            .into(binding.recipeImg)

        recipeDetailViewModel.recipe.observe(viewLifecycleOwner, { response ->
            if (!response.chefName.isNullOrEmpty()) {
                binding.recipeChefValue.text = response.chefName
            } else {
                binding.recipeChefTitle.visibility = View.GONE
                binding.recipeChefValue.visibility = View.GONE
            }
            binding.rowTitle.text = response.title
            binding.recipeDetailTitleBackground.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.halfTransparentWhite
                )
            )
            binding.recipeCalorieValue.text = response.calories.toString()
            binding.recipeDescriptionValue.text = response.description

            binding.tagHolder.visibility = View.GONE
            response.tags?.let {
                if(it.isNotEmpty()) {
                    binding.tagHolder.visibility = View.VISIBLE

                    it.map { tag ->
                        if (tag.isNotBlank()) {
                            val button = Tag(requireContext())
                            button.text = tag
                            binding.tagContainer.addView(button)
                        }
                    }
                }
            }
        })


    }
}