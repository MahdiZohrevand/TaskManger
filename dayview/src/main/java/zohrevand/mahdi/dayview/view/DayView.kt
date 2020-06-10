package zohrevand.mahdi.dayview.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.*
import android.graphics.Paint.Align
import android.text.Layout
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.facebook.fbui.textlayoutbuilder.TextLayoutBuilder
import zohrevand.mahdi.customviewtest.model.CalendarTask
import zohrevand.mahdi.dayview.R
import java.util.*
import kotlin.math.max
import kotlin.math.min


/**
 *  show task in days
 */


const val SPACE_BETWEEN_LINES = 78F
const val DEFAULT_TEXT_SIZE = 12
const val DEFAULT_RECT_COLOR = Color.GRAY
const val DEFAULT_TEXT_COLOR = Color.WHITE
const val DEFAULT_LINE_COLOR = Color.GRAY
const val DEFAULT_HOUR_BAR_COLOR = Color.BLACK
const val DEFAULT_RECT_PADDING = 4f
const val DEFAULT_ALPHA = 15
/**
 * hour bar x position in container
 */
const val HOUR_BAR_X_POSITION = 0.15f //15 percent of view width
const val RIPPLE_ANIMATION_DURATION = 200L
const val AM_PM_TEXT_SIZE = 12

class DayView : View {


    private var _rectColor: Int =
        DEFAULT_RECT_COLOR
    private var _textColor: Int =
        DEFAULT_TEXT_COLOR
    private var _rectTextSize: Float = convertToDp(DEFAULT_TEXT_SIZE)
    private var _hourBarColor =
        DEFAULT_HOUR_BAR_COLOR
    private var _lineColor =
        DEFAULT_LINE_COLOR

    private var _paddingRight = 0
    private var _paddingTop = 0
    private var _paddingLeft = 0
    private var _paddingBottom = 0

    private var _rectMarginLeft = 0f
    private var _rectMarginRight = 0f

    private var _rectPaddingRight = DEFAULT_RECT_PADDING.toDp()
    private var _rectPaddingLeft = DEFAULT_RECT_PADDING.toDp()
    private var _rectPaddingTop = DEFAULT_RECT_PADDING.toDp()
    private var _rectPaddingBottom = DEFAULT_RECT_PADDING.toDp()


    private var textPaint = TextPaint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        textAlign = Align.LEFT
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val calendarItem = mutableListOf<CalendarItem>()

    private var verticalHourBarXPosition: Float = 0f
    private var horizontalLinesSeparator: FloatArray? = null
    private var amOrPmTexts: TimesOfDay? = null

    //the vertical line that separate hours segment from other part
    private var verticalLineSeparator = object {
        var startX: Float = 0f
        var endX: Float = 0f
        var startY: Float = 0f
        var endY: Float = 0f
    }


    //the line with circle that demonstrate current time
    private var horizontalHourBarLine = object {
        var startX: Float = 0f
        var endX: Float = 0f
        var startY: Float = 0f
        var endY: Float = 0f
    }

    var callBack: ((CalendarTask) -> Unit)? = null


