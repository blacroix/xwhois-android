package fr.xebia.xwhois.person

import android.app.IntentService
import android.content.Intent
import com.bumptech.glide.Glide
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import fr.xebia.xwhois.BuildConfig
import io.realm.Realm
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException

class PersonService : IntentService(PersonService::class.java.simpleName) {

    override fun onHandleIntent(intent: Intent) {
        val client = OkHttpClient()
        client.run {
            val request = Request.Builder().url("${BuildConfig.URL}/api/all").build()
            try {
                val response = client.newCall(request).execute()
                val wrapper = JSONObject(response.body().string())
                val jsonPersons = wrapper.getJSONArray("xebians")

                val realm = Realm.getInstance(this@PersonService)
                realm.beginTransaction()
                for (i in 0..jsonPersons.length() - 1) {
                    val p = Person()
                    val jsonPerson = jsonPersons.getJSONObject(i)
                    p.name = jsonPerson.getString("name").toLowerCase()
                    p.uuid = jsonPerson.getString("uuid")
                    p.image = jsonPerson.getString("image")
                    realm.copyToRealm(p)
                    try {
                        Glide.with(this@PersonService)
                                .load(p.image)
                                .downloadOnly(-1, -1)
                                .get()
                    } catch (e: IOException) {
                        Timber.e(e, "Cannot download image ${p.name} ${p.image}")
                    }
                    Timber.i("Person cached ${i + 1}/${jsonPersons.length()}")
                }
                realm.commitTransaction()
            } catch (e: IOException) {
                Timber.e(e, "Cannot get persons")
            }
        }
        Timber.i("Persons synchronisation ended")
        sendBroadcast(Intent("REFRESH_DATA_INTENT"))
    }

}
