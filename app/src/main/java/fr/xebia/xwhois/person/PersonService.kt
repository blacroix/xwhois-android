package fr.xebia.xwhois.person

import android.app.IntentService
import android.content.Intent
import com.bumptech.glide.Glide
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import fr.xebia.xwhois.BuildConfig
import fr.xebia.xwhois.tool.AsciiTool
import io.realm.Realm
import org.json.JSONArray
import timber.log.Timber
import java.io.IOException

class PersonService : IntentService(PersonService::class.java.simpleName) {

    val keyFound = "found"
    val keyUuid = "uuid"
    val keyName = "name"
    val keyImage = "image"

    val asciiTool = AsciiTool()

    override fun onHandleIntent(intent: Intent) {
        val client = OkHttpClient()
        client.run {
            val request = Request.Builder().url("${BuildConfig.URL}/api/all").build()
            try {
                val response = client.newCall(request).execute()
                val jsonPersons = JSONArray(response.body().string())
                val realm = Realm.getInstance(this@PersonService)
                savePersons(jsonPersons, realm)
                cacheImages(realm)
            } catch (e: Exception) {
                Timber.e(e, "Cannot get persons")
            }
        }
        Timber.i("Persons synchronisation ended")
    }

    private fun cacheImages(realm: Realm) {
        val persons = realm.where(Person::class.java).equalTo(keyFound, false).findAll()
        for (i in 0..persons.size - 1) {
            val p = persons[i]
            try {
                Glide.with(this@PersonService)
                        .load(p.image)
                        .downloadOnly(-1, -1)
                        .get()
            } catch (e: IOException) {
                Timber.e(e, "Cannot download image ${p.name} ${p.image}")
            }
            if (i == 10) {
                sendBroadcast(Intent("REFRESH_DATA_INTENT"))
            }
        }
    }

    private fun savePersons(jsonPersons: JSONArray, realm: Realm) {
        realm.executeTransaction {
            for (i in 0..jsonPersons.length() - 1) {
                var p: Person?
                val jsonPerson = jsonPersons.getJSONObject(i)
                val uuid = jsonPerson.getString(keyUuid)
                p = realm.where(Person::class.java).equalTo(keyUuid, uuid).findFirst()
                if (p == null) {
                    p = Person()
                }
                p.name = asciiTool.normalize(jsonPerson.getString(keyName).toLowerCase())
                p.image = jsonPerson.getString(keyImage)
                p.uuid = uuid
                realm.copyToRealmOrUpdate(p)
                Timber.i("Person cached ${i + 1}/${jsonPersons.length()}")
            }
        }
    }

}
