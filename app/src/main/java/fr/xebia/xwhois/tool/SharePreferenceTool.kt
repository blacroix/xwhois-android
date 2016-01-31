package fr.xebia.xwhois.tool

import android.content.Context
import android.content.SharedPreferences

class SharePreferenceTool {

    private val KEY_PREFS = "XWHOIS_PREFERENCES"
    private val KEY_SCORE: String = "SCORE"
    private val KEY_NAME: String = "NAME"
    private val KEY_EMAIL: String = "EMAIL"

    lateinit var preferences: SharedPreferences;

    constructor(context: Context) {
        preferences = context.getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE)
    }

    fun updateScore(additionalScore: Int) {
        var score = preferences.getInt(KEY_SCORE, 0)
        score += additionalScore
        val editor = preferences.edit()
        editor.putInt(KEY_SCORE, score)
        editor.commit()
    }

    fun getScore(): Int {
        return preferences.getInt(KEY_SCORE, 0)
    }

    fun signIn(displayName: String, email: String) {
        val editor = preferences.edit()
        editor.putString(KEY_NAME, displayName)
        editor.putString(KEY_EMAIL, email)
        editor.commit()
    }

    fun isSignIn(): Boolean {
        return preferences.contains(KEY_NAME)
    }
}
