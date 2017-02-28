package com.internship.pbt.bizarechat.presentation.view.fragment.userinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.userinfo.UserInfoPresenter;


public class UserInfoFragment extends MvpAppCompatFragment
        implements UserInfoView, View.OnClickListener {
    public static final String EMAIL_BUNDLE_KEY = "userEmail";
    public static final String PHONE_BUNDLE_KEY = "userPhone";
    public static final String WEBSITE_BUNDLE_KEY = "userWebsite";
    public static final String FULL_NAME_BUNDLE_KEY = "userFullName";
    public static final String AVATAR_BUNDLE_KEY = "userAvatar";

    private PackageManager packageManager;
    private ImageView avatarImage;
    private Bundle arguments;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView websiteTextView;
    private String email;
    private String phone;
    private String website;
    private String fullName;
    private FloatingActionButton userInfoFab;
    private CollapsingToolbarLayout toolbarLayout;
    private TextView titleShadow;

    @InjectPresenter
    UserInfoPresenter presenter;

    @ProvidePresenter
    UserInfoPresenter provideUserInfoPresenter(){
        return new UserInfoPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        arguments = getArguments();
        email = arguments.getString(EMAIL_BUNDLE_KEY);
        phone = arguments.getString(PHONE_BUNDLE_KEY);
        website = arguments.getString(WEBSITE_BUNDLE_KEY);
        fullName = arguments.getString(FULL_NAME_BUNDLE_KEY);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.user_toolbar);
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(toolbar.getContext(),
                null, android.support.v7.appcompat.R.styleable.ActionBar, android.support.v7.appcompat.R.attr.actionBarStyle, 0);
        toolbar.setNavigationIcon(a.getDrawable(android.support.v7.appcompat.R.styleable.ActionBar_homeAsUpIndicator));
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        toolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.user_collapsing_toolbar_layout);
        emailTextView = (TextView) view.findViewById(R.id.user_info_email_value);
        phoneTextView = (TextView) view.findViewById(R.id.user_info_phone_value);
        websiteTextView = (TextView) view.findViewById(R.id.user_info_website_value);
        userInfoFab = (FloatingActionButton) view.findViewById(R.id.user_info_fab);
        toolbarLayout.setTitleEnabled(true);
        toolbarLayout.setTitle(fullName);

        view.findViewById(R.id.user_info_email_layout).setOnClickListener(this);
        view.findViewById(R.id.user_info_phone_layout).setOnClickListener(this);
        view.findViewById(R.id.user_info_website_layout).setOnClickListener(this);
        userInfoFab.setOnClickListener(this);

        packageManager = getActivity().getPackageManager();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avatarImage = (ImageView)view.findViewById(R.id.user_collapsing_toolbar_image);
        avatarImage.setImageBitmap(arguments.getParcelable(AVATAR_BUNDLE_KEY));
        if (!TextUtils.isEmpty(email))
            emailTextView.setText(email);
        if (!TextUtils.isEmpty(phone))
            phoneTextView.setText(phone);
        if (!TextUtils.isEmpty(website))
            websiteTextView.setText(website);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_info_email_layout:
                presenter.sendEmail(email);
                break;
            case R.id.user_info_phone_layout:
                presenter.dialPhoneNumber(phone);
                break;
            case R.id.user_info_website_layout:
                presenter.openWebsite(website);
                break;
            case R.id.user_info_fab:
                //TODO implement start chat with user
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.collapsing_toolbar_layout).setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().findViewById(R.id.collapsing_toolbar_layout).setVisibility(View.VISIBLE);
        avatarImage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startSendEmail(String email){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        if(emailIntent.resolveActivity(packageManager) != null)
            startActivity(emailIntent);
        else
            Snackbar.make(emailTextView, R.string.no_email_apps_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startDialPhoneNumber(String number){
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                "tel", number, null));
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)){
            if(((TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE))
                    .getSimState() == TelephonyManager.SIM_STATE_READY){
                if(Settings.Global.getInt(getContext().getContentResolver(),
                        Settings.Global.AIRPLANE_MODE_ON, 0) == 0) {
                    startActivity(phoneIntent);
                    return;
                }
            }
        }
        Snackbar.make(phoneTextView, R.string.calls_unavailable_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startOpenWebsite(String website){
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
        if(websiteIntent.resolveActivity(packageManager) != null)
            startActivity(websiteIntent);
        else
            Snackbar.make(websiteTextView, R.string.no_web_apps_error, Snackbar.LENGTH_SHORT).show();
    }
}
