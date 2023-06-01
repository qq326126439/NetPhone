package com.lx.net.netphone.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lx.net.base.activity.BaseVmActivity
import com.lx.net.common.constant.Constants
import com.lx.net.common.constant.ParamKey
import com.lx.net.common.constant.RequestCode
import com.lx.net.common.utils.AppUtil
import com.lx.net.common.utils.LogCompat.logE
import com.lx.net.common.vb.viewBinding
import com.lx.net.local.MMKVUtil
import com.lx.net.login.databinding.ActivityRegisterBinding
import com.lx.net.netphone.R
import com.lx.net.netphone.databinding.ActivitySplashBinding
import com.lx.net.router.RouterPath

/***********************************************************************
 * <p>@description:最开始进入页面
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 10:56
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 10:56
 **********************************************************************/

@Route(path = RouterPath.splash)
class SplashActivity : BaseVmActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    //1、首先声明一个数组permissions，将需要的权限都放在里面
    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_PHONE_STATE
    )
    private val mPermissionList = ArrayList<String>()
    private val alphaAnimation by lazy { AlphaAnimation(0.3f, 1.0f) }

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        alphaAnimation.duration = Constants.SPLASH_ANIMATION_DURATION
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationEnd(animation: Animation) {
                jumpActivity()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }

            override fun onAnimationStart(animation: Animation) {

            }

        })
        initPermission()
    }

    override fun bindEvent() {
        super.bindEvent()

        binding.btnLogin.setOnClickListener {

            ARouter.getInstance().build(RouterPath.register)
                .withInt(ParamKey.EXTRA_LOGIN_OR_REGISTER, 0).navigation(this)
            finish()
        }

        binding.btnRegister.setOnClickListener {

            ARouter.getInstance().build(RouterPath.register).navigation(this)
            finish()
        }
    }

    override fun createObserver() {

    }

    private fun initPermission() {
        mPermissionList.clear()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this@SplashActivity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                mPermissionList.add(permission)
            }
        }
        if (mPermissionList.isNotEmpty()) {
            // 后续操作...
            ActivityCompat.requestPermissions(
                this@SplashActivity,
                permissions,
                RequestCode.REQUEST_PERMISSION
            )
        } else {
            binding.ivLauncher.startAnimation(alphaAnimation)
        }
    }

    private fun jumpActivity() {

        val result = MMKVUtil.readString(Constants.USER_TOKEN)
        (" result  : $result").logE()
        if (!result.isNullOrEmpty()) {
            ("已有token = $result，跳转主页面").logE(TAG)
            ARouter.getInstance()
                .build(RouterPath.mainMenu)
                .navigation(this)
            finish()
        }
//        else {
//            ( "没有token，跳转登录页面").logE(TAG)
//            ARouter.getInstance()
//                .build(RouterPath.register)
//                .navigation(this)
//        }

    }

    //重写
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            RequestCode.REQUEST_PERMISSION -> {
                if(grantResults.isEmpty()){
                    return
                }
                grantResults.forEach { element->
                    if (element != PackageManager.PERMISSION_GRANTED){
                        showToast("您有未授予的权限，可能影响使用")
                        return@forEach
                    }
                }
                binding.ivLauncher.startAnimation(alphaAnimation)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}