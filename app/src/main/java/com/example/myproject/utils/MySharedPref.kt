package com.example.myproject.utils

import SHAREDPREF_NAME
import SHAREDPREF_SESSION_IMAGE_URL
import SHAREDPREF_SESSION_TOKEN
import SHAREDPREF_SESSION_USERNAME
import SHAREDPREF_SESSION_USER_ID
import android.content.Context

import android.content.SharedPreferences

class MySharedPref(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences =  context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
    }

    var token: String?
        get() = sharedPreferences.getString(SHAREDPREF_SESSION_TOKEN, null)
        set(value) = sharedPreferences.edit().putString(SHAREDPREF_SESSION_TOKEN, value).apply()

    var userId: Int?
        get() = sharedPreferences.getInt(SHAREDPREF_SESSION_USER_ID, 0)
        set(value) = sharedPreferences.edit().putInt(SHAREDPREF_SESSION_USER_ID, value ?: 0).apply()

/*

    private fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    private fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }


    fun getUserId(): Int {
       return getInt(SHAREDPREF_SESSION_USER_ID, 0)
    }

    fun getUsername(): String? {
        return getString(SHAREDPREF_SESSION_USERNAME, null)
    }

*/

/*

    fun getToken(): String? {
        return getString(SHAREDPREF_SESSION_TOKEN, null)
    }

    fun saveUserId(userId: Int) {
        saveInt(SHAREDPREF_SESSION_USER_ID, userId)
    }

    fun saveUsername(username: String) {
        saveString(SHAREDPREF_SESSION_USERNAME, username)
    }

    fun saveToken(token: String) {
        saveString(SHAREDPREF_SESSION_TOKEN, token)
    }
*/

    fun clearSharedPref() {
        with(sharedPreferences) {
            edit()
                .remove(SHAREDPREF_SESSION_TOKEN)
                .remove(SHAREDPREF_SESSION_USER_ID)
                .apply()
        }
    }
}
