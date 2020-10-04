package com.akinci.recipelist.features.splash

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.akinci.recipelist.R
import com.akinci.recipelist.commons.components.fragment.BaseFragment
import com.akinci.recipelist.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : BaseFragment() {

    private lateinit var binding : FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false)

        val animator = ValueAnimator.ofFloat(0f, 1f)

        animator.addUpdateListener {
            val value = it.animatedValue as Float
            binding.logo.scaleX = value
            binding.logo.scaleY = value
            binding.logo2.scaleX = value
            binding.logo2.scaleY = value
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) { navigateToListing() }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })

        animator.duration = 1000L
        animator.startDelay = 400L
        animator.start()

        return binding.root
    }

    fun navigateToListing(){
        Handler().postDelayed({
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_splashScreenFragment_to_recipeListFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.splashScreenFragment,
                            true).build()
                )
        }, 1000)
    }
}