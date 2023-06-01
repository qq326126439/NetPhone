package com.lx.net.base.ext

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.annotation.ArrayRes

fun Spinner.selectedValue(@ArrayRes arrayId: Int): String {
    return resources.getStringArray(arrayId)[selectedItemPosition]
}

fun Spinner.setSelection(value: String, @ArrayRes arrayId: Int) {
    val stringArray = resources.getStringArray(arrayId)
    val index = stringArray.indexOf(value)
    if (index != -1) {
        this.setSelection(index)
    }
}

fun Spinner.addOnItemSelectedListener(block: (parent: AdapterView<*>?, view: View?, position: Int, id: Long) -> Unit) {
    onItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                block.invoke(parent, view, position, id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no-op
            }
        }
}