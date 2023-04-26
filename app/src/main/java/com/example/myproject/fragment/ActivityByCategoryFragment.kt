package com.example.myproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.R
import com.example.myproject.adapter.ActivityEventByCategoryAdapter
import com.example.myproject.adapter.CategoryAdapter
import com.example.myproject.databinding.FragmentActivityByCategoryBinding
import com.example.myproject.viewmodel.ActivityByCategoryViewModel
import myToast


class ActivityByCategoryFragment : Fragment() {

    private val myViewModel: ActivityByCategoryViewModel by viewModels()
    private lateinit var activityEventByCategoryAdapter: ActivityEventByCategoryAdapter
    private lateinit var progressBar: ProgressBar

    private val args:ActivityByCategoryFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        myViewModel.getActivityEventByCategory(args.categoryId)

        myViewModel.activityEventByCategoryLiveData.observe(this, Observer {activitiesEventsByCategory->
            activityEventByCategoryAdapter.submitList(activitiesEventsByCategory)
        })

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
    ): View {
        val binding = FragmentActivityByCategoryBinding.inflate(layoutInflater)

        binding.rvActivityEvent.layoutManager = LinearLayoutManager(container?.context)
        activityEventByCategoryAdapter = ActivityEventByCategoryAdapter()
        binding.rvActivityEvent.adapter = activityEventByCategoryAdapter


        progressBar = binding.pBarCategory
        return binding.root
    }


}