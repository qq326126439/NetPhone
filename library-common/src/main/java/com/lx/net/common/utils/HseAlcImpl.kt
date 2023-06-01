package com.lx.net.common.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.lx.net.common.listener.StatusListener
import com.lx.net.common.utils.LogCompat.logI
import java.io.Serializable
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author : lxm
 * @package_name ：com.lx.net.common.utils
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2021年3月29日
 * @description ：activity生命周期监听
 */
class HseAlcImpl private constructor() : Serializable {


    companion object : Application.ActivityLifecycleCallbacks {

        private val TAG = HseAlcImpl::class.java.simpleName
        private val mStatusListeners:
                MutableList<StatusListener> = CopyOnWriteArrayList()
        private val mActivityList = LinkedList<Activity>()
        private lateinit var mApplication: Application
        private val resumeNumbers by lazy { mActivityList.size }
        private var mForegroundCount = 0
        private var mConfigCount = 0
        private var mIsBackground = false


        fun getInstance(): HseAlcImpl {
            return SingletonHolder.instance
        }

        fun getApplication(): Application {
            return mApplication
        }

        fun bindApplication(application: Application) {
            mApplication = application
            application.registerActivityLifecycleCallbacks(this)
        }

        fun addStatusChangedListener(listener: StatusListener) {
            mStatusListeners.add(listener)
        }

        fun removeStatusChangedListener(listener: StatusListener) {
            mStatusListeners.remove(listener)
        }

        private fun postStatus(activity: Activity, isForeground: Boolean) {
            if (mStatusListeners.isEmpty()) return
            for (statusListener in mStatusListeners) {
                if (isForeground) {
                    statusListener.onForeground(activity)
                } else {
                    statusListener.onBackground(activity)
                }
            }
        }

        private fun setTopActivity(activity: Activity) {
            if (mActivityList.contains(activity)) {
                if (mActivityList.first != activity) {
                    mActivityList.remove(activity)
                    mActivityList.addFirst(activity)
                }
            } else {
                mActivityList.addFirst(activity)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            ActivityManager.pushActivity(activity = activity)
            ("${activity.componentName} -- ").logI(TAG)
            ("$resumeNumbers -- $mForegroundCount -- $mConfigCount").logI(TAG)
            if (resumeNumbers == 0)
                postStatus(activity = activity, isForeground = true)
            setTopActivity(activity = activity)
        }

        override fun onActivityStarted(activity: Activity) {
            if (!mIsBackground)
                setTopActivity(activity)
            if (mConfigCount < 0)
                ++mConfigCount
            else
                ++mForegroundCount
        }

        override fun onActivityResumed(activity: Activity) {
            //resumeNumbers ++
            setTopActivity(activity = activity)
            if (mIsBackground) {
                mIsBackground = false
                postStatus(activity = activity, isForeground = true)
            }
            ActivityManager.setCurrentActivity(activity = WeakReference(activity))
            ("${activity.componentName} -- ").logI(TAG)
            ("$resumeNumbers -- $mForegroundCount -- $mConfigCount").logI(TAG)
        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
            if (activity.isChangingConfigurations) {
                --mConfigCount
            } else {
                --mForegroundCount
                if (mForegroundCount <= 0) {
                    mIsBackground = true
                    postStatus(activity = activity, isForeground = false)
                }
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
            //resumeNumbers --
            mActivityList.remove(activity)
            ActivityManager.popActivity(activity = WeakReference(activity))
            ("${activity.componentName} -- ").logI(TAG)
            ("$resumeNumbers -- $mForegroundCount -- $mConfigCount").logI(TAG)
        }

    }

    private object SingletonHolder {
        val instance = HseAlcImpl()
    }

    private fun readResolve(): Any {//防止单例对象在反序列化时重新生成对象
        return SingletonHolder.instance
    }

}