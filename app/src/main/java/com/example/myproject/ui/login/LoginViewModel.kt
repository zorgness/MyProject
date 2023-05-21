package com.example.myproject.ui.login

import ERROR_401
import ERROR_403
import STATUS_REQUEST_SUCCESS
import android.content.Context
import android.system.ErrnoException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dataclass.authentication.LoginDto
import com.example.myproject.dataclass.authentication.SessionDto
import com.example.myproject.event.Event
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
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPref: MySharedPref,
    private val context: Context

) : ViewModel() {

    enum class LoginState {
        AUTHENTICATED,
        WRONG_CREDENTIAL,
        ERROR_SERVER,
        EMPTY_FIELDS,
        ERROR_AUTHORIZATION,
        NO_CONNECTION
    }

    private val _messageLiveData = MutableLiveData<String>()

    private val _statusLiveData = MutableLiveData<Int>()

    private val _errorLiveData = MutableLiveData<LoginState?>()

    /**
     * Variables used for Two way binding in xml
     */
    val emailLiveData = MutableLiveData<String>("")
    val passwordLiveData = MutableLiveData<String>("")

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val statusLiveData: LiveData<Int>
        get() = _statusLiveData

    private val errorLiveData: LiveData<LoginState?>
        get() = _errorLiveData

    fun login(){

        _errorLiveData.value = null

        if (
            emailLiveData.value?.isNotBlank() == true
            &&
            passwordLiveData.value?.isNotBlank() == true
        ) {

            try {
                viewModelScope.launch {

                    val responseLogin: Response<SessionDto>? = withContext(Dispatchers.IO) {
                        apiService.login(LoginDto(emailLiveData.value!!, passwordLiveData.value!!))
                    }

                    val body = responseLogin?.body()

                    when {
                        responseLogin == null -> {
                             _messageLiveData.value = context.getString(R.string.server_error)
                             //_errorLiveData.value = LoginState.ERROR_SERVER
                        }

                        responseLogin.isSuccessful && (body != null) -> {

                            if (body.status == STATUS_REQUEST_SUCCESS) {

                                sharedPref.apply {
                                    saveToken(body.token)
                                    saveUserId(body.id)
                                    saveUsername(body.username)
                                    saveImageUrl(body.imageUrl)
                                }

                                _messageLiveData.value = context.getString(R.string.welcome)
                                //_errorLiveData.value = LoginState.AUTHENTICATED
                                _statusLiveData.value = body.status

                            }
                        }
                        responseLogin.code() == ERROR_401 ->
                            _messageLiveData.value = context.getString(R.string.wrong_credential)
                            //_errorLiveData.value = LoginState.WRONG_CREDENTIAL

                        responseLogin.code() == ERROR_403 ->
                            _messageLiveData.value = context.getString(R.string.unauthorized)
                            //_errorLiveData.value = LoginState.ERROR_AUTHORIZATION
                    }
                }
            }
            catch (e: ConnectException) {
                _messageLiveData.value = context.getString(R.string.no_connection)
                //_errorLiveData.value = LoginState.NO_CONNECTION
            }
            catch (erno: ErrnoException) {
                _messageLiveData.value = context.getString(R.string.no_connection)
                //_errorLiveData.value = LoginState.NO_CONNECTION
            }
        }
        else {
            _messageLiveData.value = context.getString(R.string.empty_fields)
            //_errorLiveData.value = LoginState.EMPTY_FIELDS
        }


      /*  errorLiveData.value?.let {
            _messageLiveData.value = context.getString(showErrorMessage(it))
        }*/

    }


    private fun showErrorMessage(loginState: LoginState) =
            when(loginState) {
                    LoginState.AUTHENTICATED -> R.string.welcome
                    LoginState.WRONG_CREDENTIAL -> R.string.wrong_credential
                    LoginState.ERROR_SERVER -> R.string.server_error
                    LoginState.EMPTY_FIELDS -> R.string.empty_fields
                    LoginState.ERROR_AUTHORIZATION -> R.string.unauthorized
                    LoginState.NO_CONNECTION -> R.string.no_connection
            }


}






