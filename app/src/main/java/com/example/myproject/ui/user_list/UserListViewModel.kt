package com.example.myproject.ui.user_list

import android.content.Context
import android.system.ErrnoException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dto.activity_event.ActivityEventDto
import com.example.myproject.dto.activity_event.GetActivitiesDto
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
class UserListViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPref: MySharedPref,
    private val context: Context
): ViewModel() {

    // LIST TO BE DISPLAY
    private val _listToShowLiveData = MutableLiveData<List<ActivityEventDto>>()

    val listToShowLiveData: LiveData<List<ActivityEventDto>>
        get() = _listToShowLiveData

    // USE TO PASS PARCELABLE TO DETAILS FRAGMENT WITH NAV ARGS
    private val _activityEventLiveData = MutableLiveData<ActivityEventDto>()

    val activityEventLiveData: LiveData<ActivityEventDto>
        get() = _activityEventLiveData

    // PROGRESS BAR
    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>(true)

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData

    // DISPLAY MESSAGE FOR USER
    private val _messageLiveData = MutableLiveData<String>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val headers = HashMap<String, String>()


    fun setActivityEvent(activityEvent: ActivityEventDto){
        _activityEventLiveData.value = activityEvent
    }


    fun fetchActivitiesByProfile(profileId: Int?, isCreator: Boolean) {
        headers["Authorization"] = "Bearer ${sharedPref.getToken() ?: ""}"

        try {
            viewModelScope.launch {
                val responseActivityByCategory: Response<GetActivitiesDto>? =
                    withContext(Dispatchers.IO) {

                        apiService.fetchActivitiesAll(
                            headers = headers
                        )
                    }

                val body = responseActivityByCategory?.body()

                when {
                    responseActivityByCategory == null -> {

                        _messageLiveData.value = context.getString(R.string.server_error)
                    }

                    responseActivityByCategory.isSuccessful && (body != null) -> {

                        if (isCreator) {
                            _listToShowLiveData.value = body.activitiesEvent.filter { element ->
                                element.creator.id == profileId
                            }
                        } else {
                            _listToShowLiveData.value = body.activitiesEvent.filter { activity ->
                                activity.bookings.any { booking ->
                                    booking.userAccount.id == profileId
                                }
                            }
                        }
                        _progressBarVisibilityLiveData.value = false

                    }


                    responseActivityByCategory.code() == 403 ->
                        _messageLiveData.value = context.getString(R.string.unauthorized)
                }

            }

        } catch (e: ConnectException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        }
        catch (erno: ErrnoException) {
            _messageLiveData.value = context.getString(R.string.no_connection)
        }

    }
}