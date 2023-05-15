package com.example.myproject.ui.edit

import ERROR_400
import ERROR_403
import ERROR_404
import ERROR_422
import android.content.Context
import android.system.ErrnoException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dataclass.authentication.UpdateDto
import com.example.myproject.dataclass.authentication.UserDto
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
class EditViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPref: MySharedPref,
    private val context: Context
) : ViewModel() {

    private val _messageLiveData = MutableLiveData<String>()
    private val _codeLiveData = MutableLiveData<Int>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val codeLiveData: LiveData<Int>
        get() = _codeLiveData



    fun update(email: String, username: String, imageUrl: String, city: String, description: String) {

        if (
            email.isNotBlank()
            &&
            username.isNotBlank()
            &&
            imageUrl.isNotBlank()
            &&
            city.isNotBlank()

        ) {
            if (isEmailValid(email)) {

                try {
                    viewModelScope.launch {

                        val responseUpdate: Response<UserDto>? = withContext(Dispatchers.IO) {
                            apiService.update(
                                sharedPref.getUserId(),
                                UpdateDto(
                                    email,
                                    username,
                                    city ,
                                    description,
                                    imageUrl
                                )
                            )
                        }

                        val body = responseUpdate?.body()

                        when {
                            responseUpdate == null ->
                                _messageLiveData.value = context.getString(R.string.server_error)


                            responseUpdate.isSuccessful && (body != null) -> {
                                _messageLiveData.value =
                                    context.getString(R.string.user_updated)
                                _codeLiveData.value = responseUpdate.code()
                            }

                            responseUpdate.code() == ERROR_400 ->
                                _messageLiveData.value =
                                    context.getString(R.string.parameter_problem)

                            responseUpdate.code() == ERROR_403 ->
                                _messageLiveData.value = context.getString(R.string.unauthorized)

                            responseUpdate.code() == ERROR_404 ->
                                _messageLiveData.value = context.getString(R.string.resource_introuvable)

                            responseUpdate.code() == ERROR_422 ->
                                _messageLiveData.value =
                                    context.getString(R.string.unprocessable_entity)

                        }

                    }


                } catch (e: ConnectException) {
                    _messageLiveData.value = context.getString(R.string.no_connection)
                } catch (erno: ErrnoException) {
                    _messageLiveData.value = context.getString(R.string.no_connection)
                }

            }
            else
                _messageLiveData.value = context.getString(R.string.invalid_email)
        } else
            _messageLiveData.value = context.getString(R.string.obligatory_fields)
    }

}


private fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

