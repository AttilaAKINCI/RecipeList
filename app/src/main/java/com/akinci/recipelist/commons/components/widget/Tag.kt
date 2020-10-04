package com.akinci.recipelist.commons.components.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart
import com.akinci.recipelist.R
import com.google.android.material.button.MaterialButton

class Tag : AppCompatButton {
    constructor(context: Context) : super(context) { initialize(context, null, 0) }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { initialize(
        context,
        attrs,
        0
    ) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { initialize(context, attrs, defStyleAttr) }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int){

        setTextColor(ContextCompat.getColor(context, R.color.colorSoftWhite))
        background = ContextCompat.getDrawable(context, R.drawable.rounded_button)
        isEnabled = false

        //set margins
        val margin = resources.getDimensionPixelSize(R.dimen.tag_margin)
        val height = resources.getDimensionPixelSize(R.dimen.tag_height)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            height )
        params.setMargins(margin, margin/2, margin, margin/2)
        layoutParams = params
    }
}