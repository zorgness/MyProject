package com.example.myproject.ui.details_activity_event

import CODE_201
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myproject.R
import com.example.myproject.databinding.FragmentDetailsActivityBinding
import com.example.myproject.extensions.myToast
import com.example.myproject.extensions.toHydraActivitiesId
import com.example.myproject.utils.dateFormatter
import com.example.myproject.utils.myPicassoFun
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivityFragment : Fragment() {

    private var _binding: FragmentDetailsActivityBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsActivityViewModel by viewModels()
    private val args: DetailsActivityFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.messageLiveData.observe(this) { message ->
            requireContext().myToast(message)
        }

        viewModel.codeLiveData.observe(this) { code ->
            if(code == CODE_201) {
                DetailsActivityFragmentDirections
                    .actionDetailsActivityFragmentToProfileFragment().let {
                        findNavController().navigate(it)
                    }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsActivityBinding.inflate(inflater, container, false)

        with(args.activityEvent) {
            binding.tvTitle.text = title
            binding.tvDate.text = dateFormatter(startAt)
            binding.tvLocation.text = location
            binding.tvDescription.text = description
            binding.tvUsername.text = creator.username
            binding.tvCategory.text = category.name
            binding.tvMaxPeople.text = requireContext().getString(R.string.nombre_de_personnes_1d).format(maxOfPeople)

            myPicassoFun(creator.imageUrl, binding.civUserImage)

           binding.civUserImage.setOnClickListener {
                DetailsActivityFragmentDirections
                    .actionDetailsActivityFragmentToProfileFragment(creator.id).let {
                        findNavController().navigate(it)
                    }
            }

            binding.btnJoin.setOnClickListener() {
                viewModel.createBooking(args.activityEvent.id.toHydraActivitiesId())
            }

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}