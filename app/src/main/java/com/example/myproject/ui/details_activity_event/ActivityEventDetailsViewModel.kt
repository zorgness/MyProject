package com.example.myproject.ui.details_activity_event

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myproject.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityEventDetailsViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
): ViewModel() {



    private val _messageLiveData = MutableLiveData<String>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData


  /*  fun fetchActivityEventDetails(activityEventId: Int) {

        try {
            viewModelScope.launch {
                val responseActivityEventDetails: Response<GetActivityEventDto>? = withContext(Dispatchers.IO) {
                    apiService.getActivityEventById(activityEventId)
                }
                val body = responseActivityEventDetails?.body()

                when {
                    responseActivityEventDetails == null -> {
                        _messageLiveData.value = context.getString(R.string.server_error)
                    }

                    responseActivityEventDetails.isSuccessful && (body != null) -> {
                        if(body.status == STATUS_REQUEST_SUCCESS)
                            //_activityEventDetails.value = body.activityEvent

                    }

                    responseActivityEventDetails.code() == ERROR_403 ->
                        _messageLiveData.value = context.getString(R.string.unauthorized)
                }

            }

        }  catch (e: ConnectException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        }
        catch (erno: ErrnoException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        }
    }*/

}