package com.lx.net.base.ext

import android.app.Activity
import android.app.Application
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.lx.net.base.R
import java.util.*


/**
 * @author : lxm
 * @version ：
 * @package_name ：com.lx.net.base.ext
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/22 19:36
 * @description ：
 */
@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.applicationViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null,
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }
    return ViewModelLazy(VM::class, { application.viewModelStore }, factoryPromise)
}


@MainThread
inline fun <reified VM : ViewModel> Fragment.applicationViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null,
): Lazy<VM> = createViewModelLazy(VM::class,
    { requireActivity().application.viewModelStore },
    factoryProducer)


val Application.viewModelStore: ViewModelStore by lazy {
    ViewModelStore()
}

//fun Activity.immerseNavigationBar(@ColorRes barColor : Int, @FloatRange(from = 0.0, to = 1.0)alpha :Float){
//    immersionBar {
//        transparentNavigationBar()
//        if (barColor != -1) {
//            navigationBarColor(barColor)
//        }
//        navigationBarAlpha(alpha)
//    }
//}
//
//fun Activity.immerseNavigationBar(){
//    immerseNavigationBar(-1, 0F)
//}
//
//fun Activity.immerseStatusBar(@ColorRes barColor : Int, @FloatRange(from = 0.0, to = 1.0)alpha :Float){
//    immersionBar {
//        transparentStatusBar()
//        if (barColor != -1) {
//            statusBarColor(barColor)
//        }
//        statusBarAlpha(alpha)
//    }
//}
//
//fun Activity.immerseStatusBar(){
//    immerseStatusBar(-1, 0F)
//}

fun Activity.timePickerBuild(
    type: BooleanArray,
    listener: ((content: Date, v: View?) -> Unit)? = null,
): TimePickerView {

    //时间选择器当前选择时间，起始选择时间，终止选择时间
    val now = Calendar.getInstance()
    val startDate = Calendar.getInstance()
    val endDate = Calendar.getInstance()

    //正确设置方式 原因：注意事项有说明
    //startDate.set(startDate.get(Calendar.YEAR), Calendar.MONTH, Calendar.DAY_OF_MONTH)
    endDate.set(2099, 11, 31)

    val pvTime = TimePickerBuilder(this
    ) { date, v ->
        //listener.onTimeSelect(date,v)
        listener?.invoke(date, v)
    }
        .setType(type)
        .setCancelText("取消")//取消按钮文字
        .setSubmitText("确定")//确认按钮文字
        .setSubCalSize(15)
        .setContentTextSize(15)//滚轮文字大小
        .setTitleSize(15)//标题文字大小
        .setTitleText("选择日期")//标题文字
        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
        .isCyclic(true)//是否循环滚动
        .setTitleColor(this.resources.getColor(R.color.text_color_1, null))//标题文字颜色
        .setSubmitColor(this.resources.getColor(R.color.main_blue, null))//确定按钮文字颜色
        .setCancelColor(this.resources.getColor(R.color.text_color_2, null))//取消按钮文字颜色
        .setTitleBgColor(this.resources.getColor(R.color.main_white, null))//标题背景颜色 Night mode
        .setBgColor(this.resources.getColor(R.color.main_white, null))//滚轮背景颜色 Night mode
        // 如果不设置的话，默认是系统时间*/
        //.setRangDate(startDate,endDate)//起始终止年月日设定
        .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        .isDialog(false)//是否显示为对话框样式
        .build()

    pvTime.setDate(now)
    return pvTime
}

/**
 * 构建单个滚轮选择器
 *
 * @param T
 * @param titleTxt 标题文字
 * @param selectIndex 选中下标
 * @param optionsItems 滚轮数据，如果不是string类型，需要实体实现 IPickerViewData接口，参考PopupBean实体
 * @param listener 点击事件回调
 * @return
 */
fun <T : Any?> Activity.buildSingleOptionsPickerView(
    titleTxt: String,
    selectIndex: Int,
    optionsItems: List<T>,
    listener: OnOptionsSelectListener,
): OptionsPickerView<T> {
    var pvOptions  : OptionsPickerView<T> ?= null
    pvOptions = OptionsPickerBuilder(this, listener)
        .setLayoutRes(R.layout.layout_picker_three) {
            //自定义布局中的控件初始化及事件处理
            val tvSubmit = it.findViewById<Button>(R.id.btnSubmit)
            val tvCancel = it.findViewById<Button>(R.id.btnCancel)
            tvSubmit.setOnClickListener {
                pvOptions?.returnData()
                pvOptions?.dismiss()
            }
            tvCancel.setOnClickListener { pvOptions?.dismiss() }
        }
        .build()
    pvOptions.setTitleText(titleTxt)
    pvOptions.setPicker(optionsItems)
    pvOptions.setSelectOptions(selectIndex)
    return pvOptions
}

/**
 * 构建单个滚轮选择器
 *
 * @param T
 * @param titleTxt 标题文字
 * @param selectIndex 选中下标
 * @param optionsItems 滚轮数据，如果不是string类型，需要实体实现 IPickerViewData接口，参考PopupBean实体
 * @param listener 点击事件回调
 * @return
 */
fun <T : Any?> Fragment.buildSingleOptionsPickerView(
    titleTxt: String,
    selectIndex: Int,
    optionsItems: List<T>,
    listener: OnOptionsSelectListener,
): OptionsPickerView<T> {
    var pvOptions  : OptionsPickerView<T> ?= null
    pvOptions = OptionsPickerBuilder(requireContext(), listener)
        .setLayoutRes(R.layout.layout_picker_three) {
            val btnSubmit = it.findViewById<Button>(R.id.btnSubmit)
            val btnCancel = it.findViewById<Button>(R.id.btnCancel)
            //自定义布局中的控件初始化及事件处理
            btnSubmit.setOnClickListener {
                pvOptions?.returnData()
                pvOptions?.dismiss()
            }
            btnCancel.setOnClickListener { pvOptions?.dismiss() }
        }
        .build()
    pvOptions.setTitleText(titleTxt)
    pvOptions.setPicker(optionsItems)
    pvOptions.setSelectOptions(selectIndex)
    return pvOptions
}

