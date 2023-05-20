package com.example.myproject.user_info.user_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myproject.databinding.FragmentUserHistoryBinding
import com.example.myproject.user_info.user_history.user_history_fragments.MyPagerAdapter
import com.example.myproject.extensions.myToast
import com.example.myproject.ui.profile.ProfileViewModel
import com.example.myproject.utils.myPicassoFun
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserHistoryFragment : Fragment() {

    private var _binding: FragmentUserHistoryBinding? = null
    private val binding get() = _binding!!
    private var pagerAdapter: MyPagerAdapter? = null
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.fetchUserProfile()

        viewModel.messageLiveData.observe(this) {message ->
            requireContext().myToast(message)
        }

        viewModel.profileLiveData.observe(this) { profile->
                binding.tvUsername.text = profile.username
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserHistoryBinding.inflate(layoutInflater)
        pagerAdapter = MyPagerAdapter(parentFragmentManager)

        binding.viewPager.adapter = pagerAdapter

        binding.tabsUserHistory.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}