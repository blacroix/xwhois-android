package fr.xebia.xwhois.game

class GameController(var view: GameActivity) {

    lateinit var name: String

    fun checkChar(c: Char) {
        if (name.contains(c)) {
            view.correctChar(c)
        } else {
            view.wrongChar()
        }
    }

}
