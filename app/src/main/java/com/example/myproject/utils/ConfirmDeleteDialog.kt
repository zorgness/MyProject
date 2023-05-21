package com.example.myproject.utils


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myproject.R

class ConfirmDeleteDialog(val onConfirm: ()-> Unit): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.confirm_delete))
            .setPositiveButton(getString(R.string.ok)) { _,_ ->
                onConfirm()
            }
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                dismiss()
            }
            .create()

    companion object {
        const val TAG = "DeleteConfirmationDialog"
    }
}