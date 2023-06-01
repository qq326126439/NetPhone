package com.lx.net.login.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lx.net.base.activity.BaseVmActivity
import com.lx.net.base.ext.vmObserver
import com.lx.net.common.constant.Constants
import com.lx.net.common.constant.ParamKey
import com.lx.net.common.utils.LogCompat.logE
import com.lx.net.common.utils.StringUtil
import com.lx.net.common.vb.viewBinding
import com.lx.net.local.MMKVUtil
import com.lx.net.login.R
import com.lx.net.login.databinding.ActivityRegisterBinding
import com.lx.net.login.viewmodel.LoginViewModel
import com.lx.net.router.RouterPath

@Route(path = RouterPath.register)
class RegisterActivity : BaseVmActivity() {


    // 0 : 登录,  1 : 注册
    @Autowired(name = ParamKey.EXTRA_LOGIN_OR_REGISTER)
    @JvmField
    var loginOrRegister: Int = 1


    private val binding by viewBinding(ActivityRegisterBinding::inflate)
    private val viewModel by viewModels<LoginViewModel>()

    private var mInputEmail: String? = null
    private var mInputVerifyCode: String? = null
    private var mInputInviteCode: String? = null
    private var curCodeType = -1

    private var countDownTimer: CountDownTimer? = null

    //是否勾中協議
    private var isAgreement: Boolean = false

