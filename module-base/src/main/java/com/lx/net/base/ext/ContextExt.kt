package com.lx.net.base.ext

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Context.*
import android.location.LocationManager
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

/**
 * https://blog.csdn.net/zengke1993/article/details/107078876/
 */
fun Context.getColorCompat(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.getDrawableCompat(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

val Context.inflater : LayoutInflater
 get() = LayoutInflater.from(this)

fun Context.inflateLayout(
    @LayoutRes layoutId: Int, parent: ViewGroup? = null,
    attachToRoot: Boolean = parent != null
): View = inflater.inflate(layoutId, parent, attachToRoot)

val Context.locationManager : LocationManager
 get() = getSystemService(LOCATION_SERVICE) as LocationManager

val Context.inputManager: InputMethodManager
    get() = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.notificationManager: NotificationManager
    get() = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

val Context.isNotificationOpen :Boolean
 get() = NotificationManagerCompat.from(this).areNotificationsEnabled()

fun Activity.hideSoftKeyboard(focusView: View? = null) {
    val focusV = focusView ?: currentFocus
    focusV?.apply {
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun Context.hideSoftKeyboard(focusView: View? = null) {
    focusView?.apply {
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun Context.showSoftKeyboard(editText: EditText) {
    editText.postDelayed({
        editText.requestFocus()
        inputManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }, 30)
}


fun Activity.showSoftKeyboard(editText: EditText) {
    editText.postDelayed({
        editText.requestFocus()
        inputManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }, 30)
}

val Context.screenWidthPx: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeightPx: Int
    get() = resources.displayMetrics.heightPixels
