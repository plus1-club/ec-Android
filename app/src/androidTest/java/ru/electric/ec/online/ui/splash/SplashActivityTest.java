package ru.electric.ec.online.ui.splash;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.electric.ec.online.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> activityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @SmallTest
    @Test
    public void openSplashActivity() {
        onView(withId(R.id.splash_screen)).check(matches(isDisplayed()));
    }
}