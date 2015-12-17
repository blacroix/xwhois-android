package fr.xebia.xwhois.keyboard

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import fr.xebia.xwhois.R
import kotlinx.android.synthetic.main.view_keyboard.view.*

class HoleFieldsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var name: String

    lateinit var group: LinearLayout

    fun bind(name: String) {
        this.name = name

        group = LinearLayout(context)
        group.orientation = HORIZONTAL
        group.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val holeFieldsLayout = findViewById(R.id.holeFieldsLayout) as LinearLayout
        holeFieldsLayout.addView(group)
        for (i in 0..name.length - 1) {
            var textView = TextView(context)
            val textLayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textLayoutParams.leftMargin = 10
            textLayoutParams.rightMargin = 10
            textView.layoutParams = textLayoutParams
            val c = name[i]
            if (c === ' ') {
                textView.text = " "
                textView.tag = 0
            } else {
                textView.text = "_"
                textView.tag = c
            }
            group.addView(textView)
        }
    }

    fun show(c: Char): Boolean {
        var win = 0
        for (i in 0..group.childCount - 1) {
            val view = group.getChildAt(i)
            if (view.tag === c) {
                view.tag = 0;
                (view as TextView).text = c.toString();
            } else if (view.tag != 0) {
                win += 1
            }
        }
        return win === 0
    }

}
