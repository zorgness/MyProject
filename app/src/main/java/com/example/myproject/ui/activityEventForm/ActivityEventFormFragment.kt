package com.example.myproject.ui.activityEventForm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myproject.R
import com.example.myproject.databinding.FragmentActivityEventFormBinding
import com.example.myproject.extensions.myToast
import com.example.myproject.utils.MyDatePicker
import com.example.myproject.utils.MyTimePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ActivityEventFormFragment : Fragment() {

    private var _binding: FragmentActivityEventFormBinding? = null
    private val binding get() = _binding!!
    private val myViewModel: ActivityEventFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myViewModel.messageLiveData.observe(this) { message ->
            requireContext().myToast(message)
        }

        /**
         * Navigate to the list of activities by category relative to the new activity inserted
         */
       myViewModel.newItemCategoryId.observe(this) { categoryId ->
            ActivityEventFormFragmentDirections
                .actionActivityEventFormFragmentToActivitiesByCategoryFragment(categoryId).let {
                    findNavController().navigate(it)
                }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
                        inflater,
                        R.layout.fragment_activity_event_form,
                        container,
                        false
                   )
        binding.viewModel = myViewModel
        binding.lifecycleOwner = viewLifecycleOwner



        /**
         * Launch the DATE PICKER DIALOG
         */
        binding.ivCalendar.setOnClickListener {
            MyDatePicker(
                context = requireContext(),
                editText = binding.etSelectedDate
            ).showDatePickerDialog()
        }

        /**
         * Launch the TIME PICKER DIALOG
         */
        binding.ivClock.setOnClickListener {
            MyTimePicker(
                context = requireContext(),
                editText = binding.etMeetingTime
            ).showTimePickerDialog()
        }

        /**
         * Spinner configuration
         */
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerCategoryForm.adapter = adapter
          }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}