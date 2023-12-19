package com.example.pocketgrow.helper

import android.content.Context

internal class AuthPreference(context: Context) {
    companion object {
        const val PENGGUNA_PREP = "USER_PREF"
    }

    var sharePreferences = context.getSharedPreferences(PENGGUNA_PREP, 0)

    fun clear() {
        sharePreferences
            .edit()
            .clear()
            .apply()
    }

    fun getValue(key: String) : String? {

        return sharePreferences.getString(key, null)
    }

    fun setValue(key: String, value: String) {
        val mengedit = sharePreferences
            .edit()

        mengedit.putString(key, value)
        mengedit.apply()
    }
}