package com.dicoding.submission_githubuserapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import com.dicoding.submission_githubuserapp.R
import com.dicoding.submission_githubuserapp.utility.SettingPreferences

class SplashScreen : AppCompatActivity() {

    companion object {
        const val TIME_SPLASH = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, TIME_SPLASH)

        val preferences = SettingPreferences.getInstance(dataStore)

        preferences.getThemeApp().asLiveData().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}