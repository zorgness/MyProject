package com.example.myproject.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.adapter.CategoryAdapter
import com.example.myproject.databinding.FragmentCategoryBinding
import myToast


class CategoryFragment : Fragment() {

    private val myViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myViewModel.categoriesLiveData.observe(this) { categories ->
            categoryAdapter.submitList(categories)
        }

        myViewModel.errorMessageLiveData.observe(this) {errorMessage ->

           activity?.myToast(errorMessage)
        }


        myViewModel.progressBarVisibilityLiveData.observe(this) {

            progressBar.visibility = if(it) View.VISIBLE else View.GONE
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


        progressBar = binding.pBarCategory





        return binding.root
    }

}