package com.example.traffic_rule_and_sign_quiz_app;

import androidx.test.rule.ActivityTestRule;

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
public class RegisterTest {

    @Rule
    public ActivityTestRule<RegisterActivity> rule =
            new ActivityTestRule<>(RegisterActivity.class);
    String user = "admin";
    String pass = "tara";
    String firstname = "tara";
    String lastname = "chaudhary";
    String phone = "9817509910";
    String gender = "male";
    String dob = "02";
    String image = "image";
    String email = "iamtara@gmail.com";


    @Test
    public void testregister(){
        onView(withId(R.id.username)).perform(typeText(user));
        onView(withId(R.id.password)).perform(typeText(pass));
        onView(withId(R.id.firstname)).perform(typeText(firstname));
        onView(withId(R.id.lastname)).perform(typeText(lastname));
        onView(withId(R.id.rbMale)).perform(typeText(gender));
        onView(withId(R.id.datePicker)).perform(typeText(dob));
        onView(withId(R.id.email)).perform(typeText(email));
        onView(withId(R.id.mobile)).perform(typeText(phone));
       onView(withId(R.id.imgProfile)).perform(typeText(image));

        closeSoftKeyboard();
        onView(withId(R.id.btnsignup)).perform(click());
        onView(withId(R.id.loginqqq))
                .check(matches(isDisplayed()));
    }
}
