package com.example.myproject

import SHAREDPREF_NAME
import SHAREDPREF_SESSION_TOKEN
import SHAREDPREF_SESSION_USERNAME
import SHAREDPREF_SESSION_USER_ID
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import myToast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment
                || destination.id == R.id.registerFragment
                || destination.id == R.id.splashFragment
            ) {
                binding.bottomNav.visibility = View.GONE
                binding.linearLayoutTopBar.visibility = View.GONE
            } else {

                binding.bottomNav.visibility = View.VISIBLE
                binding.linearLayoutTopBar.visibility = View.VISIBLE
            }
        }


        //LOGOUT
        fun logout() {
            with(applicationContext.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)) {

                edit().remove(SHAREDPREF_SESSION_TOKEN).remove(SHAREDPREF_SESSION_USERNAME)
                    .remove(SHAREDPREF_SESSION_USER_ID).apply()

                navController.navigate(R.id.loginFragment)

            }
        }

        binding.btnHome.setOnClickListener {
            navController.navigate(R.id.categoryFragment)
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }

        binding.btnProfile.setOnClickListener {
            navController.navigate(R.id.profileFragment)
        }




         bottomNav.setOnItemSelectedListener { item ->
               when (item.itemId) {

                   R.id.btn_notification -> {
                       myToast("no notification")
                       true
                   }

                   R.id.btn_new_event -> {
                       myToast("new form")
                       true
                   }
                   R.id.btn_history-> {
                       navController.navigate(R.id.userHistoryFragment)
                       true
                   }

                   else -> {
                       myToast("error")
                       true
                   }
               }
           }


    }

}
