package com.example.myproject.ui.activityEventForm

import CODE_201
import ERROR_400
import ERROR_403
import ERROR_422
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
class ActivityEventFormViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context,
    private val sharedPref: MySharedPref,
) : ViewModel() {


    private val _messageLiveData = MutableLiveData<String>()

    private val _newItemCategoryId = MutableLiveData<Int>()

    val newItemCategoryId: LiveData<Int>
       get() = _newItemCategoryId

    val messageLiveData: LiveData<String>
        get() = _messageLiveData


    /**
     * Variables used for Two way binding in xml
     */
    val positionSelectedLd = MutableLiveData<Int>(0)
    var titleLd = MutableLiveData<String>("")
    var descriptionLd = MutableLiveData<String>("")
    var locationLd = MutableLiveData<String>("")
    var meetingPointLd = MutableLiveData<String>("")
    var maxOfPeopleLd = MutableLiveData<String>("")
    var startAtLd = MutableLiveData<String>("")

    var categoryId: Int? = null

    fun createActivityEvent() {

        categoryId = positionSelectedLd.value?.plus(1)

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
                                category = categoryId?.toHydraCategoryId()!!,
                                creator = sharedPref.getUserId().toHydraUserId()
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
                            _messageLiveData.value = context.getString(R.string.parameter_problem)

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
}