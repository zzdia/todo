package com.example.todo

import android.app.AlertDialog
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun showAlert(view: View, title: Int, message: String) {
    AlertDialog.Builder(view.context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.alert_ok) { _,_ -> }
        .setCancelable(false)
        .show()
}