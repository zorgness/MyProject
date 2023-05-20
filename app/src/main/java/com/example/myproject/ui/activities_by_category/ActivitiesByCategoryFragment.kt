package com.example.myproject.ui.activities_by_category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.databinding.FragmentActivitiesByCategoryBinding
import com.example.myproject.extensions.myToast
import com.example.myproject.sharedviewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitiesByCategoryFragment : Fragment() {

    private var _binding: FragmentActivitiesByCategoryBinding? = null
    private val  binding get() = _binding!!
    private val viewModel: ActivitiesByCategoryViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var activitiesByCategoryAdapter: ActivitiesByCategoryAdapter
    private val args: ActivitiesByCategoryFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitiesByCategoryAdapter = ActivitiesByCategoryAdapter()

        viewModel.messageLiveData.observe(this) {message ->
            requireContext().myToast(message)
        }

       viewModel.fetchActivityEventByCategory(args.categoryId)

        viewModel.activityEventByCategoryLiveData.observe(this)  {activitiesEventsByCategory->
            if(activitiesEventsByCategory.isEmpty())
                binding.tvEmptyCategory.visibility = View.VISIBLE
            activitiesByCategoryAdapter.submitList(activitiesEventsByCategory)

        }

        activitiesByCategoryAdapter.setOnItemClick { activityEventItem->
            viewModel.setActivityEvent(activityEventItem)
        }

        viewModel.activityEventLiveData.observe(this) {activityEventItem->
           ActivitiesByCategoryFragmentDirections
               .actionActivitiesByCategoryFragmentToDetailsActivityFragment(activityEventItem).let {
                    findNavController().navigate(it)
                }
        }

        viewModel.progressBarVisibilityLiveData.observe(this) {
            binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
        }

        sharedViewModel.refreshListLiveData.observe(this) {categoryId->
            viewModel.fetchActivityEventByCategory(categoryId)
        }



      /*  activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })*/



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentActivitiesByCategoryBinding.inflate(layoutInflater)

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