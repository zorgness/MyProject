package com.example.myproject.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.dataclass.CategoryDto
import com.example.myproject.dataclass.GetCategoriesDto
import com.example.myproject.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _categoriesLiveData = MutableLiveData<List<CategoryDto>>()

    private val _categoryIdLiveData = MutableLiveData<Int>()

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()

    private val _messageLiveData = MutableLiveData<String>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val categoriesLiveData: LiveData<List<CategoryDto>>
        get() = _categoriesLiveData

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData

    val categoryIdLiveData: LiveData<Int>
            get() = _categoryIdLiveData


    init {
        getAllCategories()
    }

    fun setCategoryId(categoryId: Int) {
        _categoryIdLiveData.value = categoryId
    }


    private fun getAllCategories() {
        viewModelScope.launch {

            try {
                _progressBarVisibilityLiveData.value = true
                val responseCategories: Response<GetCategoriesDto>? = withContext(Dispatchers.IO) {
                    apiService.fetchAllCategories()
                }

                val body = responseCategories?.body()

                when {
                    responseCategories == null -> {

                        _messageLiveData.value = "erreur du serveur"
                    }

                    responseCategories.isSuccessful && (body != null) -> {
                        _categoriesLiveData.value = body.categories
                    }


                    responseCategories.code() == 403 ->
                        _messageLiveData.value = "erreur d'authorisation"
                }

                _progressBarVisibilityLiveData.value = false

            } catch (e: ConnectException) {
                _messageLiveData.value = "pas de connection"
            }
        }

    }

}

/* private fun getAllCategories() {

        viewModelScope.launch() {

            _progressBarVisibilityLiveData.value = true
            withContext(Dispatchers.IO) {
                getRemoteCategories {
                    _categoriesLiveData.value = it
                }
            }
            _progressBarVisibilityLiveData.value = false
        }
    }*/
