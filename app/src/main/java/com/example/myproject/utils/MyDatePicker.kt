package com.example.myproject.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import java.util.*


class MyDatePicker(val context: Context, private val editText: EditText) {

    private var fullDate = ""

    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                fullDate = "$dayOfMonth-${month + 1}-$year"
                editText.setText(fullDate)
            }, year, month, day)

        datePickerDialog.show()
    }

}