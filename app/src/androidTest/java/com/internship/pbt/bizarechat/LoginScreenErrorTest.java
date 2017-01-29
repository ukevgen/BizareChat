package com.internship.pbt.bizarechat;


import android.app.Instrumentation;
import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.internship.pbt.bizarechat.presentation.view.activity.LoginActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class LoginScreenErrorTest {

    private static Instrumentation instr;
    private static Instrumentation.ActivityMonitor monitor;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);
    public static Context context;


    @BeforeClass
    public static void init() {

        context = getInstrumentation().getTargetContext();
        instr = getInstrumentation();
        monitor = instr.addMonitor(LoginActivity.class.getName(), null, false);
    }


    @Test
    public void checkModalDialogForgotPassword() {
        onView(withId(R.id.forgot_password)).check(matches(isDisplayed()));
        onView(withId(R.id.forgot_password)).perform(click());
    }

    @Test
    public void checkDisabledButtonSend() {
        onView(withId(R.id.forgot_password)).perform(click());
        onView(withId(android.R.id.button1)).check(matches(not(isEnabled())));

        ViewInteraction email = onView(
                allOf(withClassName(is("android.support.design.widget.TextInputEditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        email.perform(replaceText("йцкк"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).check(matches((isEnabled())));

        email.perform(replaceText(""), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).check(matches(not(isEnabled())));

        onView(withId(android.R.id.button2)).perform(click());

    }


    @Test
    public void checkErrorOnWrongLoginPassword() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email),
                        withParent(allOf(withId(R.id.relativeLayout),
                                withParent(withId(R.id.card))))));
        appCompatEditText.perform(scrollTo(), replaceText("aaaaa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        withParent(allOf(withId(R.id.relativeLayout),
                                withParent(withId(R.id.card))))));
        appCompatEditText2.perform(scrollTo(), replaceText("aaaaa"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_in), withText("SIGN IN"),
                        withParent(allOf(withId(R.id.relativeLayout),
                                withParent(withId(R.id.card))))));
        appCompatButton.perform(scrollTo(), click());

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Wrong login or password")))
                    .check(matches(isDisplayed()));
        }).start();
    }

}
