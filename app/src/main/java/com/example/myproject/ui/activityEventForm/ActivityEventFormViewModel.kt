package com.example.myproject.ui.activityEventForm

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myproject.R
import com.example.myproject.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityEventFormViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context,
    private val sharedPref: SharedPreferences
): ViewModel()  {


    private val _messageLiveData = MutableLiveData<String>()
    private val _codeLiveData = MutableLiveData<Int>()
    private val _categoryLiveData  = MutableLiveData<Int>(0)

    var titleLd = MutableLiveData<String>("")
    var descriptionLd  = MutableLiveData<String>("")
    var locactionLd  = MutableLiveData<String>("")
    var meetingPointLd  = MutableLiveData<String>("")
    var maxOfPeopleLd  = MutableLiveData<String>("")
    var startAtLd  = MutableLiveData<String>("")

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val codeLiveData: LiveData<Int>
        get() = _codeLiveData

    private val categoryLiveData: LiveData<Int>
        get() = _categoryLiveData


    fun getCategoryId(categoryId: Int) {
        _categoryLiveData.value = categoryId
    }
    fun createActivityEvent() {

        if
        (
            ((titleLd.value?.isNotBlank() == true
                    &&
                    descriptionLd.value?.isNotBlank() == true
                    &&
                    locactionLd.value?.isNotBlank() == true)
                    && maxOfPeopleLd.value?.toInt()!! < 1
                    && startAtLd.value?.isNotBlank() == true)
                    && categoryLiveData.value != 0
        ) {



        }
        else {
            _messageLiveData.value = context.getString(R.string.empty_fields)
        }
    }
}