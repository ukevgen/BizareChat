package com.internship.pbt.bizarechat.presentation.view.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import com.internship.pbt.bizarechat.presentation.view.fragment.friends.InviteFriendsFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.privateChat.PrivateChatFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.publicChat.PublicChatFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.users.UsersFragment;

public class MainActivity extends MvpAppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainView {
    private boolean dialogsExist = false;

    private final String newChatFragmentTag = "newChatFragment";
    private final String usersFragmentTag = "usersFragment";

    @InjectPresenter
    MainPresenterImpl presenter;

    @ProvidePresenter
    MainPresenterImpl provideMainPresenter() {
        return new MainPresenterImpl();
    }

    private RelativeLayout mLayout;
    private TextView mTextOnToolbar;
    private NavigationView mNavigationView;
    private Navigator navigator = Navigator.getInstance();
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private AppBarLayout.LayoutParams toolbarParams;
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void setToolbarAndNavigationDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.setToolbarNavigationClickListener(this);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_screen_container, new PublicChatFragment())
                            .commit();
                }
                if (tab.getPosition() == 1) {
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
        toolbarParams = (AppBarLayout.LayoutParams)mToolbar.getLayoutParams();
        setSupportActionBar(mToolbar);
        mTextOnToolbar = (TextView) findViewById(R.id.chat_toolbar_title);
        mNavigationView = (NavigationView)findViewById(R.id.nav_view);
    }

    @Override
    public void showInviteFriendsScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_screen_container, new InviteFriendsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START, true);
        switch (item.getItemId()) {
            case R.id.create_new_chat:
                presenter.addNewChat();
                return true;
            case R.id.users:
                presenter.navigateToUsers();
                return true;
            case R.id.log_out:
                presenter.confirmLogOut();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void confirmLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle(R.string.are_you_sure);
        builder.setPositiveButton(R.string.sign_out, (dialog1, whichButton) -> {

        });

        builder.setNegativeButton(R.string.cancel, (dialog12, whichButton) -> dialog12.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        Button buttonSignOut = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonSignOut.setOnClickListener(
                v -> presenter.logout()
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                mNavigationView.getMenu().getItem(0).setChecked(true);
                presenter.addNewChat();
                break;
            case -1:
                onBackPressed();
                break;
            case R.id.invite_friends:
                presenter.inviteFriends();

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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(newChatFragmentTag);
        if(fragment != null){
            transaction.replace(R.id.main_screen_container, fragment, newChatFragmentTag)
                    .commit();
            return;
        }

        transaction.replace(R.id.main_screen_container, new NewChatFragment(), newChatFragmentTag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startUsersView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(usersFragmentTag);
        if(fragment != null){
            transaction.replace(R.id.main_screen_container, fragment, usersFragmentTag)
                    .commit();
            return;
        }

        transaction.replace(R.id.main_screen_container, new UsersFragment(), usersFragmentTag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void startBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            showNavigationElements();
        }
        super.onBackPressed();
    }

    private void startActionBarToggleAnim(float start, float end) {
        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toggle.onDrawerSlide(null, slideOffset);

                if (slideOffset == 1 && start == 0) {
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
        mTextOnToolbar.setText(getString(R.string.chat));
        mNavigationView.getMenu().getItem(0).setChecked(true);
        mNavigationView.getMenu().getItem(0).setChecked(false);
        toolbarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        mTabLayout.setVisibility(View.VISIBLE);
        if (!dialogsExist)
            mLayout.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle.setDrawerIndicatorEnabled(true);
        startActionBarToggleAnim(1, 0);
    }

    @Override
    public void hideNavigationElements() {
        fab.hide();
        toolbarParams.setScrollFlags(0);
        mTabLayout.setVisibility(View.GONE);
        mLayout.setVisibility(View.GONE);
        startActionBarToggleAnim(0, 1);
    }

    @Override
    public void navigateToLoginScreen() {
        navigator.navigateToLoginActivity(this);
    }

}