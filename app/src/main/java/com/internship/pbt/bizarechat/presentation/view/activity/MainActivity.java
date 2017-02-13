package com.internship.pbt.bizarechat.presentation.view.activity;

import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.navigation.Navigator;
import com.internship.pbt.bizarechat.presentation.presenter.main.MainPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.privateChat.PrivateChatFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.publicChat.PublicChatFragment;

public class MainActivity extends MvpAppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainView {

    @InjectPresenter
    MainPresenterImpl presenter;
    private RelativeLayout mLayout;
    private TextView mTextOnToolbar;
    private NavigationView mNavigationView;

    @ProvidePresenter
    MainPresenterImpl provideMainPresenter() {
        return new MainPresenterImpl();
    }

    private ActionBarDrawerToggle toggle;
    private Navigator navigator = Navigator.getInstance();
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private FloatingActionButton fab;
    private ActionBarDrawerToggle toggle;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base_layout);


        findViews();
        setToolbarAndNavigationDrawer();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setToolbarAndNavigationDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_screen_container, new PublicChatFragment())
                            .commit();
                }
                if(tab.getPosition() == 1){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_screen_container, new PrivateChatFragment())
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void findViews() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        mLayout = (RelativeLayout) findViewById(R.id.layout_chat_empty);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Public"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Private"));
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTextOnToolbar = (TextView) findViewById(R.id.chat_toolbar_title);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.create_new_chat:
                presenter.addNewChat();
                return true;
            case R.id.log_out:
                confirmLogOut();
                return true;
                break;
            default:
                return false;
        }
    }

    private void confirmLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle(R.string.are_you_sure);
        builder.setPositiveButton(R.string.sign_out, (dialog1, whichButton) -> {

        });

        builder.setNegativeButton(R.string.cancel, (dialog12, whichButton) -> dialog12.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        Button buttonSigOut = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonSigOut.setOnClickListener(
                v -> presenter.logout()
        );
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            presenter.addNewChat();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void showLackOfFriends() {
        Snackbar.make(mDrawer, R.string.no_friends_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startNewChatView() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_screen_container, new NewChatFragment())
                .addToBackStack(null)
                .commit();
        mDrawer.closeDrawer(GravityCompat.START, true);
        mTabLayout.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
        startActionBarToggleAnim(0, 1);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            mTabLayout.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            startActionBarToggleAnim(1, 0);
        }
        super.onBackPressed();
    }

    private void startActionBarToggleAnim(float start, float end){
        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toggle.onDrawerSlide(null, slideOffset);

                if(slideOffset == 1 && start == 0) {
                    toggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(200);
        anim.start();
    }

    @Override
    public void showNavigationElements() {
        fab.show();
        mTabLayout.setVisibility(View.VISIBLE);
        toggle.setDrawerIndicatorEnabled(true);

    }

    @Override
    public void hideNavigationElements() {
        fab.hide();
        mTabLayout.setVisibility(View.GONE);
        toggle.setDrawerIndicatorEnabled(false);
        mLayout.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextOnToolbar.setText("NEW CHAT");
    }

    @Override
    public void navigateToLoginScreen() {
        navigator.navigateToLoginActivity(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}