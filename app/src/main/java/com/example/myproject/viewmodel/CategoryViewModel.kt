package com.example.myproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myproject.dataclass.CategoryDto
import getRemoteCategories

class CategoryViewModel: ViewModel() {

    private val _categoriesLiveData = MutableLiveData<List<CategoryDto>>()

    val categoriesLiveData: LiveData<List<CategoryDto>>
        get() = _categoriesLiveData

    init {
        getRemoteCategories {
            _categoriesLiveData.value = it
        }
    }
}