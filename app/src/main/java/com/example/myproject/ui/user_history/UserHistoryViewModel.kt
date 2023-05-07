package com.example.myproject.ui.user_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.dataclass.GetProfileDto
import com.example.myproject.dataclass.ProfileDto
import com.example.myproject.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserHistoryViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private var _userProfileLiveData = MutableLiveData<ProfileDto>()

    private var _errorMessageLiveData = MutableLiveData<String>()

    val userProfile: LiveData<ProfileDto>
        get() = _userProfileLiveData

    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData

    fun getUserProfile(userId: Int) {
        viewModelScope.launch {
            val responseUserProfile: Response<GetProfileDto>? = withContext(Dispatchers.IO) {
                apiService.getUserProfile(userId)
            }
            val body = responseUserProfile?.body()


            when {
                responseUserProfile == null -> {
                    _errorMessageLiveData.value = "error server"
                }
                responseUserProfile.isSuccessful && (body != null) -> {
                    //ADD MESSAGE
                    _userProfileLiveData.value = body.profile
                   /*_userProfileLiveData.value = UserDto(
                        body.context,
                        body.idHydra,
                        body.type,
                        body.email,
                        body.username,
                        body.city,
                        body.description
                    )*/

                }

                responseUserProfile.code() == 403 ->
                    _errorMessageLiveData.value = "erreur d'authorisation"

            }
        }


    }
}