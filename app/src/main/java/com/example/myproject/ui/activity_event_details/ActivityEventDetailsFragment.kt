package com.example.myproject.ui.activity_event_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myproject.R
import com.example.myproject.databinding.FragmentActivityEventDetailsBinding
import com.example.myproject.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityEventDetailsFragment : Fragment() {

    private var _binding: FragmentActivityEventDetailsBinding? = null
    private val binding get() = _binding!!
    private val myViewModel: ActivityEventDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myViewModel.messageLiveData.observe(this) { message ->
            context?.myToast(message)
        }

        myViewModel.activityEventDetails.observe(this) { activityEvent ->
            with(binding) {
                (activityEvent)?.let {
                    tvTitle.text = it.title
                    tvDate.text = it.startAt
                    tvLocation.text = it.location
                    tvDescription.text = it.description
                }

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}