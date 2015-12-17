package fr.xebia.xwhois.person

import fr.xebia.xwhois.R
import java.util.*

class PersonService {

    val persons: List<Person> = listOf(
            Person("a", "luc legardeur", R.drawable.llegardeur, Date()),
            Person("c", "pablo lopez", R.drawable.plopez, Date()),
            Person("d", "julien buret", R.drawable.jburet, Date()),
            Person("e", "julien smadja", R.drawable.jsmadja, Date()),
            Person("f", "benjamin lacroix", R.drawable.blacroix, Date()),
            Person("g", "simone civetta", R.drawable.scivetta, Date())
    );

}
