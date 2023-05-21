package com.example.myproject.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myproject.R
import com.example.myproject.dataclass.booking.BookingDto

class BookingDialog(val bookingDto: BookingDto): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("bravo")
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
              dismiss()
            }
            .create()

    companion object {
        const val TAG = "BookingDialog"
    }

}