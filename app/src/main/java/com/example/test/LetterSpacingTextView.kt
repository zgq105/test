package com.example.test

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ScaleXSpan
import android.util.AttributeSet
import android.widget.TextView


class LetterSpacingTextView : TextView {
    private var spacing = Spacing.NORMAL
    private var originalText: CharSequence = ""

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    fun getSpacing(): Float {
        return spacing
    }

    fun setSpacing(spacing: Float) {
        this.spacing = spacing
        applySpacing()
    }

    override fun setText(text: CharSequence, type: BufferType) {
        originalText = text
        applySpacing()
    }

    override fun getText(): CharSequence {
        return originalText
    }

    private fun applySpacing() {
        if (this == null || originalText == null) return
        val builder = StringBuilder()
        for (i in 0 until originalText.length) {
            builder.append(originalText[i])
            if (i + 1 < originalText.length) {
                builder.append("\u00A0")
            }
        }
        val finalText = SpannableString(builder.toString())
        if (builder.toString().length > 1) {
            var i = 1
            while (i < builder.toString().length) {
                finalText.setSpan(
                    ScaleXSpan((spacing + 1) / 10),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                i += 2
            }
        }
        super.setText(finalText, BufferType.SPANNABLE)
    }

    object Spacing {
        const val NORMAL = 0f
    }
}