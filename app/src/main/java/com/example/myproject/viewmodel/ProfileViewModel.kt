package com.example.myproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.dataclass.UserDto
import com.example.myproject.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private var _userProfileLiveData = MutableLiveData<UserDto?>()

    private var _errorMessageLiveData = MutableLiveData<String>()

    val userProfile: LiveData<UserDto?>
        get() = _userProfileLiveData

    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData

    fun getUserProfile(userId: Int) {
        viewModelScope.launch {
            val responseUserProfile: Response<UserDto>? = withContext(Dispatchers.IO) {
                ApiService.getApi().getUserProfile(userId)
            }
            val body = responseUserProfile?.body()


            when {
                responseUserProfile == null -> {
                    _errorMessageLiveData.value = "error server"
                }
                responseUserProfile.isSuccessful && (body != null) -> {
                   _userProfileLiveData.value = UserDto(
                        body.context,
                        body.idHydra,
                        body.type,
                        body.email,
                        body.username,
                        body.city,
                        body.description
                    )

                }

                responseUserProfile.code() == 403 ->
                    _errorMessageLiveData.value = "erreur d'authorisation"

            }
        }


    }
}