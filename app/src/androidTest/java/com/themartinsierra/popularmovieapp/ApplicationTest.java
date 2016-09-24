package com.themartinsierra.popularmovieapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.matcher.ViewMatchers;
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
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
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
        onView(ViewMatchers.withId(R.id.movies_grid));
        onView(withId(R.id.movies_grid)).perform(click());
        try {
            Thread.sleep(10000, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(ViewMatchers.withId(R.id.movieposter_image));
        onView(withId(R.id.originaltitle_text)).check(matches(withText("Mechanic: Resurrection")));
        onView(withId(R.id.releasedate_text)).check(matches(withText("2016-08-25")));
        onView(withId(R.id.voteaverage_text)).check(matches(withText("4.28")));
        onView(withId(R.id.overview_text)).check(matches(withText("Arthur Bishop thought he had put his murderous past behind him when his most formidable foe kidnaps the love of his life. Now he is forced to travel the globe to complete three impossible assassinations, and do what he does best, make them look like accidents.")));

    }
}