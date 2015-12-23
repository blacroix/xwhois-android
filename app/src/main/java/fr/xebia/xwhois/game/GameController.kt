package fr.xebia.xwhois.game

import android.os.Handler
import fr.xebia.xwhois.R
import fr.xebia.xwhois.person.DemoPerson
import fr.xebia.xwhois.person.Person
import io.realm.Realm

class GameController(var view: GameActivity) {

    val handler = Handler()
    var persons: List<Person> = listOf()
    var demoPersons: List<DemoPerson> = listOf()
    var personUuid: String = ""
    var allowedTry = 4
    var position = -1
    var remaining = allowedTry
    var pause = false
    var type = 0

    lateinit var realm: Realm

    fun checkChar(c: Char) {
        if (!pause) {
            val name = getPersonName()
            if (name.contains(c)) {
                view.correctChar(c)
            } else {
                remaining--
                view.setRemaining(remaining)
                if (remaining === 0) {
                    pause = true
                    view.message(R.string.message_loose)
                    handler.postDelayed({ next() }, 1500)
                }
            }
        }
    }

    private fun setFound() {
        var person = getPerson(personUuid)
        if (person != null) {
            realm.beginTransaction()
            person.isFound = true
            realm.commitTransaction()
        }

    }

    private fun getPerson(uuid: String): Person? {
        val query = realm.where(Person::class.java).equalTo("uuid", uuid)
        if (query != null) {
            return query.findFirst()
        }
        return null
    }

    private fun getPersonName(): String {
        if (type === 0) {
            return demoPersons[position].name
        } else {
            return realm.where(Person::class.java).equalTo("uuid", personUuid).findFirst().name
        }
    }

    fun next() {
        position++
        remaining = allowedTry
        view.reset(remaining)

        if (persons.isEmpty()) {
            if (type == 1) {
                view.endGame()
            }
            type = 0
            if (position >= demoPersons.size) {
                view.showProgress(R.string.dialog_downloading)
                handler.postDelayed({ next() }, 1000)
            } else {
                pause = false
                val demoPerson = demoPersons[position]
                personUuid = demoPerson.uuid
                view.bind(demoPerson)
            }
        } else {
            if (type == 0) {
                position = 0
            }
            view.hideProgress()
            type = 1
            if (position >= persons.size) {
                reload()
                next()
            } else {
                pause = false
                val person = persons[position]
                personUuid = person.uuid
                view.bind(person)
            }
        }
    }

    private fun reload() {
        position = -1
        persons = realm.where(Person::class.java)
                .equalTo("found", false)
                .findAll()
    }

    fun win(win: Boolean) {
        if (win) {
            pause = true
            setFound()
            view.message(R.string.message_win);
            handler.postDelayed({ next() }, 1500)
        }
    }

    fun start(realm: Realm) {
        this.realm = realm

        val results = realm.where(Person::class.java)
                .equalTo("found", false)
                .findAll()
        if (results.isEmpty()) {
            demoPersons = withDemoPersons()
        } else {
            persons = results
        }
        next()
    }

    private fun withDemoPersons(): List<DemoPerson> {
        return listOf(
                DemoPerson("ee3bd91f-28b1-5615-a4c3-bad34f5d9db9", "luc legardeur", R.drawable.llegardeur),
                DemoPerson("a5e8bd08-4333-5a88-9d97-53b3a05b7169", "julien smadja", R.drawable.jsmadja),
                DemoPerson("c25e37c1-2d28-5a7e-b8b9-27016a654016", "pablo lopez", R.drawable.plopez),
                DemoPerson("fa1c9623-5f4f-5fff-8beb-ffee594489d3", "simone civetta", R.drawable.scivetta),
                DemoPerson("9c65ebcf-297f-5221-8e9e-d3722cec227e", "benjamin lacroix", R.drawable.blacroix),
                DemoPerson("6432aef7-ed00-53a3-a25c-363315ae52b2", "julien buret", R.drawable.jburet)
        )
    }

    fun synced() {
        if (type == 0) {
            persons = realm.where(Person::class.java)
                    .equalTo("found", false)
                    .findAll()
        }
    }

}
