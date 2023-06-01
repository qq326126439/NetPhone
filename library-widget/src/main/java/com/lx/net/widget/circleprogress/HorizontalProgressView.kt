package com.lx.net.widget.circleprogress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.lx.net.widget.R

class HorizontalProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthH: Int = 0
    private var heightH = 0
    private var bgPaint: Paint? = null
    private var proPaint: Paint? = null
    private val item_width: Float
    var bgColor: Int
    var proColor: Int
    private var temp = 0f
    private var progress = 0
    private var isUpdate = true
    private val bgRectF = RectF()
    private val proRectF = RectF()

    private fun initPaint() {
        //背景画笔
        bgPaint = Paint()
        bgPaint?.isAntiAlias = true
        bgPaint?.color = bgColor

        //进度画笔
        proPaint = Paint()
        proPaint?.isAntiAlias = true
        proPaint?.color = proColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        widthH = MeasureSpec.getSize(widthMeasureSpec)
        heightH = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(widthH, heightH)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //1、画背景
        bgRectF.left = 0f
        bgRectF.top = bgRectF.left
        bgRectF.right = widthH.toFloat()
        bgRectF.bottom = heightH.toFloat()
        canvas.drawRoundRect(bgRectF, item_width / 2, item_width / 2, bgPaint!!)

        //2、画进度
        if (progress == 0) return
        if (temp < progress) {
            temp += progress * 0.02f
            isUpdate = true
        } else {
            isUpdate = false
        }
        proRectF.left = 0f
        proRectF.top = proRectF.left
        proRectF.bottom = heightH.toFloat()
        proRectF.right = temp * widthH / 100f
        canvas.drawRoundRect(proRectF, item_width / 2, item_width / 2, proPaint!!)
        if (isUpdate) {
            invalidate()
        }
    }

    fun setProgress(progress: Int) {
        if (progress > 100) {
            this.progress = 100
        } else if (progress < 0) {
            this.progress = 0
        } else {
            this.progress = progress
        }
//        temp = 0f
        invalidate()
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressView)
        item_width = typedArray.getDimension(
            R.styleable.HorizontalProgressView_item_width,
            resources.getDimension(R.dimen.shifting_height_bottom_padding_active)
        )
        bgColor = typedArray.getColor(
            R.styleable.HorizontalProgressView_bgColor,
            context.resources.getColor(R.color.white)
        )
        proColor = typedArray.getColor(
            R.styleable.HorizontalProgressView_proColor,
            context.resources.getColor(R.color.main_blue)
        )
        typedArray.recycle()
        initPaint()
    }
}