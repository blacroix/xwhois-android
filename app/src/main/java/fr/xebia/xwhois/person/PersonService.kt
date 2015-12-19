package fr.xebia.xwhois.person

import android.app.IntentService
import android.content.Intent
import com.bumptech.glide.Glide
import fr.xebia.xwhois.BuildConfig
import fr.xebia.xwhois.network.XwhoisService
import io.realm.Realm
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import timber.log.Timber
import java.io.IOException

class PersonService : IntentService(PersonService::class.java.simpleName) {

    lateinit var persons: List<Person>

    override fun onHandleIntent(intent: Intent) {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.URL)
                .build()

        val service = retrofit.create(XwhoisService::class.java)

        val call = service.getXebians()

        try {
            val response = call.execute()
            if (!response.isSuccess) {
                Timber.e("Cannot get persons ${response.message()}")
                return
            }

            persons = response.body().xebians

            preCacheImages()
            store()

            sendBroadcast(Intent("REFRESH_DATA_INTENT"))

            Timber.i("Persons successfully synced")
        } catch (e: IOException) {
            Timber.e(e, "Cannot get persons")
        }
    }

    private fun store() {
        val realm = Realm.getInstance(this)
        realm.executeTransaction {
            realm.clear(Person::class.java)
            persons.forEach { person: Person ->
                person.name = person.name.toLowerCase()
                realm.copyToRealm(person)
            }
        }
    }

    private fun preCacheImages() {
        var index = 0
        persons.forEach { person: Person ->
            Glide.with(applicationContext)
                    .load(person.image)
                    .downloadOnly(300, 300)
                    .get()
            Timber.i("Image downloaded $index/${persons.size - 1}")
            index++
        }
    }

}
