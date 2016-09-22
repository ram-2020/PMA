package com.themartinsierra.popularmovieapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest  {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    //What's a view?
    //Check here for more answers:
    //http://tutorials.jenkov.com/android/view-viewgroup.html
    //How to set this up better than the example in Android website?
    //http://www.vogella.com/tutorials/AndroidTestingEspresso/article.html

    //tests a click on fragment_movie.xml's gridview.
    @Test
    public void test () {
        onView(withId(R.id.movies_grid)).perform(click());
    }
}