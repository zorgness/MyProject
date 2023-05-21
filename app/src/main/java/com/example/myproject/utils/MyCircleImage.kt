package com.example.myproject.utils

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView

import androidx.navigation.fragment.findNavController
import com.example.myproject.ui.details_activity_event.DetailsActivityFragmentDirections
import de.hdodenhof.circleimageview.CircleImageView


class MyCircleImage(
    val context: Context,
    val imageUrl: String,
    val onItemClick: () -> Unit
) {



    private val density = context.resources.displayMetrics.density
    private val params = LinearLayout.LayoutParams((65 * density).toInt(),(65 * density).toInt())
    private val circleImageView = CircleImageView(context)

    init {
        myPicassoFun(imageUrl, circleImageView)
        circleImageView.layoutParams = params
        circleImageView.setPadding((8 * density).toInt(),0,0,0)

        circleImageView.setOnClickListener {
            onItemClick()
        }
    }

}