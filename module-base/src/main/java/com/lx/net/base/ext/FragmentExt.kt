package com.lx.net.base.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * fragment切换
 *
 * @param to
 * @param fragmentManager
 * @param layoutId
 */
fun Fragment?.switch(
    to: Fragment?,
    fragmentManager: FragmentManager?,
    layoutId: Int,
    isHideFrom: Boolean = true
) {
    if (fragmentManager == null || layoutId <= 0 || this == to) {
        return
    }
    val transaction = fragmentManager.beginTransaction()
    if (to != null) {
        if (this != null && isHideFrom) {
            // fragmentManager.popBackStack();
            transaction.hide(this)
        }
        if (!to.isAdded) {
            // transaction.addToBackStack(null);
            transaction.add(layoutId, to, to.javaClass.simpleName)
        } else {
            transaction.show(to)
        }
    }
    transaction.commitAllowingStateLoss()
}

fun Fragment?.hide(fragmentManager: FragmentManager?){
    this?.let { fragmentManager?.beginTransaction()?.hide(it)?.commitNowAllowingStateLoss() }
}