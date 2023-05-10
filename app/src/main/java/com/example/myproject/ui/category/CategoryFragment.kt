package com.example.myproject.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.adapter.CategoryAdapter
import com.example.myproject.databinding.FragmentCategoryBinding
import com.example.myproject.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val myViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryAdapter = CategoryAdapter()

        categoryAdapter.setOnItemClick { categoryId ->
            myViewModel.setCategoryId(categoryId)
        }

        myViewModel.categoriesLiveData.observe(this) { categories ->
            categoryAdapter.submitList(categories)
        }

        myViewModel.errorMessageLiveData.observe(this) { errorMessage ->

            requireContext().myToast(errorMessage)
        }

        myViewModel.categoryIdLiveData.observe(this) { categoryId ->
              val navDir = CategoryFragmentDirections.actionCategoryFragmentToActivityByCategoryFragment(categoryId )
              findNavController().navigate(navDir)
        }



        myViewModel.progressBarVisibilityLiveData.observe(this) {

            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCategoryBinding.inflate(layoutInflater)


        binding.viewModel = myViewModel
        binding.lifecycleOwner = this

        binding.rvCategory.layoutManager = LinearLayoutManager(container?.context)

        binding.rvCategory.adapter = categoryAdapter


        progressBar = binding.pBarCategory





        return binding.root
    }

}