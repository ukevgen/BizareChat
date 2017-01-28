package com.internship.pbt.bizarechat;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.internship.pbt.bizarechat.presentation.view.activity.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class LoginScreenErrorTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void checkErrorOnWrongLoginPassword(){
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
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }

            onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Wrong login or password")))
                    .check(matches(isDisplayed()));
        }).start();
    }
}
