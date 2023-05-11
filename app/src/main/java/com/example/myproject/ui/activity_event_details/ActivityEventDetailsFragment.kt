package com.example.myproject.ui.activity_event_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myproject.R
import com.example.myproject.databinding.FragmentActivityEventDetailsBinding
import com.example.myproject.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityEventDetailsFragment : Fragment() {

    private var _binding: FragmentActivityEventDetailsBinding? = null
    private val binding get() = _binding!!
    private val myViewModel: ActivityEventDetailsViewModel by viewModels()
    private val args: ActivityEventDetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myViewModel.messageLiveData.observe(this) { message ->
            requireContext().myToast(message)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityEventDetailsBinding.inflate(inflater, container, false)

        with(args.activityEvent) {
            binding.tvTitle.text = title
            binding.tvDate.text = startAt
            binding.tvLocation.text = location
            binding.tvDescription.text = description

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}