    //gesture listener
    private val myListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            //Log.i("action", "actionDown")
            onActionDown(e)
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            //Log.i("action", "actionSingleTap")
            onSingleTape(e)
            return true
        }

    }

    private val detector: GestureDetector = GestureDetector(context, myListener)


    /**
     * The rect color
     */
    var rectColor: Int
        get() = _rectColor
        set(value) {
            _rectColor = value
            invalidate()
        }

    /**
     * The task rect font size
     */
    var rectTextSize: Float
        get() = _rectTextSize
        set(value) {
            _rectTextSize = value
            invalidate()
        }


    /**
     * The task rect font color
     */
    var textColor: Int
        get() = _textColor
        set(value) {
            _textColor = value
            invalidate()
        }

    var hourBarColor: Int
        get() = _hourBarColor
        set(value) {
            _hourBarColor = value
            invalidate()
        }

    var lineColor: Int
        get() = _lineColor
        set(value) {
            _lineColor = value
            invalidate()
        }

    var rectMarginRight: Float
        get() = _rectMarginRight
        set(value) {
            _rectMarginRight
            invalidate()
        }

    var rectMarginLeft: Float
        get() = _rectMarginLeft
        set(value) {
            _rectMarginLeft = value
            invalidate()
        }

    var rectPaddingRight: Float
        get() = _rectPaddingRight
        set(value) {
            _rectPaddingRight
            invalidate()
        }

    var rectPaddingLeft: Float
        get() = _rectPaddingLeft
        set(value) {
            _rectPaddingLeft = value
            invalidate()
        }

    var rectPaddingTop: Float
        get() = _rectPaddingTop
        set(value) {
            _rectPaddingTop = value
            invalidate()
        }

    var rectPaddingBottom: Float
        get() = _rectPaddingBottom
        set(value) {
            _rectPaddingBottom = value
            invalidate()
        }


    private fun Float.toDp(): Float {
        return this@DayView.getDensity() * this
    }

    private fun Int.toDp(): Float {
        return this@DayView.getDensity() * this
    }

    //constructors
    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }


    //initialize
    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.DayView, defStyle, 0
        )


        //get rect color from xml
        _rectColor = a.getColor(
            R.styleable.DayView_rectColor,
            rectColor
        )


        //get rect text size from xml
        //Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        //values that should fall on pixel boundaries.
        _rectTextSize = a.getDimension(
            R.styleable.DayView_textSize,
            rectTextSize
        )

        //get rect text color from xml
        _textColor = a.getColor(
            R.styleable.DayView_rectTextColor,
            textColor
        )

        _hourBarColor = a.getColor(
            R.styleable.DayView_hourBarColor,
            hourBarColor
        )

        _lineColor = a.getColor(
            R.styleable.DayView_lineColor,
            lineColor
        )

        _rectMarginRight = a.getDimension(
            R.styleable.DayView_rectMarginRight
            , rectMarginRight
        )

        _rectMarginLeft = a.getDimension(
            R.styleable.DayView_rectMarginLeft
            , rectMarginLeft
        )

        _rectPaddingRight = a.getDimension(
            R.styleable.DayView_rectPaddingRight,
            rectPaddingRight
        )

        _rectPaddingLeft = a.getDimension(
            R.styleable.DayView_rectPaddingLeft,
            rectPaddingLeft
        )

        _rectPaddingTop = a.getDimension(
            R.styleable.DayView_rectPaddingTop,
            rectPaddingTop
        )

        _rectPaddingBottom = a.getDimension(
            R.styleable.DayView_rectPaddingBottom,
            rectPaddingBottom
        )

        //free resource
        a.recycle()

        textPaint.textSize = rectTextSize


    }


    /**
     * handle Perform click in [onSingleTape] method
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return detector.onTouchEvent(event)
    }

    private fun onSingleTape(event: MotionEvent) {
        calendarItem.forEach {
            if (it.isClicked(event.x, event.y)) {
                performClick()
                callBack?.let { callBack ->
                    callBack(it.task)
                }
                return
            }

        }
    }


    private fun onActionDown(event: MotionEvent) {
        calendarItem.forEach {
            if (it.isClicked(event.x, event.y)) {
                startRippleAnimation(it, event.x, event.y)
                return
            }

        }
    }

    private fun startRippleAnimation(
        item: CalendarItem,
        x: Float,
        y: Float
    ) {
        item.startAnimation(x, y)

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        horizontalLinesSeparator?.let {
            canvas.drawLines(it, paint.apply {
                color = _lineColor
                strokeWidth = convertToDp(1f)
            })
        }
        verticalLineSeparator.let {
            canvas.drawLine(
                it.startX,
                it.startY,
                it.endX,
                it.endY,
                paint.apply { color = _lineColor })
        }

        amOrPmTexts?.show(canvas)

        calendarItem.forEach {
            it.draw(canvas)
        }

        horizontalHourBarLine.apply {
            canvas.drawLine(startX, startY, endX, endY, paint.apply {
                color = _hourBarColor
                strokeWidth = convertToDp(1f)
            })
            canvas.drawCircle(startX, startY, convertToDp(7f), paint)
        }


    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed) {
            createViews(paddingLeft, paddingRight, paddingBottom, paddingTop, width, height)
        } else if (checkPaddingIsChanged()) {
            createViews(paddingLeft, paddingRight, paddingBottom, paddingTop, width, height)
        }

    }

    private fun checkPaddingIsChanged(): Boolean {
        if (_paddingRight != paddingRight ||
            _paddingLeft != paddingLeft ||
            _paddingTop != paddingTop ||
            _paddingBottom != paddingBottom
        ) {
            return true
        }
        return false

    }

    /**
     * create basic view with given padding and width & height
     * basic view contain vertical line (hour bar) and horizontal lines (hour separator)
     * and their padding
     */
    private fun createViews(
        paddingLeft: Int,
        paddingRight: Int,
        paddingBottom: Int,
        paddingTop: Int,
        w: Int,
        h: Int,
        isRtl: Boolean = true
    ) {


        _paddingLeft = paddingLeft
        _paddingRight = paddingRight
        _paddingBottom = paddingBottom
        _paddingTop = paddingTop

        //create vertical hour bar
        //==================================
        verticalHourBarXPosition = if (isRtl) {
            w * (1 - HOUR_BAR_X_POSITION)
        } else {
            w * HOUR_BAR_X_POSITION
        }
        verticalHourBarXPosition += if (isRtl) {
            paddingRight
        } else {
            paddingLeft
        }



        //create horizontal lines separator
        //=================================
        horizontalLinesSeparator = if (isRtl) {
            createLines(verticalHourBarXPosition, 0f + paddingLeft, 23)
        } else {
            createLines(verticalHourBarXPosition, (w - paddingRight).toFloat(), 23)
        }


        //=================================
        amOrPmTexts = TimesOfDay()

        //change calendar item padding
        //=================================
        calendarItem.forEach {
            it.width = if (isRtl) {
                w - paddingRight
            } else {
                w - paddingRight
            }
        }

        //=================================
        verticalLineSeparator.apply {
            startY = 0f
            startX = verticalHourBarXPosition
            endX = verticalHourBarXPosition
            endY = h.toFloat()
        }

        //=================================
        createHorizontalHourBar(w, isRtl)

        /* horizontalHourBarLine.apply {
             startY = calculateHourBarVerticalPosition()
             startX = verticalHourBarXPosition
             endX = if (isRtl) {
                 0f + paddingLeft
             } else {
                 w.toFloat()
             }
             endY = calculateHourBarVerticalPosition()
         }*/
    }


    private fun createHorizontalHourBar(width: Int, isRtl: Boolean) {
        val yPosition = calculateHourBarVerticalPosition()
        horizontalHourBarLine.apply {
            startY = yPosition
            startX = verticalHourBarXPosition
            endX = if (isRtl) {
                0f + paddingLeft
            } else {
                width.toFloat()
            }
            this.endY = yPosition
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Log.v("Chart onMeasure w", MeasureSpec.toString(widthMeasureSpec))
        // Log.v("Chart onMeasure h", MeasureSpec.toString(heightMeasureSpec))

        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        setMeasuredDimension(
            measureDimension(desiredWidth, widthMeasureSpec),
            measureDimension(desiredHeight, heightMeasureSpec)
        )
    }


    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = calculateHeight()
            if (specMode == MeasureSpec.AT_MOST) {
                result = max(result, specSize)
            }
        }
        if (result < desiredSize) {
            Log.e("ChartView", "The view is too small, the content might get cut")
        }
        return result
    }


    private fun getDensity(): Float {
        return resources.displayMetrics.density
    }

    private fun convertToDp(value: Float): Float = value * getDensity()


    private fun convertToDp(value: Int): Float = value * getDensity()


    @Suppress("SameParameterValue")
    private fun createLines(startX: Float, endX: Float, count: Int): FloatArray {
        val floatArray = FloatArray(count * 4)
        val spaceBetweenLine = convertToDp(SPACE_BETWEEN_LINES)
        var space = 0f
        for (i in 0 until floatArray.count() step 4) {
            space += spaceBetweenLine
            floatArray[i] = startX  //start x
            floatArray[i + 1] = space  //start y
            floatArray[i + 2] = endX //end x
            floatArray[i + 3] = space //end y
        }
        return floatArray

    }


    private fun calculateHeight(): Int {
        val height = 24 * convertToDp(SPACE_BETWEEN_LINES)
        return height.toInt()
    }


    fun addCalendarTask(item: CalendarTask) {
        calendarItem.add(
            CalendarItem(
                item,
                rectTextSize.toInt(),
                getDensity()
            )
        )
        invalidate()

    }

    fun addCalendarTask(items: List<CalendarTask>) {
        items.forEach {
            calendarItem.add(
                CalendarItem(
                    it,
                    rectTextSize.toInt(),
                    getDensity()
                )
            )
        }
        invalidate()
    }

    /**
     * this must be called after the added item(s) is changed
     */
    fun updateView() {
        invalidate()
    }


    fun setOnItemClickListener(callBack: (item: CalendarTask) -> Unit) {
        this.callBack = callBack
    }


    //================================================== timer and hour bar position segment

    private var mTime: Calendar? = null

    private fun calculateHourBarVerticalPosition(): Float {
        val calendar = mTime ?: Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY].toFloat()
        val minute = calendar[Calendar.MINUTE].toFloat()
        val spaceBetweenLine = convertToDp(SPACE_BETWEEN_LINES)
        return (hour + (minute / 60)) * spaceBetweenLine
    }


    private fun changeHourBarPosition() {
        createHorizontalHourBar(width, true)
        invalidate()
    }


    //we register two broad cast receiver
    //time zone change : called when time zone changed and we update hour bar position
    //time tick : called every minute and we update hour bar position
    private val mIntentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (Intent.ACTION_TIMEZONE_CHANGED == intent.action) {
                val tz = intent.getStringExtra("time-zone")
                mTime = Calendar.getInstance(TimeZone.getTimeZone(tz))
                onTimeChanged()
            }
            if (intent.action?.compareTo(Intent.ACTION_TIME_TICK) == 0) {
                onTimeChanged()
            }

        }
    }


    private fun onTimeChanged() {
        mTime?.let {
            it.timeInMillis = System.currentTimeMillis()
        }
        changeHourBarPosition()
        invalidate()
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_TIME_TICK)
        filter.addAction(Intent.ACTION_TIME_CHANGED)
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        context.registerReceiver(mIntentReceiver, filter)
        mTime = Calendar.getInstance(TimeZone.getDefault())
        onTimeChanged()

    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        context.unregisterReceiver(mIntentReceiver)
    }


    //=================================================== inner class

    private inner class CalendarItem(
        val task: CalendarTask,
        private val textSize: Int,
        private val density: Float
    ) {

        private var animatorSet: AnimatorSet? = null
        private var fadeOutAnimator: ValueAnimator? = null
        private var rippleAnimator: ValueAnimator? = null
        private val rippleColor = Color.parseColor("#11212121")

        private val rectPaddingLeft = _rectPaddingLeft
        private val rectPaddingTop = _rectPaddingTop
        private val rectPaddingRight = _rectPaddingLeft + _rectPaddingRight
        private val rectMarginLeft = _rectMarginLeft
        private val rectMarginRight = _rectMarginRight

        private var rectF: RectF = createTaskRec(task)
        private var layout: Layout? = createLayout(task)
        private var rectAnimation: RectF? = null

        private var fadeStep = 0
        private var animationX = 0f
        private var animationY = 0f

        private var isShowingAnimation = false

        var width: Int = getWidth()
            set(value) {
                if (this.width != value) {
                    field = value
                    //recalculate rect and text layout size for new width value
                    invalidateCalendarItem()
                }

            }

        fun draw(canvas: Canvas) {
            //draw round rect
            canvas.drawRoundRect(
                rectF,
                10f,
                10f,
                paint.apply { color = task.getRectColor() ?: rectColor })

            //if there is any animation it shows
            rectAnimation?.let {
                canvas.drawRoundRect(it, 10f, 10f, paint.apply {
                    color = rippleColor
                    alpha = fadeStep
                })
            }
            canvas.save()
            //translate canvas to rect position and add padding for text layout
            canvas.translate(
                rectF.left + rectPaddingLeft,
                rectF.top + rectPaddingTop
            )
            //draw text on canvas
            layout?.draw(canvas)
            canvas.restore()
        }

        /**
         * check that given coordinates are in calendar item's rect
         * @param x the x coordinate
         * @param y the y coordinate
         * @return return true if given coordinates are in calendar item's rect other wise return false
         */
        fun isClicked(x: Float, y: Float): Boolean {
            return rectF.contains(x, y)
        }

        private fun invalidateCalendarItem() {
            rectF = createTaskRec(task)
            layout = createLayout(task)
        }

        private fun createLayout(item: CalendarTask): Layout? {


            val maxLine = rectMaxLine(textPaint, rectF)
            if (maxLine < 1) {
                return null
            }

            return TextLayoutBuilder().setText(item.getTitle()).setTextSize(textSize)
                .setWidth(getRectWidth())
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setTextSpacingMultiplier(1f).setIncludeFontPadding(true)
                .setMaxLines(rectMaxLine(textPaint, rectF))
                .setTextColor(task.getTextColor() ?: textColor)
                .setEllipsize(TextUtils.TruncateAt.END).build()
        }

        private fun createTaskRec(item: CalendarTask): RectF {

            //TODO : must support rtl and ltr,temporarily i hard code rtl
            return RectF(
                  rectMarginLeft,
                ((item.getStartTimeHour() + (item.getStartTimeMinute() / 60)) * SPACE_BETWEEN_LINES * density) + convertToDp(
                    1
                ),
                verticalHourBarXPosition - rectMarginRight,
                ((item.getEndTimeHour() + (item.getEndTimeMinute() / 60)) * SPACE_BETWEEN_LINES * density) - convertToDp(
                    1
                )
            )

            /*return RectF(
                verticalHourBarXPosition + rectMarginLeft,
                ((item.getStartTimeHour() + (item.getStartTimeMinute() / 60)) * SPACE_BETWEEN_LINES * density) + convertToDp(
                    1
                ),
                width - rectMarginRight,
                ((item.getEndTimeHour() + (item.getEndTimeMinute() / 60)) * SPACE_BETWEEN_LINES * density) - convertToDp(
                    1
                )
            )*/
        }

        /**
         * calculate max lines that fit in given rectangle
         */
        private fun rectMaxLine(textPaint: TextPaint, rect: RectF): Int {

            val rectHeight = rect.height() - rectPaddingTop
            val textLeading = textPaint.fontMetrics.leading
            val textHeight = textPaint.fontMetrics.bottom - textPaint.fontMetrics.top + textLeading

            return (rectHeight / textHeight).toInt()
        }

        private fun getRectWidth(): Int {
            return (rectF.width() - rectPaddingRight).toInt()
        }

        /**
         * start ripple animation
         * this must be called before call [setRippleAnimationStep]
         */
        fun startAnimation(x: Float, y: Float) {
            if (!isShowingAnimation) {
                isShowingAnimation = true
                rectAnimation = RectF()
                animationX = x
                animationY = y

                rippleAnimator()
                fadeOutAnimator()
                if (animatorSet == null) {
                    animatorSet = AnimatorSet()
                }
                animatorSet?.apply {
                    play(rippleAnimator)
                    play(fadeOutAnimator).after(rippleAnimator)
                    start()
                    addListener(object : AnimatorListenerAdapter() {

                        override fun onAnimationStart(animation: Animator?) {
                            isShowingAnimation = true
                            //Log.i("animation", "startAnimation = $isShowingAnimation")
                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            isShowingAnimation = false
                            freeAnimationResource()
                            //Log.i("animation", "endAnimation = $isShowingAnimation")
                        }

                    })
                }
            }

        }

        fun cancelAnimation() {
            animatorSet?.cancel()
            isShowingAnimation = false
        }

        private fun freeAnimationResource() {
            rectAnimation = null
            rippleAnimator = null
            fadeOutAnimator = null
            animatorSet = null
            isShowingAnimation = false
            invalidate()
        }

        /**
         * @param animationStep animation step
         */
        private fun setRippleAnimationStep(animationStep: Float) {

            val percent = animationStep / 100f
            fadeStep = (DEFAULT_ALPHA * percent).toInt()
            val xMovement = rectF.right - rectF.centerX()
            val yMovement = rectF.bottom - rectF.centerY()
            val centerX = rectF.centerX()
            val centerY = rectF.centerY()

            rectAnimation?.apply {
                left = centerX - (xMovement * percent)
                top = centerY - (yMovement * percent)
                right = centerX + (xMovement * percent)
                bottom = centerY + (yMovement * percent)
            }
            invalidate()
        }

        private fun setFadeOutStep(step: Int) {
            fadeStep = step
            invalidate()
        }

        private fun rippleAnimator() {
            rippleAnimator = ValueAnimator.ofFloat(60f, 100f).apply {
                duration =
                    RIPPLE_ANIMATION_DURATION
                addUpdateListener {
                    val value = it.animatedValue as Float
                    setRippleAnimationStep(value)
                }
            }
        }

        private fun fadeOutAnimator() {
            fadeOutAnimator = ValueAnimator.ofInt(DEFAULT_ALPHA, 0).apply {
                startDelay = 150L
                duration =
                    RIPPLE_ANIMATION_DURATION
                addUpdateListener {
                    val value = it.animatedValue as Int
                    setFadeOutStep(value)
                }
            }
        }

    }


    private inner class TimesOfDay(val isRtl: Boolean = true) {


        private val list = mutableListOf<AMOrPMItem>()


        val spaceBetweenLine = convertToDp(SPACE_BETWEEN_LINES)
        var space = 0f
        val amOrPmTextPadding = convertToDp(4)
        val amOrPmTextSize = convertToDp(AM_PM_TEXT_SIZE)
        val textPaint = TextPaint().apply {
            textSize = amOrPmTextSize
            flags = Paint.ANTI_ALIAS_FLAG
        }

        init {
            createAmPm()
        }


        //because draw text not correctly works on api 17 and below we have to use text layout
        fun createAmPm() {
            val layoutBuilder = TextLayoutBuilder()
            for (i in 1 until 24) {
                space += spaceBetweenLine
                // val amOrPmText = if (i > 11) "${if (i > 12) i - 12 else i} PM" else "$i AM"
                val amOrPmText = "$i:00"


                val layout = layoutBuilder.setText(amOrPmText)
                    .setWidth(
                        (width - verticalHourBarXPosition).toInt()
                    )
                    .setTypeface(Typeface.createFromAsset(context.assets, "shabnamfd.ttf"))
                    .setSingleLine(true)
                    .setAlignment(Layout.Alignment.ALIGN_CENTER)
                    .setIncludeFontPadding(false)
                    .setLineHeight(20f)
                    .setTextSize(convertToDp(12).toInt()).setTextColor(Color.BLACK).build()


                val textXPosition = if (isRtl) {
                    verticalHourBarXPosition
                } else {
                    verticalHourBarXPosition -
                            textPaint.measureText(amOrPmText) - amOrPmTextPadding
                }


                val textYPosition = space - layout!!.height / 2 /*- convertToDp(1)*/
                list.add(
                    AMOrPMItem(
                        layout,
                        amOrPmText,
                        textXPosition,
                        textYPosition
                    )
                )
            }
        }


        fun show(canvas: Canvas) {
            list.forEach {
                canvas.save()
                canvas.translate(it.xPosition, it.yPosition)
                it.layout?.draw(canvas)
                canvas.restore()
            }
        }
    }

    data class AMOrPMItem(
        var layout: Layout? = null,
        var text: String,
        var xPosition: Float,
        var yPosition: Float
    )

}


