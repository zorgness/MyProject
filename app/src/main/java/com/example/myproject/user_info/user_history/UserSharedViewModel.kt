package com.example.myproject.user_info.user_history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myproject.dataclass.profile.ProfileDto
import com.example.myproject.network.ApiService
import com.example.myproject.utils.MySharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserSharedViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context,
    private val sharedPref: MySharedPref
) : ViewModel() {

    private var _userProfileLiveData = MutableLiveData<ProfileDto>()

    private var _messageLiveData = MutableLiveData<String>()

    val userProfile: LiveData<ProfileDto>
        get() = _userProfileLiveData

    val messageLiveData: LiveData<String>
        get() = _messageLiveData


}