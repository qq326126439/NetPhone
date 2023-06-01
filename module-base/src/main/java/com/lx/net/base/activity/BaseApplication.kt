package com.lx.net.base.activity

import android.app.Application
import android.content.Context

/***
 *
 * 通用的Application
 */

abstract class BaseApplication : Application(){
    companion object {
        lateinit var instance: BaseApplication
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }


}