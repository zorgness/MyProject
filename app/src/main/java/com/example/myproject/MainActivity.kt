package com.example.myproject

import SHAREDPREF_NAME
import SHAREDPREF_SESSION_TOKEN
import SHAREDPREF_SESSION_USERNAME
import SHAREDPREF_SESSION_USER_ID
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.myproject.databinding.ActivityMainBinding
import com.example.myproject.fragment.CategoryFragment
import myToast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        //LOGOUT
        fun logout() {
            with(applicationContext.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)) {

                edit().remove(SHAREDPREF_SESSION_TOKEN).remove(SHAREDPREF_SESSION_USERNAME)
                    .remove(SHAREDPREF_SESSION_USER_ID).apply()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()

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
