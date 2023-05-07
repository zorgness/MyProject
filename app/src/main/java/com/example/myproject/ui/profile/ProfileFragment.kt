package com.example.myproject.ui.profile

import SHAREDPREF_NAME
import SHAREDPREF_SESSION_USER_ID
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myproject.databinding.FragmentProfileBinding
import com.example.myproject.dataclass.UserDto
import myToast


class ProfileFragment : Fragment() {


    private val myViewModel: ProfileViewModel by viewModels()
    var userDto: UserDto? = null
    var userId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentProfileBinding.inflate(layoutInflater)

        myViewModel.errorMessageLiveData.observe(viewLifecycleOwner) {errorMessage ->
            activity?.myToast(errorMessage)
        }

        with(activity?.applicationContext?.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)) {
            userId = this?.getInt(SHAREDPREF_SESSION_USER_ID, 0)
        }
        userId?.let { myViewModel.getUserProfile(it) }
        myViewModel.userProfile.observe(viewLifecycleOwner, Observer{profile->

            binding.tvUsername.text = profile?.username


        })

        return binding.root
    }

}