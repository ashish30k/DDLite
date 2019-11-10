package com.ashish.android.doordash.core.extenstions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Context.displayToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun FragmentActivity.addFragmentToBackStack(containerId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .addToBackStack(null)
        .add(
            containerId,
            fragment,
            fragment::class.java.simpleName
        ).commit()
}


fun FragmentActivity.replaceFragmentToBackStack(containerId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(
            containerId,
            fragment,
            fragment::class.java.simpleName
        ).commit()
}