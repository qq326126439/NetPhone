package com.lx.net.common.ext

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.postDelayed
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private const val JOB_KEY = "ViewExt.JOB_KEY"
/**
 * 配合 implementation 'androidx.core:core-ktx:1.3.2' View.isGone()
 * View.isInvisible() View.isVisible()
 */

/**
 * 占位隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.invisibleAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.INVISIBLE
    this?.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}

fun RecyclerView.getHorizontalLayoutManager() =
    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

/**
 * 隐藏view 从底部的位置向view消失
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.goneDownToViewAnim(duration: Long = 500L) {
    this?.visibility = View.GONE
    this?.startAnimation(TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 1.0f
    ).apply {
        this.duration = duration
        fillAfter = false
    })
}

/**
 * 隐藏view 从底部的位置向view消失
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.goneRightToViewAnim(duration: Long = 500L) {
    this?.visibility = View.GONE
    this?.startAnimation(TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 1.0f,
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 0.0f
    ).apply {
        this.duration = duration
        fillAfter = false
    })
}

/**
 * 隐藏view 从底部的位置向view消失
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.visibleViewToLeftAnim(duration: Long = 500L) {
    this?.visibility = View.VISIBLE
    this?.startAnimation(TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 1.0f,
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 0.0f
    ).apply {
        this.duration = duration
        fillAfter = false
    })
}

/**
 * 从底部向view的位置显示
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.visibleMoveToDownAnim(duration: Long = 500L) {
    this?.visibility = View.VISIBLE
    this?.startAnimation(TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 1.0f,
        Animation.RELATIVE_TO_SELF, 0.0f
    ).apply {
        this.duration = duration
        fillAfter = false
    })

}

/**
 * 左右晃动动画
 *
 * @param xDuration
 * @param xOffset
 */
fun View?.shakeSideToSide(xDuration: Long = 100L, xOffset: Float = 100f) {
    AnimatorSet().apply {
        playSequentially(
            ObjectAnimator.ofFloat(this@shakeSideToSide,
                "translationX", -xOffset).apply {
                duration = xDuration / 2
            },
            ObjectAnimator.ofFloat(this@shakeSideToSide,
                "translationX", -xOffset, xOffset).apply {
                duration = xDuration
                repeatMode = ValueAnimator.REVERSE
                repeatCount = 2
                interpolator = AccelerateDecelerateInterpolator()
            },
            ObjectAnimator.ofFloat(this@shakeSideToSide,
                "translationX", 0f)
                .apply {
                    duration = xDuration / 2
                }
        )
    }.start()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

val String.toEditable: Editable
    get() = Editable.Factory.getInstance().newEditable(this)

val View.viewScope: CoroutineScope
    get() {
        return synchronized(this) {
            var scope = getTag(JOB_KEY.hashCode()) as? CoroutineScope
            if (scope == null) {
                scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
                setTag(JOB_KEY.hashCode(), scope)
            }
            scope
        }
    }

/**
 * Allows calls like
 *
 * `viewGroup.inflate(R.layout.foo)`
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

/**
 * Registers the [block] lambda as [View.OnClickListener] to this View.
 *
 * If this View is not clickable, it becomes clickable.
 */
inline fun View.click(crossinline block: (view: View?) -> Unit) = setOnClickListener { block(it) }

/**
 * Register the [block] lambda as [View.OnLongClickListener] to this View.
 * By default, [consume] is set to true because it's the most common
 * use case, but you can set it to false. If you want to return a value
 * dynamically, use [View.setOnLongClickListener] instead.
 *
 * If this view is not long clickable, it becomes long clickable.
 */
inline fun View.longClick(
    consume: Boolean = true,
    crossinline block: (view: View?) -> Unit,
) = setOnLongClickListener { block(it); consume }

inline fun View.delayClick(crossinline block: (view: View?) -> Unit, delayInMillis: Long = 1000L) =
    click {
        isClickable = false
        block(it)
        postDelayed(delayInMillis) {
            isClickable = true
        }
    }


/** 防止View多次点击 */
inline fun View.setOnSingleClickListener(
    intervalMill: Long = 800,
    crossinline block: ((v: View?) -> Unit),
) {
    setOnClickListener(object : View.OnClickListener {
        var last = 0L
        override fun onClick(v: View?) {
            if (System.currentTimeMillis() - last > intervalMill) {
                block(v)
                last = System.currentTimeMillis()
            }
        }
    })
}

/** 防止View多次点击 兼容点击事件设置为this的情况 */
fun View.setOnSingleClickListener(onClickListener: View.OnClickListener, intervalMill: Long = 800) {
    setOnClickListener(object : View.OnClickListener {
        var last = 0L
        override fun onClick(v: View?) {
            if (System.currentTimeMillis() - last > intervalMill) {
                onClickListener.onClick(this@setOnSingleClickListener)
                last = System.currentTimeMillis()
            }
        }
    })
}

fun CoroutineScope.countDownCoroutines(
    total: Long,
    onTick: (Long) -> Unit,
    onStart: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
): Job {
    return flow {
        for (i in total downTo 0) {
            emit(i)
            delay(1000)
        }
    }.flowOn(Dispatchers.Main)
        .onStart { onStart?.invoke() }
        .onCompletion { onFinish?.invoke() }
        .onEach { onTick.invoke(it) }
        .launchIn(this)
}

/**
 * 滑动到指定view
 *
 */
fun NestedScrollView.scrollToView(view: View, scrollY: Int) {
    post {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        var offset: Int = location[1] + scrollY - measuredHeight
        if (offset < 0) {
            offset = 0
        }
        smoothScrollTo(0, offset)
        view.shakeSideToSide()
    }
}

/**
 * 滚动时去掉焦点
 */
@SuppressLint("ClickableViewAccessibility")
fun NestedScrollView.removeFocusWhenScrolling() {
    descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
    isFocusable = true
    isFocusableInTouchMode = true
    setOnTouchListener { v, _ ->
        v.clearFocus()
        false
    }
}

inline fun <reified T : View> View.find(@IdRes id: Int): T? = findViewById(id)
inline fun <reified T : View> Activity.find(@IdRes id: Int): T? = findViewById(id)
inline fun <reified T : View> Dialog.find(@IdRes id: Int): T? = findViewById(id)
inline fun <reified T : View> View.findOptional(@IdRes id: Int): T? = findViewById(id) as? T
inline fun <reified T : View> Activity.findOptional(@IdRes id: Int): T? = findViewById(id) as? T
inline fun <reified T : View> Dialog.findOptional(@IdRes id: Int): T? = findViewById(id) as? T