package fr.xebia.xwhois.game

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import fr.xebia.xwhois.R
import fr.xebia.xwhois.keyboard.KeyboardView
import kotlinx.android.synthetic.main.view_hole_fields.*
import kotlinx.android.synthetic.main.view_keyboard.*

class GameActivity : AppCompatActivity(), KeyboardView.OnKeyboardListener {

    var controller: GameController = GameController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val name = "Luc Legardeur".toLowerCase()
        controller.name = name
        holeFieldsLayout.bind(name);

        keyboardLayout.listener = this
    }

    override fun click(c: Char) {
        controller.checkChar(c)
    }

    fun correctChar(c: Char) {
        var win = holeFieldsLayout.show(c)
        if (win) {
            Toast.makeText(this, "Winner!", Toast.LENGTH_SHORT).show()
        }
    }

    fun wrongChar() {

    }
}