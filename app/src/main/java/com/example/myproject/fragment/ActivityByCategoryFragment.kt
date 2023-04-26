package com.example.myproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import com.example.myproject.R
import com.example.myproject.adapter.ActivityEventByCategoryAdapter
import com.example.myproject.viewmodel.ActivityByCategoryViewModel



class ActivityByCategoryFragment : Fragment() {

    private val myViewModel: ActivityByCategoryViewModel by viewModels()
    private lateinit var activityEventByCategoryAdapter: ActivityEventByCategoryAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity_by_category, container, false)
    }


}