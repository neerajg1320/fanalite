package com.fanalite.rulesapp.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText(context: Context, attrs: AttributeSet ): AppCompatEditText(context, attrs) {
    init {
        applyFont()
    }

    private fun applyFont() {
        val typeFace: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        typeface = typeFace
    }
}