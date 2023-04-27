package com.example.myproject

import SHAREDPREF_NAME
import SHAREDPREF_SESSION_TOKEN
import SHAREDPREF_SESSION_USERNAME
import SHAREDPREF_SESSION_USER_ID
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myproject.databinding.ActivityMainBinding
import com.example.myproject.fragment.CategoryFragment
import com.example.myproject.fragment.LoginFragment
import com.example.myproject.fragment.ProfileFragment
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
            } else {

                binding.bottomNav.visibility = View.VISIBLE
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




         bottomNav.setOnItemSelectedListener { item ->
               when (item.itemId) {
                   R.id.btn_logout -> {
                       logout()
                       true
                   }
                   R.id.btn_new_event -> {
                       myToast("new form")
                       true
                   }
                   R.id.profileFragment -> {
                       navController.navigate(R.id.profileFragment)
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
