package com.lx.net.widget.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.lx.net.widget.R

/**
 * @author : lxm
 * @version ：
 * @package_name ：cc.crrcgc.hse.widget.recyclerview
 * @org ：深圳赛为安全技术服务有限公司
 * @date : 2022/6/19 15:33
 * @description ：
 */
class MaxHeightRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr) {

    private var mMaxHeight = 0

    init {
      initialize(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initialize(
        context: Context,
        attrs: AttributeSet?
    ) {
        val arr =
            context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView)
        mMaxHeight = arr.getLayoutDimension(R.styleable.MaxHeightRecyclerView_maxHeight, mMaxHeight)
        arr.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpecs = heightMeasureSpec
        if (mMaxHeight > 0)
            heightMeasureSpecs = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightMeasureSpecs)
    }

}
