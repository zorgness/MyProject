package com.example.myproject.ui.details_activity_event

import CODE_204
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
import com.example.myproject.dto.booking.BookingDto
import com.example.myproject.dto.booking.InfoBookingDto
import com.example.myproject.extensions.toHydraUserId
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
class DetailsActivityViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPref: MySharedPref,
    private val context: Context
): ViewModel() {


    private val _messageLiveData = MutableLiveData<String>()

    private val _codeLiveData = MutableLiveData<Int>()

    private val _currentUserIdLiveData = MutableLiveData<Int>(sharedPref.userId)

    private val _bookingLiveData = MutableLiveData<BookingDto>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val codeLiveData: LiveData<Int>
        get() = _codeLiveData

    val currentUserIdLiveData: LiveData<Int>
        get() = _currentUserIdLiveData

    val bookingLiveData: LiveData<BookingDto>
        get() = _bookingLiveData

    private val headers = HashMap<String, String>()


   fun createBooking(activityHydraId: String) {

      val userId = sharedPref.userId?.toHydraUserId() ?: ""
       headers["Authorization"] = "Bearer ${sharedPref.token}"

      try {
          viewModelScope.launch {
               val responseBooking: Response<BookingDto>? = withContext(Dispatchers.IO) {
                   apiService.createBooking(
                       headers,
                       InfoBookingDto(userId, activityHydraId)
                   )
               }

                val body = responseBooking?.body()

               when {
                    responseBooking == null -> {
                        _messageLiveData.value = context.getString(R.string.server_error)
                    }

                    responseBooking.isSuccessful && (body != null) -> {

                        _codeLiveData.value = responseBooking.code()

                    }

                   responseBooking.code() == ERROR_400 ->
                       _messageLiveData.value = context.getString(R.string.error_parameter)

                   responseBooking.code() == ERROR_403 ->
                       _messageLiveData.value = context.getString(R.string.unauthorized)

                   responseBooking.code() == ERROR_422 ->
                       _messageLiveData.value = context.getString(R.string.unprocessable_entity)
               }
          }



      } catch (e: ConnectException) {
          _messageLiveData.value = context.getString(R.string.no_connection)
      } catch (erno: ErrnoException) {
          _messageLiveData.value = context.getString(R.string.no_connection)
      }
  }

    fun deleteActivity(activityId: Int) {

        headers["Authorization"] = "Bearer ${sharedPref.token}"

        try {
            viewModelScope.launch {
                val responseDelete: Response<Any>? = withContext(Dispatchers.IO) {
                    apiService.deleteActivityEvent(headers, activityId)
                }

                val headers = responseDelete?.headers()

                when {
                    responseDelete == null -> {
                        _messageLiveData.value = context.getString(R.string.server_error)
                    }

                    responseDelete.isSuccessful && (headers != null) -> {

                        if(responseDelete.code() == CODE_204 )
                            _messageLiveData.value = context.getString(R.string.delete_request_success)
                            _codeLiveData.value = responseDelete.code()

                    }

                    responseDelete.code() == ERROR_404 ->
                        _messageLiveData.value = context.getString(R.string.unknow_resource)
                }
            }



        } catch (e: ConnectException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        } catch (erno: ErrnoException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        }

    }

    fun cancelBooking(booKingId: Int) {

        headers["Authorization"] = "Bearer ${sharedPref.token}"

        try {

            viewModelScope.launch {

                val responseCancel: Response<Any>? = withContext(Dispatchers.IO) {
                    apiService.cancelBooking(headers, booKingId)
                }


                when {
                    responseCancel == null -> {
                        _messageLiveData.value = context.getString(R.string.server_error)
                    }

                    responseCancel.isSuccessful -> {

                        if(responseCancel.code() == CODE_204 )
                            _messageLiveData.value = context.getString(R.string.cancel_success)
                            _codeLiveData.value = responseCancel.code()

                    }

                    responseCancel.code() == ERROR_404 ->
                        _messageLiveData.value = context.getString(R.string.unknow_resource)
                }

            }

        } catch (e: ConnectException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        } catch (erno: ErrnoException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        }
    }

}