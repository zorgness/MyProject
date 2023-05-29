package com.example.myproject.ui.new_edit_activities_form

import CODE_200
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myproject.R
import com.example.myproject.databinding.FragmentNewEditActivityFormBinding
import com.example.myproject.extensions.myToast
import com.example.myproject.sharedviewmodel.SharedViewModel
import com.example.myproject.utils.MyDatePicker
import com.example.myproject.utils.MyTimePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewEditFormFragment : Fragment() {

    private var _binding: FragmentNewEditActivityFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewEditFormViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: NewEditFormFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.messageLiveData.observe(this) { message ->
            requireContext().myToast(message)
        }

        args.activityEventDto.let {
            viewModel.setActivityToUpdate(it)

        }


    /**
     * Navigate to the list of activities by category relative to the new activity inserted
     */
           viewModel.newItemCategoryId.observe(this) { categoryId ->
                NewEditFormFragmentDirections
                    .actionActivityEventFormFragmentToActivitiesByCategoryFragment(categoryId)
                        .let {
                            findNavController().navigate(it)
                        }
           }

            viewModel.codeLiveData.observe(this) { code ->
                if(code == CODE_200) {
                    sharedViewModel
                        .refreshListByCategoryId(
                            args.activityEventDto?.category?.id ?: 0
                        )
                    NewEditFormFragmentDirections
                        .actionActivityEventFormFragmentToActivitiesByCategoryFragment(
                            args.activityEventDto?.category?.id ?: 0
                        ).let {
                                    findNavController().navigate(it)
                            }
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
                        R.layout.fragment_new_edit_activity_form,
                        container,
                        false
                   )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if (args.activityEventDto != null) {
            binding.tvNewEdit.text = requireContext().getString(R.string.editer)
            binding.btnSaveActivity.visibility = View.GONE
            binding.btnEditActivity.visibility = View.VISIBLE
        }

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
            android.R.layout.preference_category
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