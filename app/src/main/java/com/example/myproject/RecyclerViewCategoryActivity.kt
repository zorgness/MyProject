package com.example.myproject

import SHAREDPREF_NAME
import SHAREDPREF_SESSION_TOKEN
import SHAREDPREF_SESSION_USERNAME
import SHAREDPREF_SESSION_USER_ID
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.adapter.CategoryAdapter
import com.example.myproject.databinding.ActivityRecyclerViewCategoryBinding
import com.example.myproject.viewmodel.CategoryViewModel
import myToast

class RecyclerViewCategoryActivity : AppCompatActivity() {

    private val myViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRecyclerViewCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = myViewModel
        binding.lifecycleOwner = this

        binding.rvCategory.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter()
        binding.rvCategory.adapter = categoryAdapter



        myViewModel.categoriesLiveData.observe(this) { categories ->
            categoryAdapter.setCategories(categories)
        }

        //LOGOUT
        fun logout() {
            with(applicationContext.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)) {

                edit().remove(SHAREDPREF_SESSION_TOKEN).remove(SHAREDPREF_SESSION_USERNAME)
                    .remove(SHAREDPREF_SESSION_USER_ID).apply()
                startActivity(Intent(this@RecyclerViewCategoryActivity, LoginActivity::class.java))
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