package com.example.myproject.ui.activityEventForm

import ERROR_403
import android.content.Context
import android.content.SharedPreferences
import android.system.ErrnoException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dataclass.ActivityEventDto
import com.example.myproject.dataclass.ActivityEventInfo
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
class ActivityEventFormViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context,
    private val sharedPref: MySharedPref,
) : ViewModel() {


    private val _messageLiveData = MutableLiveData<String>()
    private val _codeLiveData = MutableLiveData<Int>()
    private val _categoryIdLiveData = MutableLiveData<Int>(1)

    var titleLd = MutableLiveData<String>("")
    var descriptionLd = MutableLiveData<String>("")
    var locationLd = MutableLiveData<String>("")
    var meetingPointLd = MutableLiveData<String>("")
    var maxOfPeopleLd = MutableLiveData<String>("")
    var startAtLd = MutableLiveData<String>("")

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val codeLiveData: LiveData<Int>
        get() = _codeLiveData

    val categoryIdLiveData: LiveData<Int>
        get() = _categoryIdLiveData


    fun getCategoryId(categoryId: Int) {
        _categoryIdLiveData.value = categoryId
    }

    fun createActivityEvent() {
        println("${titleLd.value} " +
                "${descriptionLd.value}" +
                " ${locationLd.value} " +
                "${maxOfPeopleLd.value}" +
                "${meetingPointLd.value}" +
                " ${startAtLd.value}" +
                " ${categoryIdLiveData.value}" )


        

        if
        (
            titleLd.value?.isNotBlank() == true
            &&
            descriptionLd.value?.isNotBlank() == true
            &&
            locationLd.value?.isNotBlank() == true
            &&
            meetingPointLd.value?.isNotBlank() == true
            &&
            startAtLd.value?.isNotBlank() == true

        ) {

            try {

                viewModelScope.launch {
                    val responseNewActivityEvent: Response<ActivityEventDto>? = withContext(Dispatchers.IO) {
                        apiService.createActivityEvent(
                            ActivityEventInfo(
                                title = titleLd.value!!,
                                description = descriptionLd.value!!,
                                location = locationLd.value!!,
                                meetingPoint = meetingPointLd.value!!,
                                maxOfPeople = maxOfPeopleLd.value?.toInt() ?: 0,
                                startAt = startAtLd.value!!,
                                category = "api/categories/${categoryIdLiveData.value.toString()}",
                                creator = "api/users/${sharedPref.getUserId()}"
                            )

                        )
                    }

                    val body = responseNewActivityEvent?.body()

                    when {
                        responseNewActivityEvent == null ->
                            _messageLiveData.value = context.getString(R.string.server_error)

                        responseNewActivityEvent.isSuccessful && (body != null) -> {
                            _messageLiveData.value = context.getString(R.string.activity_event_create)
                            _codeLiveData.value = responseNewActivityEvent.code()
                        }

                        responseNewActivityEvent.code() == ERROR_403 ->
                            _messageLiveData.value = context.getString(R.string.unauthorized)
                    }
                }

            } catch (e: ConnectException) {
                _messageLiveData.value = context.getString(R.string.no_connection)
            } catch (erno: ErrnoException) {
                _messageLiveData.value = context.getString(R.string.no_connection)
            }


        } else {
            _messageLiveData.value = context.getString(R.string.empty_fields)
        }
    }
}