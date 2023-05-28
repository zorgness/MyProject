package com.example.myproject.ui.category

import ERROR_401
import ERROR_403
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.R
import com.example.myproject.dto.category.CategoryDto
import com.example.myproject.dto.category.GetCategoriesDto
import com.example.myproject.network.ApiService
import com.example.myproject.utils.MySharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPref: MySharedPref,
    private val context: Context
) : ViewModel() {

    private val _categoryIdLiveData = MutableLiveData<Int>()

    private val _categoriesLiveData = MutableLiveData<List<CategoryDto>>()

    private val _messageLiveData = MutableLiveData<String>()

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()


    val categoryIdLiveData: LiveData<Int>
        get() = _categoryIdLiveData

    val categoriesLiveData: LiveData<List<CategoryDto>>
        get() = _categoriesLiveData

    val messageLiveData: LiveData<String>
        get() = _messageLiveData

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData

    private val headers = HashMap<String, String>()

    fun setCategoryId(categoryId: Int) {
        _categoryIdLiveData.value = categoryId
    }


    fun fetchAllCategories() {
        viewModelScope.launch {

            headers["Authorization"] = "Bearer ${sharedPref.getToken()}"

            try {
                _progressBarVisibilityLiveData.value = true
                val responseCategories: Response<GetCategoriesDto>? = withContext(Dispatchers.IO) {
                    apiService.fetchAllCategories(headers)
                }

                val body = responseCategories?.body()

                when {
                    responseCategories == null -> {

                        _messageLiveData.value = context.getString(R.string.server_error)
                    }

                    responseCategories.isSuccessful && (body != null) -> {
                        _categoriesLiveData.value = body.categories

                    }

                    responseCategories.code() == ERROR_401 ->
                        _messageLiveData.value = context.getString(R.string.error_parameter)

                    responseCategories.code() == ERROR_403 ->
                        _messageLiveData.value = context.getString(R.string.unauthorized)
                }

                _progressBarVisibilityLiveData.value = false

            } catch (e: ConnectException) {
                _messageLiveData.value = context.getString(R.string.no_connection)
            }

        }
    }

}
