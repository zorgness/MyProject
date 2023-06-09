package com.example.myproject.ui.edit_profile

import CODE_200
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myproject.databinding.FragmentEditBinding
import com.example.myproject.extensions.myToast
import com.example.myproject.utils.myPicassoFun
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val  binding get() = _binding!!
    private val viewModel: EditViewModel by viewModels()
    private val args: EditFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.messageLiveData.observe(this) {message ->
            requireContext().myToast(message)
        }

        /**
         * Redirect user to profile if success on updateUser
         */
        viewModel.codeLiveData.observe(this) {event->
            event.getContentIfNotHandled()?.let {code->
                if (code == CODE_200) {
                    EditFragmentDirections
                        .actionEditFragmentToProfileFragment().let{
                            findNavController().navigate(it)
                        }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditBinding.inflate(layoutInflater)

        /**
         * Fill form with user data
         */
        with(args.user) {
            binding.etUserEmail.setText(email)
            binding.etUserUsername.setText(username)
            binding.etImageUrl.setText(imageUrl)
            myPicassoFun(imageUrl, binding.civImagePreview)
            binding.etDescription.setText(description)
            binding.etUserCity.setText(city)
        }


        with(binding) {

            etImageUrl.onFocusChangeListener = View.OnFocusChangeListener { _, isFocus ->
                etImageUrl.text.toString().trim { it <= ' ' }.let {imageUrl->
                    if (!isFocus) {
                        myPicassoFun(imageUrl, civImagePreview)
                    }
                }
            }

            btnSubmitUpdate.setOnClickListener {
                viewModel.update(
                        email = etUserEmail.text.toString(),
                        username = etUserUsername.text.toString(),
                        imageUrl = etImageUrl.text.toString(),
                        city = etUserCity.text.toString(),
                        description = etDescription.text.toString(),
                )
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    EditFragmentDirections
                        .actionEditFragmentToProfileFragment().let{
                            findNavController().navigate(it)
                        }
                }
            })


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}