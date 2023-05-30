package com.example.myproject.utils

import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import java.util.*


class MyDatePicker(val context: Context, private val editText: EditText) {

    private var fullDate = ""
    private var today = 0L
    private var year = 0
    private var month = 0
    private var day = 0

    fun showDatePickerDialog() {
        Calendar.getInstance().apply {

            year = get(Calendar.YEAR)
            month = get(Calendar.MONTH)
            day = get(Calendar.DAY_OF_MONTH)
        }.run {
            today = timeInMillis
        }

        DatePickerDialog(context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                fullDate = "$dayOfMonth-${month + 1}-$year"
                editText.setText(fullDate)
            }, year, month, day)
            .apply {
                datePicker.minDate = today
            }.show()






    }

}