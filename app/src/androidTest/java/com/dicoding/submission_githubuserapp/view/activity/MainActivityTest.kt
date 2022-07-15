package com.dicoding.submission_githubuserapp.view.activity

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.submission_githubuserapp.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchUser(){
        onView(withId(R.id.searchBar)).perform(click());  //open the searchView
        onView(withId(R.id.search_src_text)).perform(typeText("luthfiakhakim"));// the text was input
        onView(withId(R.id.searchBar)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
    }

    @Test
    fun listUser(){
        onView(withId(R.id.rv_listUser)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_listUser)).perform(click())
    }

    @Test
    fun showFavoriteUser() {
        onView(withId(R.id.favorite_menu)).perform(click())
    }

    @Test
    fun openSetting(){
        onView(withId(R.id.setting_menu)).perform(click())
    }
}
