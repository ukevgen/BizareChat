package com.internship.pbt.bizarechat.presentation.view.fragment.settings;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.BuildConfig;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.presenter.settings.SettingsPresenter;

public class SettingsFragment extends MvpAppCompatFragment
        implements SettingsView, CompoundButton.OnCheckedChangeListener {
    @InjectPresenter
    SettingsPresenter presenter;

    @ProvidePresenter
    SettingsPresenter provideSettingsPresenter(){
        return new SettingsPresenter();
    }

    private TextView toolbarTitle;
    private TextView version;
    private SwitchCompat notifications;
    private ProgressBar progressBar;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        version = (TextView)view.findViewById(R.id.settings_version);
        version.setText(String.format("v%s", BuildConfig.VERSION_NAME));
        notifications = (SwitchCompat)view.findViewById(R.id.settings_toggle_button);
        notifications.setChecked(CurrentUser.getInstance().isNotificationsOn());
        notifications.setOnCheckedChangeListener(this);
        toolbarTitle = (TextView)getActivity().findViewById(R.id.chat_toolbar_title);
        progressBar = (ProgressBar)getActivity().findViewById(R.id.main_progress_bar);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        presenter.setNotificationsState(isChecked);
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbarTitle.setText(R.string.settings_title);
    }

    @Override
    public void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }
}
