package am.victor.clean_like_app.utils.views

import android.animation.ValueAnimator
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.addTextChangedListener
import am.victor.clean_like_app.R
import am.victor.clean_like_app.utils.ValidationUtil
import am.victor.clean_like_app.utils.extensions.gone
import am.victor.clean_like_app.utils.extensions.invisible
import am.victor.clean_like_app.utils.extensions.visible
import kotlinx.android.synthetic.main.view_regular_edit_text.view.*

class RegularEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayoutCompat(context, attrs) {

    companion object {
        private const val EMAIL_OR_PHONE_TYPE = 0
        private const val PASSWORD_TYPE = 1
        private const val PHONE_NUMBER_TYPE = 2
        private const val TEXT_TYPE = 3

        const val PHONE_NUMBER_MASK = "+1 ([000]) [000]-[0000]"
    }

    interface PasswordFormListener {
        fun onFormChanged(password: String, isCompleted: Boolean)
    }

    interface InputListener {
        fun onInputChanged(input: String)
    }

    private enum class ValidationTypes {
        SPECIAL_SIGNS,
        UPPERCASE_LETTER,
        DIGITS,
        CHARACTERS
    }

    private var inputListener: InputListener? = null
    private var passwordFormListener: PasswordFormListener? = null
    private var validationEnabled = true
    private var passwordVisible = false

    private var errorExpanded: Boolean = false
    private var errorHeight: Int = 0
    private var inputType = EMAIL_OR_PHONE_TYPE

    init {
        initialize(context)
        handleAttributes(context, attrs)

        inputET.addTextChangedListener {

            inputListener?.also { listener ->
                listener.onInputChanged(
                    it.toString()
                )
            }

            if (inputType == PASSWORD_TYPE) {
                if (it.isNullOrEmpty())
                    passwordActionsCont.invisible()
                else
                    passwordActionsCont.visible()
            }

            val text = it?.toString()
            var state = true

            if (validationEnabled) {
                state = validate(ValidationTypes.SPECIAL_SIGNS, text) && state
                state = validate(ValidationTypes.UPPERCASE_LETTER, text) && state
                state = validate(ValidationTypes.DIGITS, text) && state
                state = validate(ValidationTypes.CHARACTERS, text) && state
            }

            passwordFormListener?.also { listener -> listener.onFormChanged(text ?: "", state) }
        }
    }

    private fun initialize(context: Context) {
        inflate(context, R.layout.view_regular_edit_text, this)
        orientation = VERTICAL
    }

    private fun handleAttributes(context: Context, attrs: AttributeSet?) {
        val ar = context.obtainStyledAttributes(attrs, R.styleable.RegularEditText)

        inputType = ar.getInt(R.styleable.RegularEditText_ret_input_type, EMAIL_OR_PHONE_TYPE)

        validationEnabled =
            ar.getBoolean(R.styleable.RegularEditText_ret_password_validation_enabled, true)

        setupInputType(inputType)

        val floatingHint = if (ar.hasValue(R.styleable.RegularEditText_ret_floating_hint))
            ar.getString(R.styleable.RegularEditText_ret_floating_hint)
        else
            resources.getString(R.string.email_or_phone_number)

        val hint = if (ar.hasValue(R.styleable.RegularEditText_ret_hint))
            ar.getString(R.styleable.RegularEditText_ret_hint)
        else
            resources.getString(R.string.your_email_phone_country_code)

        inputET.hint = hint
        floatingHintTV.text = floatingHint

        ar.recycle()
    }

    private fun handlePasswordVisibility() {

        if (passwordVisible) {
            showPasswordBtn.invisible()
            hidePasswordBtn.visible()
        } else {
            showPasswordBtn.visible()
            hidePasswordBtn.invisible()
        }

        inputET.transformationMethod = if (passwordVisible)
            null
        else
            PasswordTransformationMethod()

        inputET.setSelection(inputET.text?.length ?: 0)
    }

