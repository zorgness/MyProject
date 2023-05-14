package com.example.myproject.ui.main

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.myproject.R
import com.example.myproject.databinding.FragmentLoginBinding
import com.example.myproject.databinding.FragmentMainBinding
import com.example.myproject.ui.category.CategoryFragment
import com.example.myproject.ui.login.LoginViewModel
import com.example.myproject.user_info.user_history.profile.ProfileFragment
import com.example.myproject.utils.myPicassoFun
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.concurrent.fixedRateTimer

@AndroidEntryPoint
class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val myViewModel: MainFragmentViewModel by viewModels()
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*myViewModel.fetchUserImageUrl()

        myViewModel.imageUrlLiveData.observe(this)  { url ->
            val img = ImageView(context)
            myPicassoFun(url, img)
        }*/


        myViewModel.isLoggedOutLiveData.observe(this) { isLoggedOut ->
             if (isLoggedOut) {
                 MainFragmentDirections.actionMainFragmentToLoginFragment().let{
                     findNavController().navigate(it)
                 }
             }
        }

     /*  myViewModel.isGoToHome.observe(this) { isGoToHome ->
            if (isGoToHome) {
               *//* childFragmentManager.commit {
                    replace(R.id.nav_host, CategoryFragment())
                }*//*
                navController.navigateUp()

            }
        }*/

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = myViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}