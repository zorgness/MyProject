package com.example.myproject.ui.edit

import CODE_200
import CODE_201
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myproject.R
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
         * Redirect user to profile if success on update
         */
        viewModel.codeLiveData.observe(this) {code ->
            if (code == CODE_200) {
                findNavController().navigateUp()
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
                viewModel?.update(
                        email = etUserEmail.text.toString(),
                        username = etUserUsername.text.toString(),
                        imageUrl = etImageUrl.text.toString(),
                        city = etUserCity.text.toString(),
                        description = etDescription.text.toString(),
                )
            }

        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}