    private fun setupInputType(inputType: Int) {

        validationCont.visibility = if (inputType == PASSWORD_TYPE && validationEnabled)
            View.VISIBLE
        else
            View.GONE

        if (inputType == PHONE_NUMBER_TYPE) {

            inputET.gone()
            phoneNumberInputET.visible()

            phoneNumberInputET.addTextChangedListener {
                inputListener?.also { listener -> listener.onInputChanged(phoneNumberInputET.rawText) }
            }
        }

        if (inputType == PASSWORD_TYPE) {

            inputET.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                source.toString().filterNot { it.isWhitespace() }
            })

            showPasswordBtn.setOnClickListener {
                passwordVisible = true
                handlePasswordVisibility()
            }

            hidePasswordBtn.setOnClickListener {
                passwordVisible = false
                handlePasswordVisibility()
            }
        }

        when (inputType) {
            PASSWORD_TYPE -> {
                inputET.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            TEXT_TYPE -> {
              inputET.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            }
            else -> {
                inputET.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            }
        }
    }

    fun showError(errorRes: Int) {
        showError(resources.getString(errorRes))
    }

    fun showError(error: String) {
        errorTV.text = error
        errorHeight = calculateErrorHeight()
        if (!errorExpanded) animateError()
    }

    private val animationUpdateListener = ValueAnimator.AnimatorUpdateListener {

        val currentValue = it.animatedValue as Int

        val params = errorTV.layoutParams
        params.height = currentValue
        errorTV.layoutParams = params
    }

    private fun animateError() {

        val animator = if (errorExpanded) {
            ValueAnimator.ofInt(errorHeight, 0).apply {
                doOnEnd { errorTV.visibility = View.GONE }
                addUpdateListener(animationUpdateListener)
                doOnStart { errorExpanded = false }
            }
        } else {
            ValueAnimator.ofInt(0, errorHeight).apply {
                doOnStart { errorTV.visibility = View.VISIBLE }
                addUpdateListener(animationUpdateListener)
                doOnEnd { errorExpanded = true }
            }
        }

        animator.start()
    }

    private fun calculateErrorHeight(): Int {

        val widthSpec = MeasureSpec.makeMeasureSpec(this@RegularEditText.width, MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)

        errorTV.measure(widthSpec, heightSpec)

        return errorTV.measuredHeight
    }

    fun hideError() {
        if (errorExpanded) animateError()
    }

    private fun validate(type: ValidationTypes, input: String?): Boolean {

        var state = false

        val view: TextView = when (type) {
            ValidationTypes.SPECIAL_SIGNS -> specialSignsTV
            ValidationTypes.UPPERCASE_LETTER -> uppercaseTV
            ValidationTypes.DIGITS -> digitsTV
            ValidationTypes.CHARACTERS -> charactersTV
        }

        state = when (type) {
            ValidationTypes.SPECIAL_SIGNS -> ValidationUtil.isContainsSpecialCharacters(
                input
            )
            ValidationTypes.UPPERCASE_LETTER -> ValidationUtil.isContainsUppercaseLetter(
                input
            )
            ValidationTypes.DIGITS -> ValidationUtil.isContainsDigits(input)
            ValidationTypes.CHARACTERS -> if (input != null) input.length >= 8 else false
        }

        view.apply {

            if (this@RegularEditText.inputET.hasFocus()) {

                this.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        when {
                            input == null || input.isEmpty() -> R.color.dark_gray
                            state -> R.color.black
                            else -> R.color.red
                        },
                        null
                    )
                )

                this.compoundDrawables[0].apply {

                    val wrappedDrawable = DrawableCompat.wrap(this)
                    DrawableCompat.setTint(
                        wrappedDrawable,
                        ResourcesCompat.getColor(
                            resources,
                            when {
                                input == null || input.isEmpty() -> R.color.dark_gray
                                state -> R.color.turquoise
                                else -> R.color.red
                            },
                            null
                        )
                    )
                }
            }
        }

        return state
    }

    fun setInputListener(listener: InputListener) {
        this.inputListener = listener
    }

    fun setPasswordListener(listener: PasswordFormListener) {
        this.passwordFormListener = listener
    }
}