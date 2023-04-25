package com.example.myproject.viewmodel

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.dataclass.CategoryDto
import getRemoteCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel: ViewModel() {

    private val _categoriesLiveData = MutableLiveData<List<CategoryDto>>()

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()

    val categoriesLiveData: LiveData<List<CategoryDto>>
        get() = _categoriesLiveData

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() =  _progressBarVisibilityLiveData


    init {
      getAllCategories()
    }

 private fun getAllCategories() {

        viewModelScope.launch() {

            _progressBarVisibilityLiveData.value = true
            withContext(Dispatchers.IO) {
                getRemoteCategories {
                    _categoriesLiveData.value = it
                }
            }
            _progressBarVisibilityLiveData.value = false
        }
    }
}