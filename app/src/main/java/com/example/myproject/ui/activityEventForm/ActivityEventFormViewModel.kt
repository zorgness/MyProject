package com.example.myproject.ui.activityEventForm

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myproject.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityEventFormViewModel @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
): ViewModel()  {
}