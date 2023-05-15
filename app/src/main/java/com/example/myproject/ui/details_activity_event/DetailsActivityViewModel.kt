package com.example.myproject.ui.details_activity_event

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
import com.example.myproject.dataclass.booking.BookingDto
import com.example.myproject.dataclass.booking.InfoBookingDto
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

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val _codeLiveData = MutableLiveData<Int>()

    val codeLiveData: LiveData<Int>
        get() = _codeLiveData

   fun createBooking(activityHydraId: String) {

      val userId = sharedPref.getUserId().toHydraUserId()

      try {
          viewModelScope.launch {
               val responseBooking: Response<BookingDto>? = withContext(Dispatchers.IO) {
                   apiService.addBooking(InfoBookingDto(userId, activityHydraId))
               }

                val body = responseBooking?.body()

               when {
                    responseBooking == null -> {
                        _messageLiveData.value = context.getString(R.string.server_error)
                    }

                    responseBooking.isSuccessful && (body != null) -> {

                        /*if(responseBooking.code() == CODE_201 )
                            _messageLiveData.value = context.getString(R.string.booking_request_success)*/
                        _codeLiveData.value = responseBooking.code()

                    }

                   responseBooking.code() == ERROR_400 ->
                       _messageLiveData.value = context.getString(R.string.parameter_problem)

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

}