package com.example.myproject.ui.activities_by_category

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
import com.example.myproject.adapter.ActivityEventByCategoryAdapter
import com.example.myproject.databinding.FragmentActivityByCategoryBinding
import com.example.myproject.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityByCategoryFragment : Fragment() {

    private var _binding: FragmentActivityByCategoryBinding? = null
    private val  binding get() = _binding!!
    private val myViewModel: ActivityByCategoryViewModel by viewModels()
    private lateinit var activityEventByCategoryAdapter: ActivityEventByCategoryAdapter
    private val args: ActivityByCategoryFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        myViewModel.activityEventByCategoryLiveData.observe(this, Observer {activitiesEventsByCategory->
            activityEventByCategoryAdapter.submitList(activitiesEventsByCategory)
        })

        myViewModel.messageLiveData.observe(this) {message ->

            requireContext().myToast(message)
        }


        myViewModel.progressBarVisibilityLiveData.observe(this) {

            binding.pBarCategory.visibility = if(it) View.VISIBLE else View.GONE
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityByCategoryBinding.inflate(layoutInflater)

        binding.rvActivityEvent.layoutManager = LinearLayoutManager(container?.context)
        activityEventByCategoryAdapter = ActivityEventByCategoryAdapter()
        binding.rvActivityEvent.adapter = activityEventByCategoryAdapter

        myViewModel.getActivityEventByCategory(args.categoryId)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}