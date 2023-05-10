package com.example.myproject.ui.register

import ERROR_400
import ERROR_403
import ERROR_422
import android.content.Context
import android.system.ErrnoException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dataclass.RegisterInfo
import com.example.myproject.dataclass.UserDto
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
    val cityLiveData = MutableLiveData<String>("")
    val passwordLiveData = MutableLiveData<String>("")
    val confirmLiveData = MutableLiveData<String>("")

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val codeLiveData: LiveData<Int>
        get() = _codeLiveData


    fun register() {
        if
        (
            emailLiveData.value?.isNotBlank() == true
            &&
            usernameLiveData.value?.isNotBlank() == true
            &&
            cityLiveData.value?.isNotBlank() == true
            &&
            passwordLiveData.value?.isNotBlank() == true
        ) {
            if(isEmailValid(emailLiveData.value!!)) {
                if
                        (validatePassword
                        (
                        passwordLiveData.value.toString(),
                        confirmLiveData.value.toString()
                    )
                ) {

                    try {
                        viewModelScope.launch {

                            val responseRegister: Response<UserDto>? = withContext(Dispatchers.IO) {
                                apiService.register(
                                    RegisterInfo(
                                        emailLiveData.value!!,
                                        passwordLiveData.value!!,
                                        usernameLiveData.value!!,
                                        cityLiveData.value!!,
                                        ""
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
                                    _messageLiveData.value = context.getString(R.string.unauthorized)

                                responseRegister.code() == ERROR_403 ->
                                    _messageLiveData.value = context.getString(R.string.unauthorized)

                                responseRegister.code() == ERROR_422 ->
                                    _messageLiveData.value = context.getString(R.string.unprocessable_entity)

                            }

                        }


                    } catch (e: ConnectException) {
                        _messageLiveData.value = context.getString(R.string.no_connection)
                    } catch (erno: ErrnoException) {
                        _messageLiveData.value = context.getString(R.string.no_connection)
                    }

                } else
                    _messageLiveData.value = context.getString(R.string.pwd_and_confirm_not_equals)

            } else
                _messageLiveData.value = context.getString(R.string.invalid_email)

        } else
            _messageLiveData.value = context.getString(R.string.empty_fields)

    }

}

private fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
private fun validatePassword(password: String, confirm: String): Boolean {
    return password == confirm
}