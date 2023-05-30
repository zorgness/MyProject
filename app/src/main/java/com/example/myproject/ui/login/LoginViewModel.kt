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
import com.example.myproject.dto.authentication.LoginDto
import com.example.myproject.dto.authentication.SessionDto
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


    private val _messageLiveData = MutableLiveData<String>()

    private val _statusLiveData = MutableLiveData<Int>()


    /**
     * Variables used for Two way binding in xml
     */
    val emailLiveData = MutableLiveData<String>("")
    val passwordLiveData = MutableLiveData<String>("")

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val statusLiveData: LiveData<Int>
        get() = _statusLiveData


    fun login(){

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

                        }

                        responseLogin.isSuccessful && (body != null) -> {

                            if (body.status == STATUS_REQUEST_SUCCESS) {

                                sharedPref.apply {
                                    token = body.token
                                    userId = body.id

                                }

                                _messageLiveData.value = context.getString(R.string.welcome)
                                _statusLiveData.value = body.status

                            }
                        }
                        responseLogin.code() == ERROR_401 ->
                            _messageLiveData.value = context.getString(R.string.wrong_credential)


                        responseLogin.code() == ERROR_403 ->
                            _messageLiveData.value = context.getString(R.string.unauthorized)

                    }
                }
            }
            catch (e: ConnectException) {
                _messageLiveData.value = context.getString(R.string.no_connection)

            }
            catch (erno: ErrnoException) {
                _messageLiveData.value = context.getString(R.string.no_connection)

            }
        }
        else {
            _messageLiveData.value = context.getString(R.string.empty_fields)

        }
    }

}






