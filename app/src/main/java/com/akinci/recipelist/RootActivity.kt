package com.akinci.recipelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.akinci.recipelist.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var navigationController: NavController
    private lateinit var binding : ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_root)

        // setup navigation
        navigationController = findNavController(R.id.nav_host_fragment)
        // tell navigation controller that which fragments will be at the top of backstack
        // (hides backbutton for fragments which are placed at top)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.splashScreenFragment,
                R.id.recipeListFragment
            )
        )

        // remove extra padding between arrow and toolbar title
        binding.toolbar.contentInsetStartWithNavigation = 10

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navigationController, appBarConfiguration)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}