package fr.xebia.xwhois.tool

import android.content.Context
import android.content.SharedPreferences

class SharePreferenceTool {

    val keyPreference = "XWHOIS_PREFERENCES"
    val keyScore: String = "SCORE"

    lateinit var preferences: SharedPreferences;

    constructor(context: Context) {
        preferences = context.getSharedPreferences(keyPreference, Context.MODE_PRIVATE)
    }

    fun updateScore(additionalScore: Int) {
        var score = preferences.getInt(keyScore, 0)
        score += additionalScore
        val editor = preferences.edit()
        editor.putInt(keyScore, score)
        editor.commit()
    }

    public fun getScore(): Int {
        return preferences.getInt(keyScore, 0)
    }
}
