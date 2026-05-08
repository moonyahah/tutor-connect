package com.example.seproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static java.util.regex.Pattern.matches;

import androidx.test.espresso.assertion.ViewAssertions;
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
public class SignupActivityTest {
    @Rule
    public ActivityScenarioRule<SignupActivity> scenario =
            new ActivityScenarioRule<>(SignupActivity.class);

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
    public void testEmptySignup() {
        onView(withId(R.id.signupButton)).perform(click());

        onView(withId(R.id.signupButton)).check(ViewAssertions.matches(isEnabled()));
    }

    @Test
    public void testStudentSignupSuccess() {
        onView(withId(R.id.nameInput))
                .perform(replaceText("Test User"), closeSoftKeyboard());

        onView(withId(R.id.emailInput))
                .perform(replaceText("test@xyz.com"), closeSoftKeyboard());

        onView(withId(R.id.passwordInput))
                .perform(replaceText("123456"), closeSoftKeyboard());

        onView(withId(R.id.signupButton)).perform(click());

        intended(hasComponent(StudentDashboardActivity.class.getName()));
    }

    @Test
    public void testTutorSignupRedirect() {
        onView(withId(R.id.nameInput))
                .perform(replaceText("Tutor User"), closeSoftKeyboard());

        onView(withId(R.id.emailInput))
                .perform(replaceText("tutor@test.com"), closeSoftKeyboard());

        onView(withId(R.id.passwordInput))
                .perform(replaceText("123456"), closeSoftKeyboard());

        onView(withId(R.id.tutorRole)).perform(click());

        onView(withId(R.id.signupButton)).perform(click());

        intended(hasComponent(CreateTutorProfileActivity.class.getName()));
    }

    @Test
    public void testLoginRedirect() {
        onView(withId(R.id.loginRedirect)).perform(click());

        intended(hasComponent(LoginActivity.class.getName()));
    }
}