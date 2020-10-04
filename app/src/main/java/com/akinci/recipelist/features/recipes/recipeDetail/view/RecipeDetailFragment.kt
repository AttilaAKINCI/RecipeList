package com.akinci.recipelist.features.recipes.recipeDetail.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.akinci.recipelist.R
import com.akinci.recipelist.commons.components.fragment.BaseFragment
import com.akinci.recipelist.commons.components.widget.Tag
import com.akinci.recipelist.commons.data.coroutines.Status
import com.akinci.recipelist.databinding.FragmentRecipeDetailBinding
import com.akinci.recipelist.features.recipes.recipeDetail.viewmodel.RecipeDetailViewModel
import com.bumptech.glide.Glide


class RecipeDetailFragment : BaseFragment() {

    private lateinit var binding : FragmentRecipeDetailBinding
    private lateinit var transitionString: String
    private var recipeId: Long = 0

    private val viewModel : RecipeDetailViewModel by lazy {
        val activity = requireNotNull(this.activity) { "viewModel will be created after onActivityCreated() lifecycle function" }

        ViewModelProvider(
            this, RecipeDetailViewModel.Factory(
                activity.application,
                recipeId
            )
        ).get(RecipeDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val recipeDetailArgs = RecipeDetailFragmentArgs.fromBundle(requireArguments())
        recipeId = recipeDetailArgs.recipeId
        transitionString = recipeDetailArgs.transitionString

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_detail,
            container,
            false
        )

        // used for imageView transition.
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        Handler().postDelayed({
            binding.recipeInfoView.animate()
                .alpha(1.0f)
                .setDuration(200)
                .start()
        }, 400)

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rowRecipeCardView.transitionName = transitionString
        Glide.with(binding.recipeImg.context)
            .load(transitionString)
            .centerCrop()
            .into(binding.recipeImg)

        viewModel.fetchData().observe(viewLifecycleOwner, Observer {
            it?.let { it ->
                if (it.status == Status.SUCCESS) {
                    if (!it.data?.chefName.isNullOrEmpty()) {
                        binding.recipeChefValue.text = it.data?.chefName
                    } else {
                        binding.recipeChefTitle.visibility = View.GONE
                        binding.recipeChefValue.visibility = View.GONE
                    }
                    binding.rowTitle.text = it.data?.title
                    binding.recipeDetailTitleBackground.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.halfTransparentWhite
                        )
                    )
                    binding.recipeCalorieValue.text = it.data?.calories.toString()
                    binding.recipeDescriptionValue.text = it.data?.description

                    if (it.data?.tags?.isBlank()!!) {
                        binding.tagHolder.visibility = View.GONE
                    } else {
                        it.data?.tags?.split(";")?.map { tag ->
                            if (tag.isNotBlank()) {
                                val button = Tag(requireContext())
                                button.text = tag
                                binding.tagContainer.addView(button)
                            }
                        }
                    }
                }
            }
        })
    }
}