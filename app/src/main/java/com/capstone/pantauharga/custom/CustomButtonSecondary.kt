package com.capstone.pantauharga.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.pantauharga.R

class CustomButtonSecondary : AppCompatButton {
    private var isActive: Boolean = false
    private var activeBackground: Drawable
    private var inactiveBackground: Drawable
    private var activeTextColor: Int
    private var inactiveTextColor: Int

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        activeBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_active)!!
        inactiveBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_secondary)!!
        activeTextColor = ContextCompat.getColor(context, android.R.color.white)
        inactiveTextColor = ContextCompat.getColor(context, R.color.blue)
        updateStyle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        gravity = Gravity.CENTER
    }

    fun setActive(isActive: Boolean) {
        this.isActive = isActive
        updateStyle()
    }

    private fun updateStyle() {
        background = if (isActive) activeBackground else inactiveBackground
        setTextColor(if (isActive) activeTextColor else inactiveTextColor)
    }
}