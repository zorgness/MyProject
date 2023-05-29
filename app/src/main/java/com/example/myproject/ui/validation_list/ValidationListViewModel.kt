package com.example.myproject.ui.validation_list

import ERROR_401
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dto.activities.ActivityEventDto
import com.example.myproject.dto.booking.BookingDto
import com.example.myproject.dto.booking.BookingFullDto
import com.example.myproject.network.ApiService
import com.example.myproject.utils.MySharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidationListViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPref: MySharedPref,
    private val context: Context
): ViewModel() {

    // LIST TO BE DISPLAY
    private val _listToShowLiveData = MutableLiveData<List<BookingFullDto>>()

    val listToShowLiveData: LiveData<List<BookingFullDto>>
        get() = _listToShowLiveData

    private val _messageLiveData = MutableLiveData<String>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val headers = HashMap<String, String>()


    fun fetchAllBookings(creatorId: Int) {

        headers["Authorization"] = "Bearer ${sharedPref.getToken() ?: ""}"

        try {
            viewModelScope.launch {
                val responseBookings = apiService.fetchBookingsAll(headers)
                val body = responseBookings?.body()
                when {
                    responseBookings == null ->
                        _messageLiveData.value = context.getString(R.string.server_error)

                    responseBookings.isSuccessful && (body != null) ->
                       _listToShowLiveData.value = body.bookings.filter { booking->
                            booking.activity.creator.id == creatorId
                        }

                    responseBookings.code() == ERROR_401 ->
                        _messageLiveData.value = context.getString(R.string.wrong_credential)
                }


            }


        } catch (e: Exception) {

        }
    }


}