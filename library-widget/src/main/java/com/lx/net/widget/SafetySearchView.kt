package com.lx.net.widget

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lx.net.common.databinding.SafetySearchViewBinding
import com.lx.net.common.ext.delayClick
import com.lx.net.common.ext.goneRightToViewAnim
import com.lx.net.common.ext.visibleViewToLeftAnim

/**
 * @author : pengl
 * @version ： @package_name ：cc.crrcgc.hse.login.api
 * @org ： 深圳赛为安全技术服务有限公司
 * @date :2022/8/29 15:11
 * @description ：
 */
class SafetySearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = SafetySearchViewBinding.inflate(LayoutInflater.from(context), this, true)

    private val mEditText: EditText = binding.etSearch
    private val mIvEndText: ImageView = binding.ivEndText
    private var isShowCancel = false

    /** 输入框监听器 */
    private var textWatcher: TextWatcher? = null
    private var myTextWatcher: TextWatcher? = null

    /** 选中监听 */
    private var selectListener: (() -> Unit)? = null

    init {
        setTextWatcher()
        initEvent()
    }

    /** 设置监听器 */
    private fun setTextWatcher() {
        mEditText.removeTextChangedListener(textWatcher)
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                myTextWatcher?.beforeTextChanged(charSequence,i,i1,i2)
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                myTextWatcher?.onTextChanged(charSequence,i,i1,i2)
            }
            override fun afterTextChanged(editable: Editable) {
                myTextWatcher?.afterTextChanged(editable)
                if (editable.toString().isNotEmpty()) {
                    if (!isShowCancel) {
                        mIvEndText.visibleViewToLeftAnim(200L)
                        isShowCancel = true
                    }
                } else {
                    if (isShowCancel) {
                        mIvEndText.goneRightToViewAnim(200L)
                    }
                    isShowCancel = false
                }
            }
        }
        mEditText.addTextChangedListener(textWatcher)
    }

    fun setOnEditorActionListener(l: TextView.OnEditorActionListener) {
        mEditText.setOnEditorActionListener(l)
    }

    fun setMyTextWatcher(watcher: TextWatcher?){
        myTextWatcher = watcher
    }

    /**
     * 设置监听
     *
     * @param listener
     * @receiver
     */
    fun setSelectListener(listener: () -> Unit) {
        this.selectListener = listener
    }

    fun setContent(content: String?) {
        mEditText.setText(content ?: "")
    }

    fun clearMyFocus() {
        mEditText.clearFocus()
    }

    /** 初始化事件 */
    private fun initEvent() {
        mIvEndText.delayClick(
            {
                selectListener?.invoke()
            }
        )

        mEditText.viewTreeObserver.addOnGlobalLayoutListener {
                val r = Rect()
                mEditText.getWindowVisibleDisplayFrame(r)
                val screenHeight: Int = mEditText.rootView.height
                val heightDifference: Int = screenHeight - r.bottom
                if (heightDifference > 200) {
                    //软键盘显示
                    //("mIsSoftKeyboardShowing 显示").logI("SafetySearchView")
                } else {
                    //软键盘隐藏
                    mEditText.clearFocus()
                    //(" mIsSoftKeyboardShowing 隐藏").logI("SafetySearchView")
                }
            }
    }
}