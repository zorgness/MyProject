package com.example.myproject.ui.category

import ERROR_401
import ERROR_403
import android.content.Context
import android.util.Log
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

    // KEEP TRACK OF SELECTED CATEGORY ID
    private val _categoryIdLiveData = MutableLiveData<Int>()

    val categoryIdLiveData: LiveData<Int>
        get() = _categoryIdLiveData

    // LIST OF CATEGORIES
    private val _categoriesListLiveData = MutableLiveData<List<CategoryDto>>()

    val categoriesListLiveData: LiveData<List<CategoryDto>>
        get() = _categoriesListLiveData


    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()

    val progressBarVisibilityLiveData: LiveData<Boolean>
        get() = _progressBarVisibilityLiveData

    private val _messageLiveData = MutableLiveData<String>()

    val messageLiveData: LiveData<String>
        get() = _messageLiveData



    private val headers = HashMap<String, String>()

    fun setCategoryId(categoryId: Int) {
        Log.d("category Id", categoryId.toString())
        _categoryIdLiveData.value = categoryId
    }


    fun fetchAllCategories() {
        viewModelScope.launch {

            headers["Authorization"] = "Bearer ${sharedPref.token}"

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
                        _categoriesListLiveData.value = body.categories

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
