package com.example.myproject.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myproject.R
import com.example.myproject.databinding.FragmentProfileBinding
import com.example.myproject.dataclass.authentication.UserDto
import com.example.myproject.extensions.myToast
import com.example.myproject.user_info.user_history.UserSharedViewModel
import com.example.myproject.utils.myPicassoFun
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val args: ProfileFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.messageLiveData.observe(this) {message ->
            requireContext().myToast(message)
        }

        viewModel.isCurrentUserLiveData.observe(this) {isCurrentUser ->
            binding.ivEdit.visibility = if(isCurrentUser) View.VISIBLE else View.GONE
        }

        viewModel.setProfileId(args.profileId)
        viewModel.fetchUserProfile()


        viewModel.profileLiveData.observe(this) { profile->
            with(binding) {

                myPicassoFun(profile.imageUrl, civProfileImage)
                tvUsername.text = profile.username
                tvProfileBio.text = profile.description
                tvCity.text = profile.city
               /* profile.bookings.forEach { bookings ->
                    tvBooking.text = bookings.activity.title + "\n"
                }*/

                binding.ivEdit.setOnClickListener {
                    with(profile) {
                        ProfileFragmentDirections
                            .actionProfileFragmentToEditFragment(
                                UserDto(email, username,  city, description, imageUrl)
                            ).let {
                                findNavController().navigate(it)
                            }
                    }
                }
            }
        }

        viewModel.progressBarVisibilityLiveData.observe(this) {
            binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}