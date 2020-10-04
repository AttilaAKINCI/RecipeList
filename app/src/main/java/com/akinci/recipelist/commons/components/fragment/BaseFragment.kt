package com.akinci.recipelist.commons.components.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.akinci.recipelist.RootActivity
import com.akinci.recipelist.features.splash.SplashScreenFragment

abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        when(this) {
            is SplashScreenFragment -> { (activity as AppCompatActivity).supportActionBar?.hide() }
            else -> { (activity as AppCompatActivity).supportActionBar?.show() }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}