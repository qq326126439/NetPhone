package com.lx.net.netphone.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lx.net.base.activity.BaseVmActivity
import com.lx.net.common.utils.LogCompat.logE
import com.lx.net.common.vb.viewBinding
import com.lx.net.netphone.ChatClient
import com.lx.net.netphone.R
import com.lx.net.netphone.databinding.ActivityMainMenuBinding
import com.lx.net.router.RouterPath
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.lang.Exception


@Route(path = RouterPath.mainMenu)
class MainMenuActivity : BaseVmActivity() {

    companion object{
        const val MAIN_VIEW = "main_view"
    }

    private val binding by viewBinding(ActivityMainMenuBinding::inflate)
    private var msgFragment: Fragment? = null
    private var contactFragment: Fragment? = null
    private var findFragment: Fragment? = null
    private var settingFragment: Fragment? = null
    private val FRAGMENT_TAG = arrayOf(
        "MsgFragment",
        "ContactFragment",
        "FindFragment",
        "SettingFragment"
    )
    private var lastClickTime = 0L
    private val TAB_SELECTED = "tab_selected"
    private var indexSelected = 0x00
    private val TAB_MSG = 0x01
    private val TAB_CONTACT = TAB_MSG + 1
    private val TAB_Find = TAB_CONTACT + 1
    private val TAB_SETTING = TAB_Find + 1
    private var exitTime = 0L

    private val wic by lazy { ViewCompat.getWindowInsetsController(window.decorView) }

    private var mSocket: Socket? = null



    private val onConnectEvent = Emitter.Listener {
        runOnUiThread {

            it.forEach { listen ->
                ("it == $listen").logE()
            }

            ("it ==  onConnectEvent").logE()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
//        ChatClient.initClient()



            if (mSocket == null){
                try {
                    mSocket = IO.socket("ws://192.168.10.45:9527/chat");
                } catch (e: Exception){
                    ("error  ${e.message}").logE()
                }

            }
            mSocket?.on(Socket.EVENT_CONNECT, onConnectEvent);
            mSocket?.connect()

        initFragment(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_SELECTED, indexSelected)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (savedInstanceState != null) {
            // 读取上一次界面Save时候tab选中的状态״̬
            indexSelected = savedInstanceState.getInt(TAB_SELECTED, indexSelected)
            msgFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[TAB_MSG - 1])
            contactFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[TAB_CONTACT - 1])
            findFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[TAB_Find - 1])
            settingFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[TAB_SETTING - 1])
            msgFragment?.let { fragmentTransaction.hide(it) }
            contactFragment?.let { fragmentTransaction.hide(it) }
            findFragment?.let { fragmentTransaction.hide(it) }
            settingFragment?.let { fragmentTransaction.hide(it) }
            fragmentTransaction.commitAllowingStateLoss()
        } else {
            indexSelected = TAB_CONTACT
        }
        checkFragment(indexSelected)
    }

    /**
     * 选择选中的fragment
     *
     * @param index fragment index
     */
    private fun checkFragment(index: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (index) {
            TAB_MSG -> {
                if (msgFragment == null) {
                    msgFragment = ARouter.getInstance()
                        .build(RouterPath.messageFragment)
                        .navigation() as Fragment?
                    msgFragment?.let {
                        fragmentTransaction.add(
                            R.id.fragment_content, it,
                            FRAGMENT_TAG[TAB_MSG - 1]
                        )
                    }
                } else {
                    fragmentTransaction.show(msgFragment!!)
                }
                setIndex(TAB_MSG, fragmentTransaction)
                fragmentTransaction.commitAllowingStateLoss()
//                binding.mainNav.setBackgroundColor(getColor(R.color.white))
            }
            TAB_CONTACT -> {
                if (contactFragment == null) {
                    contactFragment = ARouter.getInstance()
                        .build(RouterPath.contactFragment)
                        .navigation() as Fragment?
                    contactFragment?.let {
                        fragmentTransaction.add(
                            R.id.fragment_content, it,
                            FRAGMENT_TAG[TAB_CONTACT - 1]
                        )
                    }
                } else {
                    fragmentTransaction.show(contactFragment!!)
                }
                setIndex(TAB_CONTACT, fragmentTransaction)
                fragmentTransaction.commitAllowingStateLoss()
//                binding.mainNav.setBackgroundColor(getColor(R.color.white))
            }
            TAB_Find -> {
                if (findFragment == null) {
                    findFragment = ARouter.getInstance()
                        .build(RouterPath.findFragment)
                        .navigation() as Fragment?
                    findFragment?.let {
                        fragmentTransaction.add(
                            R.id.fragment_content, it,
                            FRAGMENT_TAG[TAB_Find - 1]
                        )
                    }
                } else {
                    fragmentTransaction.show(findFragment!!)
                }
                setIndex(TAB_Find, fragmentTransaction)
                fragmentTransaction.commitAllowingStateLoss()
//                binding.mainNav.setBackgroundColor(getColor(R.color.white))
            }
            TAB_SETTING -> {
                if (settingFragment == null) {
                    settingFragment = ARouter.getInstance()
                        .build(RouterPath.settingFragment)
                        .navigation() as Fragment?
                    settingFragment?.let {
                        fragmentTransaction.add(
                            R.id.fragment_content, it,
                            FRAGMENT_TAG[TAB_SETTING - 1]
                        )
                    }
                } else {
                    fragmentTransaction.show(settingFragment!!)
                }
                setIndex(TAB_SETTING, fragmentTransaction)
                fragmentTransaction.commitAllowingStateLoss()
//                binding.mainNav.setBackgroundColor(getColor(R.color.main_bg_color))
            }
        }
    }

    /** @param tab 设置下标 */
    private fun setIndex(tab: Int, fragmentTransaction: FragmentTransaction) {
        lastClickTime = if (indexSelected != tab) { // 隐藏上次展示的fragment
            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[indexSelected - 1])
                ?.let { fragmentTransaction.hide(it) }
            0L
        } else {
            if (lastClickTime != 0L) {
                if (System.currentTimeMillis() - lastClickTime <= 500)
                    0L
                else
                    System.currentTimeMillis()
            } else {
                System.currentTimeMillis()
            }
        }
        // 更新当前展示的下标
        indexSelected = tab
    }

    override fun createObserver() {

        LiveEventBus.get<Boolean>(MAIN_VIEW).observe(this){ showOrGone ->
            if (showOrGone){

            } else {

            }
        }
    }

    override fun bindEvent() {
        super.bindEvent()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.menu_msg ->
                    checkFragment(TAB_MSG)
                R.id.menu_contact ->
                    checkFragment(TAB_CONTACT)
                R.id.menu_find ->
                    checkFragment(TAB_Find)
                R.id.menu_setting ->
                    checkFragment(TAB_SETTING)
            }
            true
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            showToast("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            finish()
            System.exit(0);
        }
    }

}