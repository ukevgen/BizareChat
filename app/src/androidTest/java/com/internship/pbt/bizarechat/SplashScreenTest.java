package com.internship.pbt.bizarechat;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.internship.pbt.bizarechat.presentation.view.activity.SplashActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;

@RunWith(AndroidJUnit4.class)
public class SplashScreenTest {
    
    @Rule
    public ActivityTestRule<SplashActivity> mActivityRule = new ActivityTestRule<>(
            SplashActivity.class);

    @Test
    public void splashScreenDisappearsIn3Seconds(){
        init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intended(anyIntent());
            }
        }).start();
        release();
    }
}
