package com.udacity

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import kotlinx.android.synthetic.main.content_main.view.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates
import androidx.core.content.withStyledAttributes as withStyledAttributes1
import androidx.core.content.withStyledAttributes as withStyledAttributes2

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var background_Color = 0
    private var text_Color = 0
    private var widthSize = 0
    private var heightSize = 0
    private var pointPosition = PointF(0f, 0f)
    private var valueAnimator = ValueAnimator()
    private var position = 0f
    private var markerRadius = 0f
    val mText = "Downlod"
    val bound = Rect()
    val path = Path()
    var currentX = 0f
    var currentY = 0f
    private var dx = 0f
    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }

    init {
        isClickable = true
        context.withStyledAttributes2(attrs, R.styleable.LoadingButton) {
            background_Color = getColor(R.styleable.LoadingButton_backgroundColor, 0)
            text_Color = getColor(R.styleable.LoadingButton_textColor, 0)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_DOWN) {
            markerRadius = heightSize.toFloat() / 4
            position = 1f
            pointPosition.calculateXY(position, markerRadius)
            path.reset()
            path.moveTo(pointPosition.x, pointPosition.y)
            currentX = pointPosition.x
            currentY = pointPosition.y
        }
        animateFun()
        return true
    }

    private val paint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = 70F
    }

    private fun PointF.calculateXY(position: Float, radius: Float) {
        paint.getTextBounds(mText, 0, mText.length, bound)
        val textWidth = bound.width()
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + position * (Math.PI / 360)
        x = (radius * cos(angle)).toFloat() + (width + textWidth) / 2 + radius
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    fun animateFun() {
        valueAnimator.apply {
            setFloatValues(0f, 1f)
            duration = 3000
            reverse()
            interpolator = LinearInterpolator()
            addUpdateListener {
                dx = it.animatedValue as Float
                position += 1.0f
                invalidate()
            }
            start()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(background_Color)
        val x = widthSize.toFloat() / 2
        val y = (heightSize.toFloat() + bound.height()) / 2

        paint.color = context.getColor(R.color.colorPrimaryDark)
        canvas.drawRect(0f, 0f, widthSize * dx, heightSize.toFloat(), paint)

        pointPosition.calculateXY(position, markerRadius)
        path.quadTo(
            currentX,
            currentY,
            pointPosition.x,
            pointPosition.y
        )
        paint.color = Color.YELLOW
        canvas.drawPath(path, paint)
        if (dx == 1f) {
            canvas.drawColor(background_Color)
            path.reset()
        }
        paint.color = text_Color
        canvas.drawText(mText, x, y, paint)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}