    private var mInVideCode: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)

        if (loginOrRegister != -1){

            if (loginOrRegister == 0){
                changeUi(true)
            } else {
                changeUi(false)
            }
            curCodeType = loginOrRegister
        }
    }


    override fun createObserver() {


        //接收验证码请求
        viewModel.sendCode.vmObserver(this) {
            onLoading {
                showLoadingDialog()
            }
            onSuccess {
                hideLoadingDialog()
                showToast("验证码为$it,"+resources.getString(R.string.str_code_time))
                startCountDown()
                mInVideCode = it
            }
            onError {
                hideLoadingDialog()
                showToast(it.errorMsg)
            }
        }

        //注册用户请求
        viewModel.userRegister.vmObserver(this) {
            onLoading {
                showLoadingDialog()
            }
            onSuccess {
                hideLoadingDialog()
                showToast("注册成功")
                MMKVUtil.write(Constants.USER_TOKEN, it?.token)
                (" result  : ${it?.token}").logE()
                //更换为登录ui
                changeUi(true)
            }
            onError {
                hideLoadingDialog()
                showToast(it.errorMsg)
            }
        }

        //注册用户请求
        viewModel.userLogin.vmObserver(this) {
            onLoading {
                showLoadingDialog()
            }
            onSuccess {
                hideLoadingDialog()
                (" result  : ${it?.token}").logE()
                MMKVUtil.write(Constants.USER_TOKEN, it?.token)
               //跳到主页面
                ARouter.getInstance().build(RouterPath.mainMenu).navigation(this@RegisterActivity)
                finish()
            }
            onError {
                hideLoadingDialog()
                showToast(it.errorMsg)
            }
        }

    }

    private fun changeUi(isLogin: Boolean){

        countDownTimer?.cancel()
        binding.etEmail.text.clear()
        binding.etVerify.text.clear()
        binding.etVerifyCode.text.clear()
        mInputEmail = null
        mInputVerifyCode = null
        mInputInviteCode = null
        binding.tvGetVerifyCode.apply {
            text = resources.getString(R.string.str_get_verify_code)
            setTextColor(resources.getColor(R.color.green_08CA64))
            isEnabled = true
        }

        if (isLogin){
            curCodeType = 0
            binding.tvRegisterText.text = resources.getString(R.string.str_login)
            binding.ctl3Layout.visibility = View.INVISIBLE
            binding.btnRegisterNewAccount.background = resources.getDrawable(R.drawable.shape_btn_green_bg_8)
            binding.tvGetCodeHow.visibility = View.INVISIBLE
            binding.btnRegisterNewAccount.text = resources.getString(R.string.str_direct_login)
            binding.tvHasAccountYesOrNo.text = resources.getString(R.string.str_no_account)
            binding.tvLoginOrRegister.text = resources.getString(R.string.str_to_register)
            binding.llAgreeXx.visibility = View.INVISIBLE
        } else {
            curCodeType = 1
            binding.tvRegisterText.text = resources.getString(R.string.str_register)
            binding.ctl3Layout.visibility = View.VISIBLE
            binding.btnRegisterNewAccount.background = resources.getDrawable(R.drawable.shape_btn_blue_bg_8)
            binding.tvGetCodeHow.visibility = View.VISIBLE
            binding.btnRegisterNewAccount.text = resources.getString(R.string.str_register_new_account)
            binding.tvHasAccountYesOrNo.text = resources.getString(R.string.str_has_account)
            binding.tvLoginOrRegister.text = resources.getString(R.string.str_direct_login)
            binding.llAgreeXx.visibility = View.VISIBLE
        }
    }

    private fun startCountDown(){

        countDownTimer = object : CountDownTimer(60 * 1000,1000){//1000ms运行一次onTick里面的方法
        override fun onFinish() {

            runOnUiThread {
                binding.etVerify.text.clear()
                binding.tvGetVerifyCode.apply {
                    text = resources.getString(R.string.str_get_verify_code)
                    setTextColor(resources.getColor(R.color.green_08CA64))
                    isEnabled = true
                }
            }
        }

            override fun onTick(millisUntilFinished: Long) {

                var second=millisUntilFinished/1000%60
                Log.d(TAG,"==倒计时"+ second+"秒")

                runOnUiThread {
                    binding.tvGetVerifyCode.apply {
                        text = "$second 秒后重新获取"
                        setTextColor(resources.getColor(R.color.text_color_7b7b7b))
                        isEnabled = false
                    }
                }
            }
        }

        countDownTimer?.start()
    }

    override fun bindEvent() {
        super.bindEvent()


        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                ("mInputEmail.toString() ${s.toString()}").logE()

                Log.e("parry", "mInputEmail.toString() ${s.toString()}")
                mInputEmail = s.toString()
            }

        })

        binding.etVerify.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                mInputVerifyCode = s.toString()
            }

        })

        binding.etVerifyCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                mInputInviteCode = s.toString()
            }

        })

        binding.tvGetVerifyCode.setOnClickListener {

            if (mInputEmail == null) {
                showToast("请输入邮箱号")
                return@setOnClickListener
            }

            if (!StringUtil.validateEmail(mInputEmail!!)) {
                showToast("请输入正确的邮箱号")
                return@setOnClickListener
            }

            viewModel.getVerifyCode(curCodeType, mInputEmail!!)

        }

        binding.btnRegisterNewAccount.setOnClickListener {

            if (binding.btnRegisterNewAccount.text == resources.getString(R.string.str_register_new_account)) {
                if (!isAgreement){
                    showToast("请勾选底部协议")
                    return@setOnClickListener
                }
                if (mInputVerifyCode == null) {
                    showToast("请填写验证码")
                    return@setOnClickListener
                }

                viewModel.userRegister(mInputEmail!!, "", mInputVerifyCode!!)
            } else {

                if (mInputEmail == null) {
                    showToast("请输入邮箱号")
                    return@setOnClickListener
                }

                if (!StringUtil.validateEmail(mInputEmail!!)) {
                    showToast("请输入正确的邮箱号")
                    return@setOnClickListener
                }

                if (mInputVerifyCode == null) {
                    showToast("请填写验证码")
                    return@setOnClickListener
                }

                viewModel.userLogin(mInputEmail!!, mInputVerifyCode!!)
            }
        }

        binding.ivAgreement.setOnClickListener {

            isAgreement = !isAgreement
            if (isAgreement) {
                binding.ivAgreement.background = resources.getDrawable(R.mipmap.round_select)
            } else {
                binding.ivAgreement.background = resources.getDrawable(R.mipmap.round_un_select)
            }
        }

        binding.tvLoginOrRegister.setOnClickListener {


            if (binding.tvLoginOrRegister.text == resources.getString(R.string.str_direct_login)){
                changeUi(true)
            } else {
                changeUi(false)
            }
        }
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        countDownTimer = null
        super.onDestroy()
    }
}