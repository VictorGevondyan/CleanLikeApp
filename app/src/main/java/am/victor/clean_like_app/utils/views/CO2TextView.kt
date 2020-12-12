package am.victor.clean_like_app.utils.views

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.SubscriptSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import am.victor.clean_like_app.R

class CO2TextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.BaseText_Black_13
) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        textAlignment = TEXT_ALIGNMENT_CENTER
    }

    override fun setText(text: CharSequence?, type: BufferType?) {

        val text = "CO2 Saved: $text ppm"
        val superscriptSpan = SubscriptSpan()
        val sizeSpan = RelativeSizeSpan(0.8F)
        val builder = SpannableStringBuilder(text)
        builder.setSpan(
            superscriptSpan,
            2,
            3,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.setSpan(
            sizeSpan,
            2,
            3,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        super.setText(builder, BufferType.SPANNABLE)
    }
}