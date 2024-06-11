package app.forecastweather.search.ui.splash

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Slide
import android.widget.Toast
import app.forecastweather.search.MainActivity
import app.forecastweather.search.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.enterTransition = Slide()
        window.exitTransition = Slide()

        val intent = Intent(this, MainActivity::class.java)
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        val isConnectedToInternet = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if(isConnectedToInternet){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity).toBundle())
            }, 3000)
        } else {
            Toast.makeText(this, "Problem with Internet...", Toast.LENGTH_LONG).show()
        }
    }
}