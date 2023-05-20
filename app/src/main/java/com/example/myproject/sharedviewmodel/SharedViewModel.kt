package com.example.myproject.sharedviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {
    
    private val _refreshListLiveData = MutableLiveData<Int>()
    
    val refreshListLiveData: LiveData<Int>
        get() = _refreshListLiveData

    fun refreshListByCategoryId(categoryId: Int) {
        _refreshListLiveData.value = categoryId
    }
}