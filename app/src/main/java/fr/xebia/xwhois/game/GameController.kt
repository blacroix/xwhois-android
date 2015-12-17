package fr.xebia.xwhois.game

import android.os.Handler
import fr.xebia.xwhois.R
import fr.xebia.xwhois.person.Person
import fr.xebia.xwhois.person.PersonService

class GameController(var view: GameActivity) {

    lateinit var person: Person

    var allowedTry = 4

    var personService = PersonService()

    var position = 0

    var remaining = allowedTry

    var pause = false

    fun checkChar(c: Char) {
        if (!pause) {
            if (person.name.contains(c)) {
                view.correctChar(c)
            } else {
                remaining--
                view.setRemaining(remaining)
                if (remaining === 0) {
                    pause = true
                    view.message(R.string.message_loose)
                    Handler().postDelayed({ nextX() }, 1500)
                }
            }
        }
    }

    fun nextX() {
        pause = false
        remaining = allowedTry
        person = personService.persons[position]
        view.setRemaining(remaining)
        view.bind(person)
        position = (position + 1) % personService.persons.size
    }

    fun win(win: Boolean) {
        if (win) {
            pause = true
            view.message(R.string.message_win);
            Handler().postDelayed({ nextX() }, 1500)
        }
    }

}
