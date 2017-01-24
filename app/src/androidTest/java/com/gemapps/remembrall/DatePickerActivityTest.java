package com.gemapps.remembrall;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.gemapps.remembrall.ui.DatePickerActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.gemapps.remembrall.ui.DatePickerActivity.DATA_RESULT_KEY;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by edu on 1/24/17.
 */
@RunWith(AndroidJUnit4.class)
public class DatePickerActivityTest {

    @Rule
    public ActivityTestRule<DatePickerActivity> mActivityTestRule =
            new ActivityTestRule<>(DatePickerActivity.class);


    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
//        Intent resultData = new Intent();
//        resultData.putExtra(DATA_RESULT_KEY, Calendar.getInstance().getTimeInMillis());
//        intending(anyIntent())
//                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));
    }

    @Test
    public void pickingDate(){

        Intent resultData = new Intent();
        resultData.putExtra(DATA_RESULT_KEY, Calendar.getInstance().getTimeInMillis());
        Instrumentation.ActivityResult result = new Instrumentation
                .ActivityResult(Activity.RESULT_OK, resultData);
        Matcher<Intent> expectedIntent = allOf(hasExtraWithKey(DATA_RESULT_KEY));

        Intents.init();
        intending(expectedIntent).respondWith(result);
        onView(withId(R.id.date_picker)).perform(click());
        onView(withId(R.id.accept_button)).perform(click());
        intended(hasComponent(DatePickerActivity.class.getName()));
        intended(expectedIntent);
        Intents.release();

    }
}
