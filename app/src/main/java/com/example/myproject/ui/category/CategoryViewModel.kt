package com.example.myproject.ui.category

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.dataclass.CategoryDto
import com.example.myproject.dataclass.GetCategoriesDto
import com.example.myproject.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException

class CategoryViewModel : ViewModel() {

    private val _categoriesLiveData = MutableLiveData<List<CategoryDto>>()

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()

    private val _errorMessageLiveData = MutableLiveData<String>()

    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData

    val categoriesLiveData: LiveData<List<CategoryDto>>
        get() = _categoriesLiveData

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData


    init {
        getAllCategories()
    }


    private fun getAllCategories() {
        viewModelScope.launch {

            try {
                _progressBarVisibilityLiveData.value = true
                val responseCategories: Response<GetCategoriesDto>? = withContext(Dispatchers.IO) {
                    ApiService.getApi().getAllCategories()
                }

                val body = responseCategories?.body()

                when {
                    responseCategories == null -> {

                        _errorMessageLiveData.value = "erreur du serveur"
                    }

                    responseCategories.isSuccessful && (body != null) -> {
                        _categoriesLiveData.value = body.categories
                    }


                    responseCategories.code() == 403 ->
                        _errorMessageLiveData.value = "erreur d'authorisation"
                }

                _progressBarVisibilityLiveData.value = false

            } catch (e: ConnectException) {
                _errorMessageLiveData.value = "pas de connection"
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
