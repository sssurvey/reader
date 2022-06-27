package com.haomins.reader.view.activities

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.haomins.reader.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SettingsActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SettingsActivity::class.java)

    @Test
    fun shouldDisplaySettingsFragmentContainer() {
        onView(withId(R.id.settings_container)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowSettingsFragmentByDefault() {
        onView(withText(R.string.settings_option_dark_mode)).check(matches(isDisplayed()))
        onView(withText(R.string.settings_option_send_feedback)).check(matches(isDisplayed()))
        onView(withText(R.string.settings_news_disclosures)).check(matches(isDisplayed()))
        onView(withText(R.string.settings_option_about)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowDisclosureFragmentWhenClickDisclosureOption() {

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(R.string.settings_news_disclosures)),
                click()
            )
        )
        onView(withId(R.id.news_disclosure_title)).check(matches(isDisplayed()))
        onView(withId(R.id.news_disclosure_content)).check(matches(isDisplayed()))
        onView(withId(R.id.news_disclosure_contact_email)).check(matches(isDisplayed()))
        onView(withId(R.id.news_disclosure_contact_website)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowAboutFragmentWhenClickAboutOption() {
        //TODO:
    }

}