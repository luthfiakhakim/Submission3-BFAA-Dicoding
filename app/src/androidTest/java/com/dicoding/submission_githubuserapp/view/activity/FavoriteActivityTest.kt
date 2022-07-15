package com.dicoding.submission_githubuserapp.view.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.submission_githubuserapp.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FavoriteActivityTest{

    @Before
    fun setup(){
        ActivityScenario.launch(FavoriteActivity::class.java)
    }

    @Test
    fun listFav(){
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()))
    }
}