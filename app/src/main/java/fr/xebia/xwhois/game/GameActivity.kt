package fr.xebia.xwhois.game

import android.app.ProgressDialog
import android.content.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import fr.xebia.xwhois.R
import fr.xebia.xwhois.keyboard.KeyboardView
import fr.xebia.xwhois.person.DemoPerson
import fr.xebia.xwhois.person.Person
import fr.xebia.xwhois.person.PersonService
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.view_hole_fields.*
import kotlinx.android.synthetic.main.view_keyboard.*

class GameActivity : AppCompatActivity(), KeyboardView.OnKeyboardListener {

    var gameController: GameController = GameController(this)

    val dataUpdateReceiver: DataUpdateReceiver = DataUpdateReceiver()

    var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        keyboardLayout.listener = this

        startService(Intent(this, PersonService::class.java))

        gameController.start(Realm.getInstance(this))
    }

    override fun onResume() {
        super.onResume()

        val filter = IntentFilter("REFRESH_DATA_INTENT")
        registerReceiver(dataUpdateReceiver, filter)
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(dataUpdateReceiver)
    }

    override fun keyboardCharSelected(c: Char) {
        gameController.checkChar(c)
    }

    fun correctChar(c: Char) {
        var win = holeFieldsLayout.show(c)
        gameController.win(win)
    }

    fun setRemaining(remaining: Int) {
        counterText.text = remaining.toString()
    }

    fun bind(person: Person) {
        holeFieldsLayout.bind(person.name)
        Glide.with(this)
                .load(person.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(personImage)
    }

    fun bind(person: DemoPerson) {
        holeFieldsLayout.bind(person.name)
        personImage.setImageResource(person.image)
    }

    fun message(messageId: Int) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
    }

    fun endGame() {
        message(R.string.message_end)
    }

    inner class DataUpdateReceiver() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == "REFRESH_DATA_INTENT") {
                gameController.synced()
            }
        }
    }

    fun showProgress(messageId: Int) {
        if (progress == null) {
            progress = ProgressDialog.show(this, null, getString(messageId))
        }
    }

    fun hideProgress() {
        if (progress != null) {
            (progress as ProgressDialog).dismiss()
            progress = null
        }
    }
}