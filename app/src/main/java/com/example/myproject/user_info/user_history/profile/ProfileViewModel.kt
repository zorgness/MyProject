package com.example.myproject.user_info.user_history.profile

import android.content.Context
import android.system.ErrnoException
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
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context,
    private val sharedPref: MySharedPref
) : ViewModel() {

    private var _userProfileLiveData = MutableLiveData<ProfileDto>()

    private var _messageLiveData = MutableLiveData<String>()

    private var _profileIdLiveData = MutableLiveData<Int>(0)

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>(false)

    val userProfile: LiveData<ProfileDto>
        get() = _userProfileLiveData

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val profileIdLiveData: LiveData<Int>
        get() = _profileIdLiveData

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData


    fun setProfileId(profileId: Int) {
        _profileIdLiveData.value = profileId
    }

    fun fetchUserProfile() {

        val profileId = if(profileIdLiveData.value!! > 0) profileIdLiveData.value else sharedPref.getUserId()

        try {
            _progressBarVisibilityLiveData.value = true
            viewModelScope.launch {
                val responseUserProfile: Response<GetProfileDto>? = withContext(Dispatchers.IO) {
                    apiService.fetchUserProfile(profileId!!)
                }
                val body = responseUserProfile?.body()

                when {
                    responseUserProfile == null -> {
                        _messageLiveData.value = context.getString(R.string.server_error)
                    }
                    responseUserProfile.isSuccessful && (body != null) -> {
                        _userProfileLiveData.value = body.profile
                        _progressBarVisibilityLiveData.value = false
                    }

                    responseUserProfile.code() == 403 ->
                        _messageLiveData.value = context.getString(R.string.unauthorized)

                }

                _progressBarVisibilityLiveData.value = false
            }

        }  catch (e: ConnectException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        }
        catch (erno: ErrnoException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        }
    }
}