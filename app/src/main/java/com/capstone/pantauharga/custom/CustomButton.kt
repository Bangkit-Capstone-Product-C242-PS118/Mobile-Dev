package com.capstone.pantauharga.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.pantauharga.R

class CustomButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColor: Int = 0
    private var backgroundColor: Drawable

    init {
        txtColor = ContextCompat.getColor(context, android.R.color.white)
        backgroundColor = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = backgroundColor
        setTextColor(txtColor)
        gravity = Gravity.CENTER
    }
}