package fr.xebia.xwhois.game

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import fr.xebia.xwhois.R
import fr.xebia.xwhois.keyboard.KeyboardView
import fr.xebia.xwhois.person.Person
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.view_hole_fields.*
import kotlinx.android.synthetic.main.view_keyboard.*

class GameActivity : AppCompatActivity(), KeyboardView.OnKeyboardListener {

    var controller: GameController = GameController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        keyboardLayout.listener = this

        controller.nextX()
    }

    override fun click(c: Char) {
        controller.checkChar(c)
    }

    fun correctChar(c: Char) {
        var win = holeFieldsLayout.show(c)
        controller.win(win)
    }

    fun setRemaining(remaining: Int) {
        counterText.text = remaining.toString()
    }

    fun bind(person: Person) {
        holeFieldsLayout.bind(person.name)
        personImage.setImageResource(person.image)
    }

    fun message(messageId: Int) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
    }
}