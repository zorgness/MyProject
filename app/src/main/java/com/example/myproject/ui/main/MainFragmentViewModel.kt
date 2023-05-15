package com.example.myproject.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myproject.utils.MySharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val sharedPref: MySharedPref
): ViewModel() {

    private val _isLoggedOutLiveData = MutableLiveData<Boolean>(false)

    val isLoggedOutLiveData: LiveData<Boolean>
        get() = _isLoggedOutLiveData


    fun logout() {
        sharedPref.clearSharedPref()
        _isLoggedOutLiveData.value = true
    }

}