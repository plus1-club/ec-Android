package ru.electric.ec.online.ui.details;

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
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Invoice;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

public class DetailsActivityTest {
    @Rule
    public ActivityTestRule<DetailsActivity> activityRule
            = new ActivityTestRule<DetailsActivity>(DetailsActivity.class){
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
            Detail detail = new Detail("test", 2, "шт", 2.0, 4.0, "old", "old2");
            Invoice invoice = new Invoice(123, "01.01.2000", 110.0, "old");
            invoice.details.add(detail);
            App.getModel().invoice.invoices.add(invoice);

            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, DetailsActivity.class);
            result.putExtra("title", "Детали счета");
            result.putExtra("position", 0);
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
    public void openDetailsActivity() {
        onView(withId(R.id.textNumberDate)).check(matches(isDisplayed()));
    }

}