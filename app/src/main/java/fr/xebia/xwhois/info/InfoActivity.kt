package fr.xebia.xwhois.info

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.xebia.xwhois.R
import fr.xebia.xwhois.person.Person
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val realm = Realm.getInstance(this)
        val total = realm.where(Person::class.java).findAll().size
        val found = realm.where(Person::class.java).equalTo("found", true).findAll().size
        counterValueText.text = "$found/$total"
        closeButton.setOnClickListener { finish() }
    }

}
