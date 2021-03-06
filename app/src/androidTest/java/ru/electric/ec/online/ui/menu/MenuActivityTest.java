package ru.electric.ec.online.ui.menu;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.electric.ec.online.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

public class MenuActivityTest {
    @Rule
    public ActivityTestRule<MenuActivity> activityRule
            = new ActivityTestRule<MenuActivity>(MenuActivity.class){
        @Override
        protected void afterActivityLaunched() {
            Intents.init();
            super.afterActivityLaunched();
        }

        @Override
        protected void afterActivityFinished() {
            super.afterActivityFinished();
            Intents.release();
        }

        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, MenuActivity.class);
            result.putExtra("title", "Меню");
            return result;
        }
    };

    @Before
    public void setUp() {
        intending(not(isInternal())).respondWith(new Instrumentation
                .ActivityResult(Activity.RESULT_OK, null));
    }

    @After
    public void tearDown() {
    }

    @SmallTest
    @Test
    public void openMenuActivity() {
        onView(withId(R.id.layoutRequest)).check(matches(isDisplayed()));
    }
}