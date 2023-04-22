package com.example.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.adapter.CategoryAdapter
import com.example.myproject.databinding.ActivityRecyclerViewCategoryBinding
import com.example.myproject.viewmodel.CategoryViewModel

class RecyclerViewCategoryActivity : AppCompatActivity() {

    private val myViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =  ActivityRecyclerViewCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = myViewModel
        binding.lifecycleOwner = this



        binding.rvCategory.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter()
        binding.rvCategory.adapter = categoryAdapter



        myViewModel.categoriesLiveData.observe(this) {categories->
            categoryAdapter.setCategories(categories)
        }

    }
}