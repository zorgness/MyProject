package com.example.myproject.ui.activities_by_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.dataclass.ActivityEventDto
import com.example.myproject.dataclass.GetActivityByCategoryDto
import com.example.myproject.dataclass.GetCategoriesDto
import com.example.myproject.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ActivityByCategoryViewModel : ViewModel() {

    private val _activityEventByCategoryLiveData = MutableLiveData<List<ActivityEventDto>>()

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()

    private val _errorMessageLiveData = MutableLiveData<String>()

    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData

    val activityEventByCategoryLiveData: LiveData<List<ActivityEventDto>>
        get() = _activityEventByCategoryLiveData

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData


    fun getActivityEventByCategory(categoryId: Int) {
        viewModelScope.launch {
            _progressBarVisibilityLiveData.value = true
            val responseActivityByCategory: Response<GetActivityByCategoryDto>? =
                withContext(Dispatchers.IO) {
                    ApiService.getApi().getActivityByCategory(categoryId)
                }

            val body = responseActivityByCategory?.body()

            when {
                responseActivityByCategory == null -> {

                    _errorMessageLiveData.value = "erreur du serveur"
                }

                responseActivityByCategory.isSuccessful && (body != null) -> {
                    _activityEventByCategoryLiveData.value = body.activitiesEvent
                }


                responseActivityByCategory.code() == 403 ->
                    _errorMessageLiveData.value = "erreur d'authorisation"
            }

            _progressBarVisibilityLiveData.value = false

        }
    }
}

