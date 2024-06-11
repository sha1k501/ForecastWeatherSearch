package app.forecastweather.search.utils

import android.content.Context

class Utils(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    var currentCity: String?
        get() = sharedPreferences.getString(CURRENT_WEATHER, null)
        set(value){
            with(sharedPreferences.edit()){
                putString(CURRENT_WEATHER, value)
                apply()
            }
        }

    var lastCity: String?
        get() = sharedPreferences.getString(LAST_WEATHER, null)
        set(value){
            with(sharedPreferences.edit()){
                putString(LAST_WEATHER, value)
                apply()
            }
        }

    var userCity: String?
        get() = sharedPreferences.getString(USER_CITY, null)
        set(value){
            with(sharedPreferences.edit()){
                putString(USER_CITY, value)
                apply()
            }
        }

    companion object{
        const val CURRENT_WEATHER = "current_weather"
        const val LAST_WEATHER = "last_weather"
        const val SHARED_PREFERENCES_NAME = "shared_prefs"
        const val USER_CITY = "user_city"
    }

}