package com.example.myproject.ui.activities_by_category

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dataclass.ActivityEventDto
import com.example.myproject.dataclass.GetActivityByCategoryDto
import com.example.myproject.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ActivityByCategoryViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) : ViewModel() {

    private val _activityEventByCategoryLiveData = MutableLiveData<List<ActivityEventDto>>()

    private val _activityEventLiveData = MutableLiveData<ActivityEventDto>()

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()

    private val _messageLiveData = MutableLiveData<String>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val activityEventByCategoryLiveData: LiveData<List<ActivityEventDto>>
        get() = _activityEventByCategoryLiveData

    val activityEventLiveData: LiveData<ActivityEventDto>
        get() = _activityEventLiveData

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData

    fun setActivityEvent(activityEvent: ActivityEventDto){
        _activityEventLiveData.value = activityEvent
    }

    fun getActivityEventByCategory(categoryId: Int) {
        viewModelScope.launch {
            _progressBarVisibilityLiveData.value = true
            val responseActivityByCategory: Response<GetActivityByCategoryDto>? =
                withContext(Dispatchers.IO) {
                    apiService.fetchActivityByCategory(categoryId)
                }

            val body = responseActivityByCategory?.body()

            when {
                responseActivityByCategory == null -> {

                    _messageLiveData.value = context.getString(R.string.server_error)
                }

                responseActivityByCategory.isSuccessful && (body != null) -> {
                    _activityEventByCategoryLiveData.value = body.activitiesEvent
                    _progressBarVisibilityLiveData.value = false
                }


                responseActivityByCategory.code() == 403 ->
                    _messageLiveData.value = context.getString(R.string.unauthorized)
            }

        }
    }
}

