package com.example.myproject.ui.register

import ERROR_400
import ERROR_403
import ERROR_422
import ERROR_500
import android.content.Context
import android.system.ErrnoException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dto.authentication.RegisterDto
import com.example.myproject.dto.authentication.UserDto
import com.example.myproject.extensions.isLongEnough
import com.example.myproject.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) : ViewModel() {

    private val _messageLiveData = MutableLiveData<String>()
    private val _codeLiveData = MutableLiveData<Int>()

    /**
     * Variables used for Two way binding in xml
     */
    val emailLiveData = MutableLiveData<String>("")
    val usernameLiveData = MutableLiveData<String>("")
    val imageUrlLiveData = MutableLiveData<String>("")
    val cityLiveData = MutableLiveData<String>("")
    val descriptionLiveData = MutableLiveData<String>("")
    val passwordLiveData = MutableLiveData<String>("")
    val confirmLiveData = MutableLiveData<String>("")

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val codeLiveData: LiveData<Int>
        get() = _codeLiveData


    fun register() {
        if (
            emailLiveData.value?.isNotBlank() == true
            &&
            usernameLiveData.value?.isNotBlank() == true
            &&
            imageUrlLiveData.value?.isNotBlank() == true
            &&
            cityLiveData.value?.isNotBlank() == true
            &&
            passwordLiveData.value?.isNotBlank() == true
        ) {
            if(isEmailValid(emailLiveData.value!!)) {
                if(
                    validatePassword(
                        passwordLiveData.value.toString(),
                        confirmLiveData.value.toString()
                    )
                ) {
                    if(passwordLiveData.value.toString().isLongEnough()) {
                        try {
                            viewModelScope.launch {

                                val responseRegister: Response<UserDto>? = withContext(Dispatchers.IO) {
                                    apiService.register(
                                        RegisterDto(
                                            email = emailLiveData.value!!,
                                            password = passwordLiveData.value!!,
                                            username = usernameLiveData.value!!,
                                            city = cityLiveData.value!!,
                                            description = descriptionLiveData.value!!,
                                            imageUrl = imageUrlLiveData.value!!
                                        )
                                    )
                                }

                                val body = responseRegister?.body()

                                when {
                                    responseRegister == null ->
                                        _messageLiveData.value = context.getString(R.string.server_error)

                                    responseRegister.isSuccessful && (body != null) -> {
                                        _messageLiveData.value = context.getString(R.string.user_registreted)
                                        _codeLiveData.value = responseRegister.code()
                                    }

                                    responseRegister.code() == ERROR_400 ->
                                        _messageLiveData.value = context.getString(R.string.error_parameter)

                                    responseRegister.code() == ERROR_403 ->
                                        _messageLiveData.value = context.getString(R.string.unauthorized)

                                    responseRegister.code() == ERROR_422 ->
                                        _messageLiveData.value = context.getString(R.string.unprocessable_entity)

                                    responseRegister.code() == ERROR_500 ->
                                        _messageLiveData.value = context.getString(R.string.username_or_email_already_exist)

                                }

                            }


                        } catch (e: ConnectException) {
                            _messageLiveData.value = context.getString(R.string.no_connection)
                        } catch (erno: ErrnoException) {
                            _messageLiveData.value = context.getString(R.string.no_connection)
                        }

                    }
                    else
                        _messageLiveData.value = context.getString(R.string.password_to_short)

                } else
                    _messageLiveData.value = context.getString(R.string.pwd_and_confirm_not_equals)

            } else
                _messageLiveData.value = context.getString(R.string.invalid_email)

        } else
            _messageLiveData.value = context.getString(R.string.obligatory_fields)

    }

}

private fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
private fun validatePassword(password: String, confirm: String): Boolean {
    return password == confirm
}