package com.akinci.recipelist.features.splash

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.akinci.recipelist.R
import com.akinci.recipelist.databinding.FragmentSplashScreenBinding
import timber.log.Timber

class SplashScreenFragment : Fragment() {
    private lateinit var binding : FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** Initialization of ViewBinding not need for DataBinding here **/
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.animation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) { navigateToListing() }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })

        Timber.d("SplashFragment created..")
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.animation.playAnimation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //hide appbar on splash screen
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    fun navigateToListing(){
        Handler(Looper.getMainLooper()).postDelayed({
            /** Navigate to Recipe List Page **/
            NavHostFragment.findNavController(this).navigate(R.id.action_splashScreenFragment_to_recipeListFragment)
        }, 100)
    }
}