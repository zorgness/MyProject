package com.example.myproject.user_info.user_history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dataclass.GetProfileDto
import com.example.myproject.dataclass.ProfileDto
import com.example.myproject.network.ApiService
import com.example.myproject.utils.MySharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
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

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            val responseUserProfile: Response<GetProfileDto>? = withContext(Dispatchers.IO) {
                apiService.getUserProfile(sharedPref.getUserId())
            }
            val body = responseUserProfile?.body()


            when {
                responseUserProfile == null -> {
                    _messageLiveData.value = context.getString(R.string.server_error)
                }
                responseUserProfile.isSuccessful && (body != null) -> {
                    _userProfileLiveData.value = body.profile
                }

                responseUserProfile.code() == 403 ->
                    _messageLiveData.value = context.getString(R.string.unauthorized)

            }
        }


    }
}