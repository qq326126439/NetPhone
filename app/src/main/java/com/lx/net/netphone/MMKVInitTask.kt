package com.lx.net.netphone

import android.app.Application
import com.lx.net.base.ext.SyncInitTask
import com.lx.net.common.constant.Constants
import com.lx.net.common.utils.LogCompat.logI
import com.lx.net.local.MMKVUtil
import com.tencent.mmkv.MMKV

class MMKVInitTask : SyncInitTask() {

    override fun init(application: Application) {
        val rootDir = MMKV.initialize(application)
        ("mmkv root: $rootDir").logI(TAG)
        MMKVUtil.write(Constants.API_BASE_URL, BuildConfig.BASE_URL)
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun level(): Int {
        return -3
    }

    override fun asyncTaskName(): String {
        return "MMKV"
    }

}