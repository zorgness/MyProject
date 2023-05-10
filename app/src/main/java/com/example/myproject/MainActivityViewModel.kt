package com.example.myproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myproject.utils.MySharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val sharedPref: MySharedPref
) : ViewModel() {

    private val _isLoggedOutLiveData = MutableLiveData<Boolean>(false)
    private val _isGoToHome = MutableLiveData<Boolean>(false)
    private val _isGoToProfile = MutableLiveData<Boolean>(false)
    private val _imageUrlLiveData = MutableLiveData<String>()

    val isLoggedOutLiveData: LiveData<Boolean>
        get() = _isLoggedOutLiveData

    val isGoToHome: LiveData<Boolean>
        get() = _isGoToHome

    val isGoToProfile: LiveData<Boolean>
        get() = _isGoToProfile

    val imageUrlLiveData: LiveData<String>
        get() = _imageUrlLiveData


    fun fetchUserImageUrl() {
        _imageUrlLiveData.value = sharedPref.getImageUrl() ?: ""
    }

    fun logout() {
        sharedPref.clearSharedPref()
        _isLoggedOutLiveData.value = true
    }

    fun goToHome() {
        _isGoToHome.value = true
    }

    fun goToProfile() {
        _isGoToProfile.value = true
    }
}