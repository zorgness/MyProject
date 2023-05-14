package com.example.myproject.ui.activities_by_category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.databinding.FragmentActivitiesByCategoryBinding
import com.example.myproject.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitiesByCategoryFragment : Fragment() {

    private var _binding: FragmentActivitiesByCategoryBinding? = null
    private val  binding get() = _binding!!
    private val activitiesByCatViewModel: ActivitiesByCategoryViewModel by viewModels()
    private lateinit var activitiesByCategoryAdapter: ActivitiesByCategoryAdapter
    private val args: ActivitiesByCategoryFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitiesByCategoryAdapter = ActivitiesByCategoryAdapter()

        activitiesByCatViewModel.messageLiveData.observe(this) {message ->
            requireContext().myToast(message)
        }

       activitiesByCatViewModel.fetchActivityEventByCategory(args.categoryId)

        activitiesByCatViewModel.activityEventByCategoryLiveData.observe(this)  {activitiesEventsByCategory->
            activitiesByCategoryAdapter.submitList(activitiesEventsByCategory)
        }

        activitiesByCategoryAdapter.setOnItemClick { activityEventItem->
            activitiesByCatViewModel.setActivityEvent(activityEventItem)
        }

       activitiesByCatViewModel.activityEventLiveData.observe(this) {activityEventItem->
           ActivitiesByCategoryFragmentDirections
               .actionActivitiesByCategoryFragmentToDetailsActivityFragment(activityEventItem).let {
                    findNavController().navigate(it)
                }
        }

        activitiesByCatViewModel.progressBarVisibilityLiveData.observe(this) {
            binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
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