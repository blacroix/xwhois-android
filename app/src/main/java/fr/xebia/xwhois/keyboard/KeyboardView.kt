package fr.xebia.xwhois.keyboard

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import fr.xebia.xwhois.game.GameActivity
import kotlinx.android.synthetic.main.view_keyboard.view.*

class KeyboardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var listener: OnKeyboardListener

    override fun onFinishInflate() {
        super.onFinishInflate()

        a.setOnClickListener { listener.click('a') }
        b.setOnClickListener { listener.click('b') }
        c.setOnClickListener { listener.click('c') }
        d.setOnClickListener { listener.click('d') }
        e.setOnClickListener { listener.click('e') }
        f.setOnClickListener { listener.click('f') }
        g.setOnClickListener { listener.click('g') }
        h.setOnClickListener { listener.click('h') }
        i.setOnClickListener { listener.click('i') }
        j.setOnClickListener { listener.click('j') }
        k.setOnClickListener { listener.click('k') }
        l.setOnClickListener { listener.click('l') }
        m.setOnClickListener { listener.click('m') }
        n.setOnClickListener { listener.click('n') }
        o.setOnClickListener { listener.click('o') }
        p.setOnClickListener { listener.click('p') }
        q.setOnClickListener { listener.click('q') }
        r.setOnClickListener { listener.click('r') }
        s.setOnClickListener { listener.click('s') }
        t.setOnClickListener { listener.click('t') }
        u.setOnClickListener { listener.click('u') }
        v.setOnClickListener { listener.click('v') }
        w.setOnClickListener { listener.click('w') }
        xKey.setOnClickListener { listener.click('x') }
        yKey.setOnClickListener { listener.click('y') }
        zKey.setOnClickListener { listener.click('z') }
    }

    interface OnKeyboardListener {
        fun click(c: Char)
    }
}