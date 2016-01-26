package fr.xebia.xwhois.game

import android.animation.ValueAnimator
import android.app.ProgressDialog
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
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

    lateinit var gameController: GameController

    val dataUpdateReceiver: DataUpdateReceiver = DataUpdateReceiver()

    var progress: ProgressDialog? = null

    lateinit var bulletArray: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        bulletArray = arrayOf(bulletZeroView, bulletOneView, bulletTwoView, bulletThreeView)

        keyboardLayout.listener = this

        startService(Intent(this, PersonService::class.java))

        gameController = GameController(this)
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

    fun looseBullet(position: Int) {
        bulletArray[position].visibility = View.GONE
    }

    fun looseLifeProgress(errorDistance: Int) {
        val ed = pxFromDp(errorDistance)
        val va = ValueAnimator.ofInt(lifeImageView.layoutParams.width, ed)
        va.addUpdateListener {
            val v: Int = it.animatedValue as Int
            lifeImageView.layoutParams.width = v
            lifeImageView.requestLayout()
        }
        va.setDuration(200)
        va.start()
    }

    fun reset(errorDistance: Int) {
        looseLifeProgress(errorDistance)
        keyboardLayout.reset()
        for (imageView in bulletArray)
            imageView.visibility = View.VISIBLE
    }

    fun bind(person: Person) {
        holeFieldsLayout.bind(person.name)
        keyboardLayout.bind(person.name)

        Glide.with(this)
                .load(person.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(personImage)
    }

    fun bind(person: DemoPerson) {
        holeFieldsLayout.bind(person.name)
        keyboardLayout.bind(person.name)
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

    fun pxFromDp(dp: Int): Int {
        return (dp * this@GameActivity.resources.displayMetrics.density).toInt()
    }

    fun setScore(score: Int) {
        counterText.text = getString(R.string.text_score, score)
    }
}