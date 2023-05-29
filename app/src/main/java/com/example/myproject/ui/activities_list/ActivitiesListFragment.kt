package com.example.myproject.ui.activities_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.databinding.FragmentActivitiesListBinding

import com.example.myproject.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitiesListFragment : Fragment() {

    private var _binding: FragmentActivitiesListBinding? = null
    private val  binding get() = _binding!!
    private val viewModel: ActivitiesListViewModel by viewModels()
    private lateinit var activitiesByCategoryAdapter: ActivitiesByCategoryAdapter
    private val args: ActivitiesListFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitiesByCategoryAdapter = ActivitiesByCategoryAdapter()

        viewModel.fetchActivitiesByCategory(categoryId = args.categoryId)

        viewModel.messageLiveData.observe(this) {message ->
            requireContext().myToast(message)
        }

        viewModel.listToShowLiveData.observe(this)  { activitiesByCategory->
                activitiesByCategoryAdapter.submitList(activitiesByCategory)
                if(activitiesByCategory.isEmpty()) {
                    binding.tvEmptyCategory.visibility = View.VISIBLE
                }
        }

        // SET THE ACTIVITY DTO TO BE PASS TO DETAILS FRAGMENT
        activitiesByCategoryAdapter.setOnItemClick { activityItem->
            viewModel.setActivityEvent(activityItem)
        }

        viewModel.activityEventLiveData.observe(this) {activityDto->
           ActivitiesListFragmentDirections
               .actionActivitiesByCategoryFragmentToDetailsActivityFragment(activityDto).let {
                    findNavController().navigate(it)
                }
        }

        viewModel.progressBarVisibilityLiveData.observe(this) {
            binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentActivitiesListBinding.inflate(layoutInflater)

        binding.rvActivityEvent.layoutManager = LinearLayoutManager(container?.context)

        binding.rvActivityEvent.adapter = activitiesByCategoryAdapter

        binding.progressBar.visibility = View.GONE

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}