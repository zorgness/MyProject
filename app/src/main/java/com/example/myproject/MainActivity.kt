package com.example.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import com.example.myproject.extensions.myToast
import com.example.myproject.utils.myPicassoFun


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val myViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = myViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)


        // FIND ANOTHER SOLUTION
        with(binding) {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.loginFragment
                    || destination.id == R.id.registerFragment
                    || destination.id == R.id.splashFragment
                ) {
                    bottomNav.visibility = View.GONE
                    linearLayoutTopBar.visibility = View.GONE
                } else {
                    bottomNav.visibility = View.VISIBLE
                    linearLayoutTopBar.visibility = View.VISIBLE
                }
            }
        }
        // FIND ANOTHER SOLUTION

        myViewModel.fetchUserImageUrl()

        myViewModel.imageUrlLiveData.observe(this)  { url ->
            myPicassoFun(url, binding.btnProfile)
        }

        myViewModel.isLoggedOutLiveData.observe(this) { isLoggedOut ->
            if (isLoggedOut) {
                //NOT GOOD
                navController.navigate(R.id.loginFragment)
            }
        }

        myViewModel.isGoToHome.observe(this) { isGoToHome ->
            if (isGoToHome) {
                //NOT GOOD
                navController.navigate(R.id.categoryFragment)
            }
        }

        myViewModel.isGoToProfile.observe(this) { isGoToProfile ->
            if (isGoToProfile) {
                //NOT GOOD
                navController.navigate(R.id.profileFragment)
            }
        }


    }

}
