package com.lx.net.widget.dialog

import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatDialogFragment

/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.widget.dialog
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
abstract class ViewConvertListener<T : AppCompatDialogFragment>() : Parcelable {

    constructor(parcel: Parcel) : this()

    abstract fun convertView(holder: ViewHolder, dialog: T)

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    private val creator: Parcelable.Creator<ViewConvertListener<T>?> by lazy(LazyThreadSafetyMode.NONE) {

        object : Parcelable.Creator<ViewConvertListener<T>?> {

            override fun createFromParcel(source: Parcel): ViewConvertListener<T> {
                return object : ViewConvertListener<T>(source) {

                    override fun convertView(holder: ViewHolder, dialog: T) {
                    }
                }
            }

            override fun newArray(size: Int): Array<ViewConvertListener<T>?> {
                return arrayOfNulls<ViewConvertListener<T>?>(size)
            }
        }

    }

}