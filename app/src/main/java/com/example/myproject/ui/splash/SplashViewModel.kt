package com.example.myproject.ui.splash

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.myproject.utils.MySharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPref: MySharedPref
): ViewModel() {

    private val _destinationLiveData = MutableLiveData<NavDirections>()

    val destinationLiveData: LiveData<NavDirections>
        get() = _destinationLiveData

    init {
        Handler().postDelayed({
            getDestination()
        }, 2000)
    }

    private fun getDestination() {

        _destinationLiveData.value = sharedPref.token.let {token->
                if(token != null)
                    SplashFragmentDirections.actionSplashFragmentToMainFragment()
                else
                    SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            }
    }
}