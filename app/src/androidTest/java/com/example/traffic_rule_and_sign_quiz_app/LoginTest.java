package com.example.traffic_rule_and_sign_quiz_app;


import androidx.test.rule.ActivityTestRule;

import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;

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
public class LoginTest {
    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class);
    String username = "admin";
    String password = "tara";


    @Test
    public void testLogin(){
        onView(withId(R.id.username)).perform(typeText(username));
        onView(withId(R.id.password)).perform(typeText(password));
        closeSoftKeyboard();
        onView(withId(R.id.signin)).perform(click());
        onView(withId(R.id.dasboard))
                .check(matches(isDisplayed()));
    }


}

