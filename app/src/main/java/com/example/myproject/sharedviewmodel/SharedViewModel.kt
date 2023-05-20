package com.example.myproject.sharedviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {
    
    private val _categoryListUpdatedLiveData = MutableLiveData<Int>()
    
    val categoryListUpdatedLiveData: LiveData<Int>
        get() = _categoryListUpdatedLiveData

    fun refreshListByCategoryId(categoryId: Int) {
        _categoryListUpdatedLiveData.value = categoryId
    }
}