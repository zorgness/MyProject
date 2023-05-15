package com.example.myproject.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import java.util.*

class MyTimePicker(val context: Context, private val editText: EditText) {

        private var hour: Int = 0
        private var minute: Int = 0

        fun showTimePickerDialog() {
            Calendar.getInstance().apply {
                hour = get(Calendar.HOUR_OF_DAY)
                minute = get(Calendar.MINUTE)
            }

            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->

                    editText.setText(String.format("%d : %d", hourOfDay, minute))
                }, hour, minute, false
            ).show()
        }


}