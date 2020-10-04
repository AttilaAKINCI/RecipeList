package com.akinci.recipelist.commons.components.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.akinci.recipelist.R

class CustomToolbar : Toolbar {
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

    lateinit var container:RelativeLayout
    lateinit var leftBarContainer:LinearLayout
    lateinit var titleTextView:AppCompatTextView

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int){

        container = RelativeLayout(context)
        container.layoutParams = ViewGroup.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )

        leftBarContainer = LinearLayout(context)
        leftBarContainer.orientation = LinearLayout.HORIZONTAL
        val leftBarLayoutParams = RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )
        leftBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START)
        leftBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        leftBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        leftBarContainer.layoutParams = leftBarLayoutParams

        titleTextView = AppCompatTextView(context)
        titleTextView.isSingleLine = true
        titleTextView.ellipsize = TextUtils.TruncateAt.END
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        titleTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.colorSoftWhite, context.theme))
        val titleLayoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        titleLayoutParams.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        titleTextView.layoutParams = titleLayoutParams

        leftBarContainer.addView(titleTextView)

        container.addView(leftBarContainer)
        addView(container)
    }

    override fun setTitle(title: CharSequence?) { titleTextView.text = title }
}