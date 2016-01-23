package fr.xebia.xwhois.keyboard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import fr.xebia.xwhois.R
import fr.xebia.xwhois.game.GameActivity
import fr.xebia.xwhois.person.Person
import kotlinx.android.synthetic.main.view_keyboard.view.*
import java.util.*

class KeyboardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val random = Random()

    private var defaultColor: ColorStateList? = null

    lateinit var listener: OnKeyboardListener

    override fun onFinishInflate() {
        super.onFinishInflate()

        a.setOnClickListener { charClicked(it as TextView) }
        b.setOnClickListener { charClicked(it as TextView) }
        c.setOnClickListener { charClicked(it as TextView) }
        d.setOnClickListener { charClicked(it as TextView) }
        e.setOnClickListener { charClicked(it as TextView) }
        f.setOnClickListener { charClicked(it as TextView) }
        g.setOnClickListener { charClicked(it as TextView) }
        h.setOnClickListener { charClicked(it as TextView) }
        i.setOnClickListener { charClicked(it as TextView) }
        j.setOnClickListener { charClicked(it as TextView) }
        k.setOnClickListener { charClicked(it as TextView) }
        l.setOnClickListener { charClicked(it as TextView) }
        m.setOnClickListener { charClicked(it as TextView) }
        n.setOnClickListener { charClicked(it as TextView) }
        o.setOnClickListener { charClicked(it as TextView) }
        p.setOnClickListener { charClicked(it as TextView) }
        q.setOnClickListener { charClicked(it as TextView) }
        r.setOnClickListener { charClicked(it as TextView) }
        s.setOnClickListener { charClicked(it as TextView) }
        t.setOnClickListener { charClicked(it as TextView) }
        u.setOnClickListener { charClicked(it as TextView) }
        v.setOnClickListener { charClicked(it as TextView) }
        w.setOnClickListener { charClicked(it as TextView) }
        xKey.setOnClickListener { charClicked(it as TextView) }
        yKey.setOnClickListener { charClicked(it as TextView) }
        zKey.setOnClickListener { charClicked(it as TextView) }
    }

    private fun charClicked(textView: TextView) {
        if (!textView.isSelected) {
            if (defaultColor == null) {
                defaultColor = textView.textColors
            }
            textView.setTextColor(resources.getColor(R.color.grey_5f))
            textView.isSelected = true
            listener.keyboardCharSelected(textView.text[0])
        }
    }

    private fun findTextViewByChar(c: Char): TextView? {
        for (i in 0..firstLineLayout.childCount - 1) {
            val textView = firstLineLayout.getChildAt(i) as TextView
            if (textView.text[0] == c) {
                return textView
            }
        }
        for (i in 0..secondLineLayout.childCount - 1) {
            val textView = secondLineLayout.getChildAt(i) as TextView
            if (textView.text[0] == c) {
                return textView
            }
        }
        for (i in 0..thirdLineLayout.childCount - 1) {
            val textView = thirdLineLayout.getChildAt(i) as TextView
            if (textView.text[0] == c) {
                return textView
            }
        }
        return null
    }

    fun bind(name: String) {
        for (i in 0..1) {
            val textView = findTextViewByChar(name[random.nextInt(name.length)])
            if (textView != null) {
                charClicked(textView)
            }
        }
    }

    fun reset() {
        if (defaultColor != null) {
            for (i in 0..firstLineLayout.childCount - 1) {
                val textView = firstLineLayout.getChildAt(i) as TextView
                textView.setTextColor(defaultColor)
                textView.isSelected = false
            }
            for (i in 0..secondLineLayout.childCount - 1) {
                val textView = secondLineLayout.getChildAt(i) as TextView
                textView.setTextColor(defaultColor)
                textView.isSelected = false
            }
            for (i in 0..thirdLineLayout.childCount - 1) {
                val textView = thirdLineLayout.getChildAt(i) as TextView
                textView.setTextColor(defaultColor)
                textView.isSelected = false
            }
        }
    }

    interface OnKeyboardListener {
        fun keyboardCharSelected(c: Char)
    }
}