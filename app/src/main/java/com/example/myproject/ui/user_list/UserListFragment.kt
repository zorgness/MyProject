package com.example.myproject.ui.user_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.databinding.FragmentUserListBinding
import com.example.myproject.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {


    private var _binding: FragmentUserListBinding? = null
    private val  binding get() = _binding!!
    private val viewModel: UserListViewModel by viewModels()
    private lateinit var userListAdapter: UserListAdapter
    private val args: UserListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userListAdapter = UserListAdapter()

        viewModel.fetchActivitiesByProfile(args.profileId, args.isCreator)


        viewModel.messageLiveData.observe(this) {message ->
            requireContext().myToast(message)
        }

        viewModel.listToShowLiveData.observe(this)  { activitiesEvent->
            userListAdapter.submitList(activitiesEvent)
            if(activitiesEvent.isEmpty()) {
                binding.tvEmptyCategory.visibility = View.VISIBLE
            }
        }

        userListAdapter.setOnItemClick { activityEventItem->
            viewModel.setActivityEvent(activityEventItem)
        }

        viewModel.activityEventLiveData.observe(this) {activityEventItem->

            UserListFragmentDirections
                .actionUserListFragmentToDetailsActivityFragment(activityEventItem)
                .let {
                    findNavController().navigate(it)
                }
        }



    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentUserListBinding.inflate(layoutInflater)

        binding.rvActivityEvent.layoutManager = LinearLayoutManager(container?.context)

        binding.rvActivityEvent.adapter = userListAdapter

        binding.progressBar.visibility = View.GONE

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}