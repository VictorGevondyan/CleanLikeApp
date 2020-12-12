package am.victor.clean_like_app.utils.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.InputFilter
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import am.victor.clean_like_app.R

class VerificationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatEditText(context, attrs) {

    companion object {
        private const val NUMBER_OF_ENTRIES = 6
    }

    private val cursorAnimation: Runnable = object : Runnable {
        override fun run() {
            // Switch the cursor visibility and set it
            val newAlpha = if (cursorPaint.alpha == 0) 255 else 0
            cursorPaint.alpha = newAlpha
            // Call onDraw() to draw the cursor with the new Paint
            invalidate()
            // Wait 500 milliseconds before calling self again
            postDelayed(this, 700)
        }
    }

    private val entryGap = context.resources.getDimension(R.dimen._6dp)
    private val entryCornerRadius =
        context.resources.getDimension(R.dimen._4dp)

    private val entryPaint by lazy {
        Paint().apply {
            color = ResourcesCompat.getColor(context.resources, R.color.light_gray, null)
            strokeWidth = resources.getDimension(R.dimen._1dp)
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
    }

    private val errorEntryPaint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            color = ResourcesCompat.getColor(resources, R.color.red, null)
            strokeWidth = resources.getDimension(R.dimen._1dp)
            isAntiAlias = true
        }
    }

    private val textPaint by lazy {
        Paint().apply {
            textSize = context.resources.getDimension(R.dimen._27sp)
            color = ResourcesCompat.getColor(context.resources, R.color.black, null)
            isAntiAlias = true
            typeface = ResourcesCompat.getFont(context, R.font.roboto)
        }
    }

    private val cursorPaint by lazy {
        Paint().apply {
            color = ResourcesCompat.getColor(resources, R.color.turquoise, null)
            isAntiAlias = true
        }
    }

    private val textRect by lazy {
        Rect()
    }

    private var wholeWidth = 0f
    private var wholeHeight = 0f

    private val cursorWidth = resources.getDimension(R.dimen._2dp)
    private var cursorHeight = 0f
    private val cursorCorners = resources.getDimension(R.dimen._65dp)

    private var entrySize = 0f

    private var isErrorVisible = false

    init {
        setup()
        disableDefaultSelection()
        post(cursorAnimation)
    }

    private fun setup() {

        this.setBackgroundResource(0)
        this.isCursorVisible = false
        this.setTextIsSelectable(false)
        this.filters = arrayOf(InputFilter.LengthFilter(NUMBER_OF_ENTRIES))
        this.keyListener = DigitsKeyListener.getInstance("0123456789")
    }

    private fun disableDefaultSelection() {

        super.setCustomSelectionActionModeCallback(object : ActionMode.Callback {

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
            }

        })

        super.setOnClickListener {
            setSelection(editableText.count())
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val desiredWidth = suggestedMinimumWidth + paddingStart + paddingEnd

        wholeWidth = measureDimension(desiredWidth, widthMeasureSpec).toFloat()

        calculateEntriesSize()

        val desiredHeight = entrySize + paddingTop + paddingBottom

        wholeHeight = measureDimension(desiredHeight.toInt(), heightMeasureSpec).toFloat()

        setMeasuredDimension(wholeWidth.toInt(), wholeHeight.toInt())
    }

    private fun calculateEntriesSize() {
        entrySize =
            (wholeWidth - paddingStart - paddingEnd - (NUMBER_OF_ENTRIES - 1) * entryGap) / NUMBER_OF_ENTRIES
        cursorHeight = entrySize * 36 / 100f
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = result.coerceAtMost(specSize)
            }
        }

        return result
    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.also {

            var startX = paddingStart.toFloat()
            val bottom = wholeHeight - paddingBottom

            for (i in 0 until NUMBER_OF_ENTRIES) {
                it.drawRoundRect(
                    RectF(
                        startX, paddingTop.toFloat(),
                        (startX + entrySize), (paddingTop + entrySize)
                    ),
                    entryCornerRadius,
                    entryCornerRadius,
                    entryPaint
                )

                if (isErrorVisible) {
                    it.drawRoundRect(
                        RectF(
                            startX, paddingTop.toFloat(),
                            (startX + entrySize), (paddingTop + entrySize)
                        ),
                        entryCornerRadius,
                        entryCornerRadius,
                        errorEntryPaint
                    )
                }

                if (editableText.count() > i) {

                    textPaint.getTextBounds(text.toString(), i, i + 1, textRect)
                    val textWidth = textRect.width()
                    val textHeight = textRect.height()
                    val middle = startX + entrySize / 2

                    canvas.drawText(
                        editableText,
                        i,
                        i + 1,
                        middle - textWidth / 2,
                        bottom - entrySize / 2 + textHeight / 2,
                        textPaint
                    )
                }

                startX += (entrySize + entryGap)
            }

            var currentCursorPosition = 0

            text?.also { text -> currentCursorPosition = text.length }

            if (currentCursorPosition < 6) {

                val start =
                    paddingStart + entrySize / 2 + (entryGap + entrySize) * currentCursorPosition

                it.drawRoundRect(
                    start,
                    paddingTop + entrySize / 2 - cursorHeight / 2,
                    start + cursorWidth,
                    paddingTop + entrySize / 2 + cursorHeight / 2,
                    cursorCorners, cursorCorners,
                    cursorPaint
                )
            }
        }
    }

    fun isErrorEnabled(state: Boolean) {
        this.isErrorVisible = state
        invalidate()
    }
}