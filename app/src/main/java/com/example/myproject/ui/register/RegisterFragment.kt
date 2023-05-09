package com.example.myproject.ui.register

import CODE_201
import STATUS_REQUEST_SUCCESS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myproject.R
import com.example.myproject.databinding.FragmentRegisterBinding
import com.example.myproject.dataclass.RegisterInfo
import com.example.myproject.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val  binding get() = _binding!!
    private val myViewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myViewModel.messageLiveData.observe(this) {message ->
            requireContext().myToast(message)
        }

        myViewModel.codeLiveData.observe(this) {code ->
            if (code == CODE_201) {
                findNavController().popBackStack()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register, container,false)
        binding.viewModel = myViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}