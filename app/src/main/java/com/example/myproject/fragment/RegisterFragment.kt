package com.example.myproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myproject.R
import com.example.myproject.databinding.FragmentRegisterBinding
import com.example.myproject.dataclass.RegisterInfo
import register


class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navController = findNavController()
        val binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.btnSubmitRegister.setOnClickListener {
            val email =  binding.etUserEmail.text.toString()
            val username = binding.etUserUsername.text.toString()
            val city = binding.etUserCity.text.toString()
            val password = binding.etUserPassword.text.toString()
            val confirm = binding.etUserPasswordConfirm.text.toString()

            if (email.isNotBlank() && username.isNotBlank() && city.isNotBlank() && password.isNotBlank()) {
                if (validatePassword(password, confirm)) {
                    register(RegisterInfo(email, password, username, city, ""), loginCallback = {
                        with(it) {

                            //NEED A SOLUTION TO LOG AFTER REGISTRATION
                            navController.navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                    }, errorCallback = { error ->

                        //myToast(error.toString())
                    })

                } else {
                   // myToast("password and confirm are not equals")
                }
            } else {
                //myToast("fields can't be blank")
            }
        }
        return binding.root
    }

    private fun validatePassword(password: String, confirm: String): Boolean {
        return password == confirm
    }


}