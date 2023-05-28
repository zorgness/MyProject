package com.example.myproject.ui.activities_list

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
class ActivitiesListViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPref: MySharedPref,
    private val context: Context
) : ViewModel() {

    private val _activitiesListLiveData = MutableLiveData<List<ActivityEventDto>>()

    private val _activityEventLiveData = MutableLiveData<ActivityEventDto>()

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>(true)

    private val _isEmptyListLiveData = MutableLiveData<Boolean>(false)

    private val _messageLiveData = MutableLiveData<String>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    //List
    val activitiesListLiveData: LiveData<List<ActivityEventDto>>
        get() = _activitiesListLiveData

    val activityEventLiveData: LiveData<ActivityEventDto>
        get() = _activityEventLiveData

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData

    val isEmptyListLiveData: LiveData<Boolean>
        get() = _isEmptyListLiveData

    var activitiesFullList = listOf<ActivityEventDto>()

    private val headers = HashMap<String, String>()

    init {
        fetchActivitiesAll()
    }
    fun fetchListToShow(categoryId: Int) {

        //_activitiesListLiveData.value = activitiesFullList

      /*  if(categoryId > 0){
            _activitiesListLiveData.value = activitiesFullList.filter { element -> element.category.id == categoryId }
        } else {
            _activitiesListLiveData.value = activitiesFullList
        }*/

    }
    fun setActivityEvent(activityEvent: ActivityEventDto){
        _activityEventLiveData.value = activityEvent
    }


    private fun fetchActivitiesAll() {
        headers["Authorization"] = "Bearer ${sharedPref.getToken() ?: ""}"

        try {
            viewModelScope.launch {
                val responseActivityByCategory: Response<GetActivitiesDto>? =
                    withContext(Dispatchers.IO) {

                        apiService.fetchActivitiesAll(headers)
                    }

                val body = responseActivityByCategory?.body()

                when {
                    responseActivityByCategory == null -> {

                        _messageLiveData.value = context.getString(R.string.server_error)
                    }

                    responseActivityByCategory.isSuccessful && (body != null) -> {

                        _activitiesListLiveData.value  = body.activitiesEvent
                        _progressBarVisibilityLiveData.value = false
                      /*  if(body.activitiesEvent.isEmpty())
                            _isEmptyListLiveData.value = true*/
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

