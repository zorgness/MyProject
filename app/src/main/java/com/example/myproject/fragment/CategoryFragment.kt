package com.example.myproject.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.R
import com.example.myproject.adapter.CategoryAdapter
import com.example.myproject.databinding.FragmentCategoryBinding
import com.example.myproject.viewmodel.CategoryViewModel
import kotlinx.coroutines.NonDisposableHandle.parent


class CategoryFragment : Fragment() {

    private val myViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myViewModel.categoriesLiveData.observe(this) { categories ->
            categoryAdapter.submitList(categories)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        val binding = FragmentCategoryBinding.inflate(layoutInflater)


        binding.viewModel = myViewModel
        binding.lifecycleOwner = this

        binding.rvCategory.layoutManager = LinearLayoutManager(container?.context)
        categoryAdapter = CategoryAdapter()
        binding.rvCategory.adapter = categoryAdapter

        return binding.root
    }

}