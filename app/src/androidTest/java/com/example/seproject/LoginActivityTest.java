package com.example.seproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.seproject.activities.CreateTutorProfileActivity;
import com.example.seproject.activities.LoginActivity;
import com.example.seproject.activities.SignupActivity;
import com.example.seproject.activities.StudentDashboardActivity;
import com.example.seproject.services.MockAuthService;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> scenario = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setup() {
        Intents.init();

        scenario.getScenario().onActivity(activity -> {
            activity.setAuthService(new MockAuthService());
        });
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testEmptyFields() {
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.loginButton)).check(matches(isEnabled()));
    }

    @Test
    public void testInvalidLogin() {
        onView(withId(R.id.emailInput))
                .perform(replaceText("wrong@xyz.com"), closeSoftKeyboard());

        onView(withId(R.id.passwordInput))
                .perform(replaceText("wrong"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testStudentLoginSuccess() {
        onView(withId(R.id.emailInput))
                .perform(replaceText("soap@xyz.com"), closeSoftKeyboard());

        onView(withId(R.id.passwordInput))
                .perform(replaceText("123456"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());

        intended(hasComponent(StudentDashboardActivity.class.getName()));
    }

    @Test
    public void testTutorLoginSuccess() {
        onView(withId(R.id.emailInput))
                .perform(replaceText("tut@xyz.com"), closeSoftKeyboard());

        onView(withId(R.id.passwordInput))
                .perform(replaceText("123456"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());

        try { Thread.sleep(3000); } catch (Exception e) {}

        intended(hasComponent(CreateTutorProfileActivity.class.getName()));
    }

    @Test
    public void testSignupRedirect() {
        onView(withId(R.id.signupRedirect)).perform(click());

        intended(hasComponent(SignupActivity.class.getName()));
    }
}