package com.example.myproject.ui.activityEventForm

import CODE_201
import android.app.DatePickerDialog
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

        myViewModel.newItemCategoryId.observe(this) { categoryId->
            ActivityEventFormFragmentDirections.actionActivityEventFormFragmentToActivityByCategoryFragment(categoryId).let {
                findNavController().navigate(it)
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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

        var year = 0
        var month = 0
        var day = 0
        var fullDate = ""

        //SPINNER CONFIG
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategoryForm.adapter = adapter
        }



       /*binding.btnSaveArticle.setOnClickListener {
            myViewModel.getCategoryId(binding.spinnerCategoryForm.selectedItemId.toInt() + 1)
        }*/



        binding.etSelectedDate.setOnClickListener {

            Calendar.getInstance().apply {
                year = get(Calendar.YEAR)
                month = get(Calendar.MONTH)
                day = get(Calendar.DAY_OF_MONTH)
            }

            DatePickerDialog(

                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->

                    fullDate = "$dayOfMonth-${monthOfYear + 1}-$year"
                    binding.etSelectedDate.setText(fullDate)
                },

                year,
                month,
                day
            ).show()

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}