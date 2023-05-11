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

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val myViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryAdapter = CategoryAdapter()

        categoryAdapter.setOnItemClick { categoryId ->
            myViewModel.setCategoryId(categoryId)
        }

        myViewModel.categoriesLiveData.observe(this) { categoriesList ->
            categoryAdapter.submitList(categoriesList)
        }

        myViewModel.messageLiveData.observe(this) { message ->
            requireContext().myToast(message)
        }

        myViewModel.categoryIdLiveData.observe(this) { categoryId ->
            CategoryFragmentDirections
                .actionCategoryFragmentToActivityByCategoryFragment(categoryId).let {
                    findNavController().navigate(it)
                }
        }


        myViewModel.progressBarVisibilityLiveData.observe(this) {
            binding.pBarCategory.visibility = if (it) View.VISIBLE else View.GONE
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(layoutInflater)

        binding.viewModel = myViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.rvCategory.layoutManager = LinearLayoutManager(container?.context)
        binding.rvCategory.adapter = categoryAdapter

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}