package ru.electric.ec.online.ui.enter;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.MediumTest;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.electric.ec.online.R;
import ru.electric.ec.online.ui.info.InfoActivity;
import ru.electric.ec.online.ui.menu.MenuActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.not;

public class EnterActivityTest {

    @Rule
    public ActivityTestRule<EnterActivity> activityRule = new ActivityTestRule<EnterActivity>(EnterActivity.class){
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
    public void openEnterActivity() {
        onView(withId(R.id.editLogin)).check(matches(isDisplayed()));
    }

    @MediumTest
    @Test
    public void fillEditsAndPushEnterButtonSuccess() throws Exception {
        onView(withId(R.id.editLogin)).perform(
                clearText(),
                typeText("1770"),
                closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(
                clearText(),
                typeText("As12345"),
                closeSoftKeyboard());
        onView(withId(R.id.buttonEnter)).perform(click());
        sleep(1000);
        intended(hasComponent(MenuActivity.class.getName()));
    }

    @MediumTest
    @Test
    public void fillEditsAndPushEnterButtonError() throws Exception {
        onView(withId(R.id.editLogin)).perform(
                clearText(),
                typeText("1770"),
                closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(
                clearText(),
                typeText(""),
                closeSoftKeyboard());
        onView(withId(R.id.buttonEnter)).perform(click());
        sleep(1000);
        intended(hasComponent(InfoActivity.class.getName()));
    }
}