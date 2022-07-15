package com.dicoding.submission_githubuserapp.view.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.submission_githubuserapp.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailUserActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(DetailUserActivity::class.java)
    }

    @Test
    fun addToFavorite(){
        onView(withId(R.id.fab_favorited)).perform(click())
    }
}