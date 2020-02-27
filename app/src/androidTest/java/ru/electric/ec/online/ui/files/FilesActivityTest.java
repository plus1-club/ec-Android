package ru.electric.ec.online.ui.files;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.SmallTest;
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
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.not;

public class FilesActivityTest {
    @Rule
    public ActivityTestRule<FilesActivity> activityRule
            = new ActivityTestRule<FilesActivity>(FilesActivity.class){
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
            Context targetContext = getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, FilesActivity.class);
            result.putExtra("title", "Список файлов");
            return result;
        }
    };

    @Before
    public void grantPhonePermission() {
        // In M+, trying to call a number will trigger a runtime dialog. Make sure
        // the permission is granted before running this test.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getInstrumentation().getTargetContext().getPackageName()
                            + " android.permission.READ_EXTERNAL_STORAGE");
        }
    }

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
    public void openFilesActivity() {
        onView(withId(R.id.files)).check(matches(isDisplayed()));
    }
}