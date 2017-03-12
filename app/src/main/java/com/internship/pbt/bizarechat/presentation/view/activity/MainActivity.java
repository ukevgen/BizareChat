package com.internship.pbt.bizarechat.presentation.view.activity;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.DialogsDataRepository;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.domain.events.GcmMessageReceivedEvent;
import com.internship.pbt.bizarechat.domain.interactor.GetAllDialogsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.SignOutUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.navigation.Navigator;
import com.internship.pbt.bizarechat.presentation.presenter.main.MainPresenterImpl;
import com.internship.pbt.bizarechat.presentation.util.Converter;
import com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PrivateDialogsFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PublicDialogsFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.friends.InviteFriendsFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.settings.SettingsFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.userinfo.UserInfoFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.users.UsersFragment;
import com.internship.pbt.bizarechat.service.messageservice.BizareChatMessageService;
import com.internship.pbt.bizarechat.service.messageservice.MessageServiceBinder;
import com.internship.pbt.bizarechat.service.util.NotificationUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends MvpAppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainView,
        PrivateDialogsFragment.OnPrivateDialogClickListener, PublicDialogsFragment.OnPublicDialogClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String NEW_CHAT_FR_TAG = "newChatFragment";
    private final static String USERS_FR_TAG = "usersFragment";
    private final static String INVITE_FRIENDS_FR_TAG = "inviteFriendsFragment";
    private final static String PUBLIC_DIALOGS_FR_TAG = "publicDialogsFragment";
    private final static String PRIVATE_DIALOGS_FR_TAG = "privateDialogsFragment";
    private final static String CHAT_ROOM_FR_TAG = "chatRoomFragment_";
    private final static String SETTINGS_FR_TAG = "settingsFragment";


    @InjectPresenter
    MainPresenterImpl presenter;
    private RelativeLayout mLayout;
    private TextView mTextOnToolbar;
    private NavigationView mNavigationView;
    private Navigator navigator = Navigator.getInstance();
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private AppBarLayout.LayoutParams toolbarParams;
    private TabLayout mTabLayout;
    private FloatingActionButton fab;
    private ActionBarDrawerToggle toggle;
    private BizareChatMessageService messageService;
    private ServiceConnection messageServiceConnection;
    private Intent messageServiceIntent;
    private ProgressBar progressBar;
    private Converter converter;
    private CircleImageView drawerAvatar;
    private TextView drawerLogin;
    private CurrentUser currentUser;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @ProvidePresenter
    MainPresenterImpl provideMainPresenter() {
        return new MainPresenterImpl(new SignOutUseCase(new SessionDataRepository(BizareChatApp.
                getInstance().getSessionService())),
                new GetAllDialogsUseCase(
                        new DialogsDataRepository(
                                BizareChatApp.getInstance().getDialogsService(),
                                BizareChatApp.getInstance().getDaoSession())),
                BizareChatApp.getInstance().getDaoSession(),
                new GetPhotoUseCase(
                        new ContentDataRepository(
                                BizareChatApp.getInstance().getContentService(),
                                BizareChatApp.getInstance().getCacheUsersPhotos())));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base_layout);
        messageServiceIntent = new Intent(this, BizareChatMessageService.class);

        if (!CurrentUser.getInstance().isSubscribed()) {
            presenter.sendSubscriptionToServer();
            presenter.updateDialogsDao();
        }

        if (CurrentUser.getInstance().getStringAvatar() == null
                && CurrentUser.getInstance().getAvatarBlobId() != null) {
            presenter.loadUserAvatar();
        }

        if (!isMyServiceRunning(BizareChatMessageService.class)) {
            startService(new Intent(this, BizareChatMessageService.class));
        }

        findViews();
        setToolbarAndNavigationDrawer();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setUserInformation();
        if (!presenter.isLaunched()) {
            showPublicDialogs();
            presenter.setLaunched(true);
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (presenter.isPrivateDialogsOnScreen()) {
                mTabLayout.getTabAt(1).select();
            }
        }
    }

    public BizareChatMessageService getMessageService() {
        return messageService;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUserInformation() {

        View headerView = mNavigationView.getHeaderView(0);
        headerView.findViewById(R.id.nav_header_root).setOnClickListener(this);
        if (currentUser == null) {
            currentUser = CurrentUser.getInstance();
        }

        TextView email = (TextView) headerView.findViewById(R.id.header_email);
        if (currentUser.getCurrentEmail() != null) {
            email.setText(currentUser.getCurrentEmail());
        }

        drawerLogin = (TextView) headerView.findViewById(R.id.user_login);
        drawerLogin.setText(CurrentUser.getInstance().getFullName());

        drawerAvatar = (CircleImageView) headerView.findViewById(R.id.user_pic);
        if (converter == null) {
            converter = new Converter(getApplicationContext());
        }

        String s = CurrentUser.getInstance().getStringAvatar();
        Bitmap bitmap = converter.decodeBase64(s);
        if (bitmap != null) {
            drawerAvatar.setImageBitmap(bitmap);
        }

    }

    @Override
    public void setAvatarImage(Bitmap image) {
        drawerAvatar.setImageBitmap(image);
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

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    showPublicDialogs();
                    return;
                }
                if (tab.getPosition() == 1) {
                    showPrivateDialogs();
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
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        toolbarParams = (AppBarLayout.LayoutParams) toolbarLayout.getLayoutParams();
        setSupportActionBar(mToolbar);
        mTextOnToolbar = (TextView) findViewById(R.id.chat_toolbar_title);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        progressBar = (ProgressBar) findViewById(R.id.main_progress_bar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void showInviteFriendsScreen() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(INVITE_FRIENDS_FR_TAG);
        if (fragment != null) {
            transaction.replace(R.id.main_screen_container, fragment, INVITE_FRIENDS_FR_TAG)
                    .commit();
            return;
        }

        transaction.replace(R.id.main_screen_container, new InviteFriendsFragment(),
                INVITE_FRIENDS_FR_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        closeDrawer();
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
            case R.id.settings:
                presenter.navigateToSettingsScreen();
                return true;
            case R.id.invite_users:
                presenter.inviteFriends();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void showSettingsScreen() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SETTINGS_FR_TAG);
        if (fragment != null) {
            getSupportFragmentManager().popBackStack();
            transaction.replace(R.id.main_screen_container, fragment, SETTINGS_FR_TAG)
                    .commit();
            return;
        }

        transaction.replace(R.id.main_screen_container, new SettingsFragment(), SETTINGS_FR_TAG)
                .addToBackStack(NEW_CHAT_FR_TAG)
                .commit();
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
    public void showUserInfo() {
        Fragment fragment = new UserInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putLong(UserInfoFragment.ID_BUNDLE_KEY, CurrentUser.getInstance().getCurrentUserId());
        bundle.putString(UserInfoFragment.EMAIL_BUNDLE_KEY, CurrentUser.getInstance().getCurrentEmail());
        bundle.putString(UserInfoFragment.PHONE_BUNDLE_KEY, CurrentUser.getInstance().getPhone());
        bundle.putString(UserInfoFragment.WEBSITE_BUNDLE_KEY, CurrentUser.getInstance().getWebsite());
        bundle.putString(UserInfoFragment.FULL_NAME_BUNDLE_KEY, CurrentUser.getInstance().getFullName());
        bundle.putParcelable(UserInfoFragment.AVATAR_BUNDLE_KEY, ((BitmapDrawable) drawerAvatar.getDrawable()).getBitmap());
        fragment.setArguments(bundle);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet sharedElementTransition = (TransitionSet) TransitionInflater
                    .from(this)
                    .inflateTransition(R.transition.user_shared_element);
            TransitionSet userInfoTransitionIn = (TransitionSet) TransitionInflater
                    .from(this)
                    .inflateTransition(R.transition.user_info_transition_in);
            TransitionSet userInfoTransitionOut = (TransitionSet) TransitionInflater
                    .from(this)
                    .inflateTransition(R.transition.user_info_transition_out);
            fragment.setSharedElementEnterTransition(sharedElementTransition);
            fragment.setEnterTransition(userInfoTransitionIn);
            fragment.setReturnTransition(userInfoTransitionOut);
            fragment.setSharedElementReturnTransition(sharedElementTransition);

        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_screen_container, fragment)
                .addSharedElement(drawerAvatar, getString(R.string.transition_user_avatar_name))
                .addSharedElement(drawerLogin, getString(R.string.transition_user_full_name))
                .addToBackStack(null)
                .commit();
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
                break;
            case R.id.nav_header_root:
                presenter.showCurrentUserInfo();
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void hideEmptyScreen() {
        findViewById(R.id.layout_chat_empty).setVisibility(View.GONE);
    }

    @Override
    public void showEmptyScreen() {
        findViewById(R.id.layout_chat_empty).setVisibility(View.VISIBLE);
    }

    @Override
    public void showLackOfFriends() {
        Snackbar.make(mDrawer, R.string.no_friends_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startNewChatView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(NEW_CHAT_FR_TAG);
        if (fragment != null) {
            getSupportFragmentManager().popBackStack();
            transaction.replace(R.id.main_screen_container, fragment, NEW_CHAT_FR_TAG)
                    .commit();
            return;
        }

        transaction.replace(R.id.main_screen_container, new NewChatFragment(), NEW_CHAT_FR_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startUsersView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(USERS_FR_TAG);
        if (fragment != null) {
            getSupportFragmentManager().popBackStack();
            transaction.replace(R.id.main_screen_container, fragment, USERS_FR_TAG)
                    .commit();
            return;
        }

        transaction.replace(R.id.main_screen_container, new UsersFragment(), USERS_FR_TAG)
                .addToBackStack(NEW_CHAT_FR_TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void startBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            presenter.showNavigationElements();
            getSupportFragmentManager().popBackStackImmediate();
            if (presenter.isPrivateDialogsOnScreen()) {
                mTabLayout.getTabAt(1).select();
            } else {
                mTabLayout.getTabAt(0).select();
            }
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void showPublicDialogs() {
        presenter.setPrivateDialogsOnScreen(false);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(PUBLIC_DIALOGS_FR_TAG);
        if (fragment != null) {
            ((PublicDialogsFragment) fragment).setDialogClickListener(this);
            transaction.replace(R.id.main_screen_container, fragment, PUBLIC_DIALOGS_FR_TAG)
                    .commit();
            return;
        }

        PublicDialogsFragment publicDialogsFragment = new PublicDialogsFragment();
        publicDialogsFragment.setDialogClickListener(this);
        transaction.replace(R.id.main_screen_container, publicDialogsFragment,
                PUBLIC_DIALOGS_FR_TAG)
                .commit();
    }

    @Override
    public void showPrivateDialogs() {
        presenter.setPrivateDialogsOnScreen(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(PRIVATE_DIALOGS_FR_TAG);
        if (fragment != null) {
            ((PrivateDialogsFragment) fragment).setDialogClickListener(this);
            transaction.replace(R.id.main_screen_container, fragment, PRIVATE_DIALOGS_FR_TAG)
                    .commit();
            return;
        }

        PrivateDialogsFragment privateDialogsFragment = new PrivateDialogsFragment();
        privateDialogsFragment.setDialogClickListener(this);
        transaction.replace(R.id.main_screen_container, privateDialogsFragment,
                PRIVATE_DIALOGS_FR_TAG)
                .commit();
    }

    @Override
    public void onPrivateDialogClick(DialogModel dialog) {
        presenter.navigateToPrivateChatRoom(dialog);
    }

    @Override
    public void onPublicDialogClick(DialogModel dialog) {
        presenter.navigateToPublicChatRoom(dialog);
    }

    @Override
    public void showPrivateChatRoom(DialogModel dialogModel) {
        String tag = CHAT_ROOM_FR_TAG + dialogModel.getDialogId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            transaction.replace(R.id.main_screen_container, fragment, tag)
                    .commit();
            return;
        }

        fragment = new ChatRoomFragment();
        Bundle args = new Bundle();
        args.putString(ChatRoomFragment.DIALOG_ID_BUNDLE_KEY, dialogModel.getDialogId());
        args.putLong(ChatRoomFragment.DIALOG_ADMIN_BUNDLE_KEY, dialogModel.getAdminId());
        args.putString(ChatRoomFragment.DIALOG_NAME_BUNDLE_KEY, dialogModel.getName());
        args.putInt(ChatRoomFragment.DIALOG_TYPE_BUNDLE_KEY, dialogModel.getType());
        args.putString(ChatRoomFragment.DIALOG_ROOM_JID_BUNDLE_KEY, dialogModel.getXmppRoomJid());
        ArrayList<Integer> list = new ArrayList<>(dialogModel.getOccupantsIds());
        args.putIntegerArrayList(ChatRoomFragment.OCCUPANTS_IDS_BUNDLE_KEY, list);

        fragment.setArguments(args);
        transaction.replace(R.id.main_screen_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showPublicChatRoom(DialogModel dialogModel) {
        String tag = CHAT_ROOM_FR_TAG + dialogModel.getDialogId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            transaction.replace(R.id.main_screen_container, fragment, tag)
                    .commit();
            return;
        }

        fragment = new ChatRoomFragment();
        Bundle args = new Bundle();
        args.putString(ChatRoomFragment.DIALOG_ID_BUNDLE_KEY, dialogModel.getDialogId());
        args.putLong(ChatRoomFragment.DIALOG_ADMIN_BUNDLE_KEY, dialogModel.getAdminId());
        args.putString(ChatRoomFragment.DIALOG_NAME_BUNDLE_KEY, dialogModel.getName());
        args.putInt(ChatRoomFragment.DIALOG_TYPE_BUNDLE_KEY, dialogModel.getType());
        args.putString(ChatRoomFragment.DIALOG_ROOM_JID_BUNDLE_KEY, dialogModel.getXmppRoomJid());
        ArrayList<Integer> list = new ArrayList<>(dialogModel.getOccupantsIds());
        args.putIntegerArrayList(ChatRoomFragment.OCCUPANTS_IDS_BUNDLE_KEY, list);

        fragment.setArguments(args);
        transaction.replace(R.id.main_screen_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    private void startActionBarToggleAnim(float start, float end) {
        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.addUpdateListener(valueAnimator -> {
            float slideOffset = (Float) valueAnimator.getAnimatedValue();
            toggle.onDrawerSlide(null, slideOffset);

            if (slideOffset == 1 && start == 0) {
                toggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            if (slideOffset == 0 && start == 1) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.setDrawerIndicatorEnabled(true);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(200);
        anim.start();
    }

    @Override
    public void showNavigationElements() {
        fab.show();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            mTextOnToolbar.setText(getString(R.string.chat));
            mNavigationView.getMenu().getItem(0).setChecked(true);
            mNavigationView.getMenu().getItem(0).setChecked(false);
        }
        toolbarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        mTabLayout.setVisibility(View.VISIBLE);
        startActionBarToggleAnim(1, 0);
    }

    @Override
    public void closeDrawer() {
        mDrawer.closeDrawer(GravityCompat.START, true);
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
        stopService(new Intent(this, BizareChatMessageService.class));
        navigator.navigateToLoginActivity(this);
    }

    @SuppressWarnings("unchecked")
    private void bindMessageService() {
        messageServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                messageService = ((MessageServiceBinder<BizareChatMessageService>) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                messageService = null;
            }
        };
        bindService(messageServiceIntent, messageServiceConnection, 0);
    }

    private void unbindMessageService() {
        unbindService(messageServiceConnection);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onGsmMessageReceived(GcmMessageReceivedEvent event) {
        Log.d(TAG, event.getMessage());
    }

    @Override
    protected void onStart() {
        super.onStart();
        NotificationUtils.clearNotifications(getApplicationContext());
        EventBus.getDefault().register(this);
        bindMessageService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        unbindMessageService();
    }


}