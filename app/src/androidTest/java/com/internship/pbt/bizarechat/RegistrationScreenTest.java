package com.internship.pbt.bizarechat;


import android.app.Instrumentation;
import android.content.Context;
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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RegistrationScreenTest {

    private static Instrumentation instr;
    private static Instrumentation.ActivityMonitor monitor;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    public static Context context;

    @BeforeClass
    public static void init() {

        context = getInstrumentation().getTargetContext();
        instr = getInstrumentation();
        monitor = instr.addMonitor(LoginActivity.class.getName(), null, false);
    }

    @Test
    public void checkPasswordConfirm() {
        onView(withId(R.id.sign_up)).perform(click());
        onView(withId(R.id.register_password)).perform(replaceText("QW123qweqwe"));
        onView(withId(R.id.register_confirm_password)).perform(replaceText(""));
        onView(allOf(withId(R.id.register_sign_up), withText("SIGN UP")))
                .perform(scrollTo(), click());

        onView(withText(R.string.do_not_match_password))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity()
                        .getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

}