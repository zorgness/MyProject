package com.example.myproject.ui.login


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
import com.example.myproject.databinding.FragmentLoginBinding
import com.example.myproject.extensions.myToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val myViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       myViewModel.messageLiveData.observe(this) { message ->
            context?.myToast(message)
        }


        myViewModel.statusLiveData.observe(this) { status ->
            if (status == STATUS_REQUEST_SUCCESS) {
                LoginFragmentDirections.actionLoginFragmentToCategoryFragment().let {
                    findNavController().navigate(it)
                }

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = myViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.tvRegisterLoginActivity.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}