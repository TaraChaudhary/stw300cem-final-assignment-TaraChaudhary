package com.example.traffic_rule_and_sign_quiz_app;

import androidx.test.rule.ActivityTestRule;

import com.example.traffic_rule_and_sign_quiz_app.ui.Profile.UpdateFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
@RunWith(JUnit4.class)
public class UpdateTest {

    @Rule
public ActivityTestRule<DashboardActivity> rule =
        new ActivityTestRule<>(DashboardActivity.class);

@Before
public void Update()
{
    rule.getActivity().getFragmentManager().beginTransaction();


}

    @Test
    public void testregister(){
        onView(withId(R.id.username)).perform(typeText("as"));

        onView(withId(R.id.first)).perform(typeText("as"));
        onView(withId(R.id.last)).perform(typeText("as"));


        onView(withId(R.id.email1)).perform(typeText("as"));
        onView(withId(R.id.phone1)).perform(typeText("as"));
        onView(withId(R.id.post_profileimg)).perform(typeText("as"));

        closeSoftKeyboard();
        onView(withId(R.id.signup)).perform(click());
        onView(withId(R.id.dasboard))
                .check(matches(isDisplayed()));
    }
}
