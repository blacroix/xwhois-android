package fr.xebia.xwhois.keyboard

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.view_hole_fields.view.*

class HoleFieldsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var group: LinearLayout

    override fun onFinishInflate() {
        super.onFinishInflate()

        group = LinearLayout(context)
        group.orientation = HORIZONTAL
        group.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        holeFieldsLayout.addView(group)
    }

    fun bind(name: String) {
        group.removeAllViews()
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
