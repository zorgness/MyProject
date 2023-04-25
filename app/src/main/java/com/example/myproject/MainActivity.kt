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
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myproject.databinding.ActivityMainBinding
import com.example.myproject.fragment.CategoryFragment
import com.example.myproject.fragment.LoginFragment
import myToast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.splashFragment) {

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
               /* supportFragmentManager.commit {
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    replace(R.id.nav_host, LoginFragment())
                }*/
                navController.navigate(R.id.loginFragment)

            }

        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btn_logout -> {
                    logout()
                    true
                }
                R.id.btn_new_event -> {
                    myToast("new form")
                    true
                }
                else -> {
                    myToast("profile")
                    true
                }
            }
        }


    }

}
