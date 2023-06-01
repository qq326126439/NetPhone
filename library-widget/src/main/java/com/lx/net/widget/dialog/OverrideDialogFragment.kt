package com.lx.net.widget.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.view.View.OnTouchListener
import androidx.fragment.app.DialogFragment

/***********************************************************************
 * <p>@description:
 * <p>@author: pengl
 * <p>@created on: 2022/7/27 0027 22:12
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/27 0027 22:12
 **********************************************************************/
open class OverrideDialogFragment : DialogFragment(), OnTouchListener {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.dialog?.setContentView(requireView())
        activity?.let { this.dialog?.setOwnerActivity(it) }
        this.dialog?.setCancelable(false)
        this.dialog?.window?.decorView?.setOnTouchListener(this)
        this.dialog?.setOnKeyListener(DialogInterface.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE) {
                if (onEnableKeyBack()) {
                    dismiss()
                    return@OnKeyListener true
                }
            }
            false
        })
        savedInstanceState?.let {
            val dialogState = it.getBundle("android:savedDialogState")
            dialogState?.let { it1 -> this.dialog?.onRestoreInstanceState(it1) }
        }
    }

    open fun onEnableKeyBack(): Boolean {
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (onEnableKeyBack() && isCancelable && dialog?.isShowing == true) {
            dismiss()
            return true
        }
        return false
    }

}