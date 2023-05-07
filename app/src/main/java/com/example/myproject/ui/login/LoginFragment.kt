package com.example.myproject.ui.login

import SHAREDPREF_NAME
import SHAREDPREF_SESSION_TOKEN
import SHAREDPREF_SESSION_USERNAME
import SHAREDPREF_SESSION_USER_ID
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myproject.R
import com.example.myproject.databinding.FragmentLoginBinding
import com.example.myproject.dataclass.LoginInfo
import login


class LoginFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navController = findNavController()
        val binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.tvRegisterLoginActivity.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnSubmitLogin.setOnClickListener {
            val email = binding.etUserEmail.text.toString()
            val password = binding.etUserPassword.text.toString()


            //add view model who get email and password

            if (email.isNotBlank() && password.isNotBlank()) {

                login(LoginInfo(email, password), loginCallback = {
                    with(it) {
                        activity?.applicationContext?.getSharedPreferences(
                            SHAREDPREF_NAME,
                            android.content.Context.MODE_PRIVATE
                        )?.edit()
                            ?.putString(SHAREDPREF_SESSION_TOKEN, this.token)
                            ?.putInt(SHAREDPREF_SESSION_USER_ID, this.id)
                            ?.putString(SHAREDPREF_SESSION_USERNAME, this.username)
                            ?.apply()
                        navController.navigate(R.id.action_loginFragment_to_categoryFragment)
                    }
                }, errorCallback = { error ->
                    //myToast(error.toString())
                })

            } else {
                //myToast("fields can't be empty")
            }
        }

        return binding.root
    }



}