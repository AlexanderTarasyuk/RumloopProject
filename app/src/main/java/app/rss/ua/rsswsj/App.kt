package app.rss.ua.rsswsj

import android.app.Application
import android.content.SharedPreferences

/**
 * @author OTarasiuk
 * @since 13.08.2020
 */

class App : Application() {
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "alex"
    lateinit var sharedPref: SharedPreferences
    override fun onCreate() {
        super.onCreate()
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    }

}