package com.example.myproject.ui.new_edit_activities_form

import CODE_201
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
import com.example.myproject.dto.activities.ActivityEventDto
import com.example.myproject.dto.activities.ActivityEventPostDto
import com.example.myproject.extensions.toHydraCategoryId
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
class NewEditFormViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context,
    private val sharedPref: MySharedPref,
) : ViewModel() {


    private val _messageLiveData = MutableLiveData<String>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    private val _newItemCategoryId = MutableLiveData<Int>()

    val newItemCategoryId: LiveData<Int>
        get() = _newItemCategoryId


    private val _codeLiveData = MutableLiveData<Int>()

    val codeLiveData: LiveData<Int>
        get() = _codeLiveData

    private val _activityIdToUpdateLiveData = MutableLiveData<Int>()

    private val activityIdToUpdateLiveData: LiveData<Int>
        get() = _activityIdToUpdateLiveData



    /**
     * Variables used for Two way binding in xml
     *
     * positionSelectedLd refer to the spinner category position
     */
    val positionSelectedLd = MutableLiveData<Int>(0)
    var titleLd = MutableLiveData<String>("")
    var descriptionLd = MutableLiveData<String>("")
    var locationLd = MutableLiveData<String>("")
    var meetingPointLd = MutableLiveData<String>("")
    var meetingTimeLd = MutableLiveData<String>("")
    var maxOfPeopleLd = MutableLiveData<String>("")
    var startAtLd = MutableLiveData<String>("")

    var categoryId: Int? = null
    private val headers = HashMap<String, String>()


    /**
     * Set the fields values on Edit
     */
    fun setActivityToUpdate(activityEventDto: ActivityEventDto?) {
        _activityIdToUpdateLiveData.value = activityEventDto?.id
        positionSelectedLd.value = activityEventDto?.category?.id?.minus(1)
        titleLd.value = activityEventDto?.title
        descriptionLd.value = activityEventDto?.description
        locationLd.value = activityEventDto?.location
        meetingPointLd.value = activityEventDto?.meetingPoint
        meetingTimeLd.value = activityEventDto?.meetingTime
        maxOfPeopleLd.value = activityEventDto?.maxOfPeople?.toString() ?: ""
        //startAtLd.value = dateFormatter(activityEventDto?.startAt ?: "01-01-2023T0000.00")
        startAtLd.value = activityEventDto?.startAt
    }

    fun createActivityEvent() {

        categoryId = positionSelectedLd.value?.plus(1)
        headers["Authorization"] = "Bearer ${sharedPref.getToken() ?: ""}"

        if (
            titleLd.value?.isNotBlank() == true
            &&
            descriptionLd.value?.isNotBlank() == true
            &&
            locationLd.value?.isNotBlank() == true
            &&
            meetingPointLd.value?.isNotBlank() == true
            &&
            meetingTimeLd.value?.isNotBlank() == true
            &&
            startAtLd.value?.isNotBlank() == true
            &&
            maxOfPeopleLd.value?.toInt()!! >= 1

        ) {

            try {

                viewModelScope.launch {
                    val responseNewActivityEvent: Response<ActivityEventDto>? = withContext(Dispatchers.IO) {
                        apiService.createActivityEvent(
                            headers,
                            ActivityEventPostDto(
                                title = titleLd.value!!,
                                description = descriptionLd.value!!,
                                location = locationLd.value!!,
                                meetingPoint = meetingPointLd.value!!,
                                maxOfPeople = maxOfPeopleLd.value?.toInt() ?: 1,
                                startAt = startAtLd.value!!,
                                category = categoryId?.toHydraCategoryId()!!,
                                creator = sharedPref.getUserId().toHydraUserId(),
                                meetingTime = meetingTimeLd.value!!
                            )

                        )
                    }

                    val body = responseNewActivityEvent?.body()

                    when {
                        responseNewActivityEvent == null ->
                            _messageLiveData.value = context.getString(R.string.server_error)

                        responseNewActivityEvent.isSuccessful && (body != null) -> {
                            if(responseNewActivityEvent.code() == CODE_201) {
                                _messageLiveData.value = context.getString(R.string.activity_event_create)
                                _newItemCategoryId.value = categoryId
                            }
                        }

                        responseNewActivityEvent.code() == ERROR_400 ->
                            _messageLiveData.value = context.getString(R.string.error_parameter)

                        responseNewActivityEvent.code() == ERROR_403 ->
                            _messageLiveData.value = context.getString(R.string.unauthorized)

                        responseNewActivityEvent.code() == ERROR_422 ->
                            _messageLiveData.value = context.getString(R.string.unprocessable_entity)
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


    fun updateActivityEvent() {

        categoryId = positionSelectedLd.value?.plus(1)

        headers["Authorization"] = "Bearer ${sharedPref.getToken() ?: ""}"

        if (
            titleLd.value?.isNotBlank() == true
            &&
            descriptionLd.value?.isNotBlank() == true
            &&
            locationLd.value?.isNotBlank() == true
            &&
            meetingPointLd.value?.isNotBlank() == true
            &&
            meetingTimeLd.value?.isNotBlank() == true
            &&
            startAtLd.value?.isNotBlank() == true
            &&
            maxOfPeopleLd.value?.toInt()!! >= 1

        ) {

            try {

                viewModelScope.launch {
                    val responseUpdateActivityEvent: Response<ActivityEventDto>? = withContext(Dispatchers.IO) {
                        apiService.updateActivityEvent(
                            headers,
                            activityId = activityIdToUpdateLiveData.value!!,
                            ActivityEventPostDto(
                                title = titleLd.value!!,
                                description = descriptionLd.value!!,
                                location = locationLd.value!!,
                                meetingPoint = meetingPointLd.value!!,
                                maxOfPeople = maxOfPeopleLd.value?.toInt() ?: 1,
                                startAt = startAtLd.value!!,
                                category = categoryId?.toHydraCategoryId()!!,
                                creator = sharedPref.getUserId().toHydraUserId(),
                                meetingTime = meetingTimeLd.value!!
                            )

                        )
                    }

                    val body = responseUpdateActivityEvent?.body()

                    when {
                        responseUpdateActivityEvent == null ->
                            _messageLiveData.value = context.getString(R.string.server_error)

                        responseUpdateActivityEvent.isSuccessful && (body != null) -> {
                                _messageLiveData.value = context.getString(R.string.activity_event_updated)
                                _codeLiveData.value = responseUpdateActivityEvent.code()
                        }

                        responseUpdateActivityEvent.code() == ERROR_400 ->
                            _messageLiveData.value = context.getString(R.string.error_parameter)

                        responseUpdateActivityEvent.code() == ERROR_404 ->
                            _messageLiveData.value = context.getString(R.string.unknow_resource)

                        responseUpdateActivityEvent.code() == ERROR_422 ->
                            _messageLiveData.value = context.getString(R.string.unprocessable_entity)
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