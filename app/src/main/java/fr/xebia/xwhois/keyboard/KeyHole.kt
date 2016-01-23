package fr.xebia.xwhois.keyboard

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class KeyHole : TextView {

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        setup()
    }

    private fun setup() {
        typeface = Typeface.createFromAsset(context.assets, "fonts/SpecialElite.ttf")

        val textLayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textLayoutParams.leftMargin = 5
        textLayoutParams.rightMargin = 5
        layoutParams = textLayoutParams

        textSize = 20f
        setAllCaps(true)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup()
    }
}
