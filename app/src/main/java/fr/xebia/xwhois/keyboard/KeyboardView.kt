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

        a.setOnClickListener { listener.keyboardCharSelected('a') }
        b.setOnClickListener { listener.keyboardCharSelected('b') }
        c.setOnClickListener { listener.keyboardCharSelected('c') }
        d.setOnClickListener { listener.keyboardCharSelected('d') }
        e.setOnClickListener { listener.keyboardCharSelected('e') }
        f.setOnClickListener { listener.keyboardCharSelected('f') }
        g.setOnClickListener { listener.keyboardCharSelected('g') }
        h.setOnClickListener { listener.keyboardCharSelected('h') }
        i.setOnClickListener { listener.keyboardCharSelected('i') }
        j.setOnClickListener { listener.keyboardCharSelected('j') }
        k.setOnClickListener { listener.keyboardCharSelected('k') }
        l.setOnClickListener { listener.keyboardCharSelected('l') }
        m.setOnClickListener { listener.keyboardCharSelected('m') }
        n.setOnClickListener { listener.keyboardCharSelected('n') }
        o.setOnClickListener { listener.keyboardCharSelected('o') }
        p.setOnClickListener { listener.keyboardCharSelected('p') }
        q.setOnClickListener { listener.keyboardCharSelected('q') }
        r.setOnClickListener { listener.keyboardCharSelected('r') }
        s.setOnClickListener { listener.keyboardCharSelected('s') }
        t.setOnClickListener { listener.keyboardCharSelected('t') }
        u.setOnClickListener { listener.keyboardCharSelected('u') }
        v.setOnClickListener { listener.keyboardCharSelected('v') }
        w.setOnClickListener { listener.keyboardCharSelected('w') }
        xKey.setOnClickListener { listener.keyboardCharSelected('x') }
        yKey.setOnClickListener { listener.keyboardCharSelected('y') }
        zKey.setOnClickListener { listener.keyboardCharSelected('z') }
    }

    interface OnKeyboardListener {
        fun keyboardCharSelected(c: Char)
    }
}