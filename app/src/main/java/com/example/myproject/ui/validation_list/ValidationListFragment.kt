package com.example.myproject.ui.validation_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.R
import com.example.myproject.databinding.FragmentActivitiesListBinding
import com.example.myproject.databinding.FragmentValidationListBinding
import com.example.myproject.extensions.myToast
import com.example.myproject.ui.activities_list.ActivitiesByCategoryAdapter
import com.example.myproject.ui.activities_list.ActivitiesListFragmentArgs
import com.example.myproject.ui.activities_list.ActivitiesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValidationListFragment : Fragment() {

    private var _binding: FragmentValidationListBinding? = null
    private val  binding get() = _binding!!
    private val viewModel: ValidationListViewModel by viewModels()
    private lateinit var validationListAdapter: ValidationListAdapter
    private val args: ValidationListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        validationListAdapter = ValidationListAdapter()

        viewModel.fetchAllBookings(args.creatorId)

        viewModel.listToShowLiveData.observe(this) {bookings->
            validationListAdapter.submitList(bookings)
        }

        viewModel.messageLiveData.observe(this) {message ->
            requireContext().myToast(message)
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentValidationListBinding.inflate(layoutInflater)

        binding.rvBookingValidation.layoutManager = LinearLayoutManager(container?.context)

        binding.rvBookingValidation.adapter = validationListAdapter

        binding.progressBar.visibility = View.GONE

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}