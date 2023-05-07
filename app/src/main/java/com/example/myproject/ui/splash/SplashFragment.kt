package com.example.myproject.ui.splash

import SHAREDPREF_NAME
import SHAREDPREF_SESSION_TOKEN
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myproject.R

class SplashFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val navController = findNavController()
        Handler().postDelayed({

            activity?.applicationContext?.getSharedPreferences(
                SHAREDPREF_NAME,
                Context.MODE_PRIVATE
            )
                ?.apply {

                    (
                            getString(SHAREDPREF_SESSION_TOKEN, null)
                                ?.run {
                                    navController.navigate(R.id.action_splashFragment_to_categoryFragment)
                                }
                                ?: navController.navigate(R.id.action_splashFragment_to_loginFragment)
                            )

                }

        }, 2000)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


}