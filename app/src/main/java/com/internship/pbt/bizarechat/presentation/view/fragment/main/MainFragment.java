package com.internship.pbt.bizarechat.presentation.view.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.presenter.main.MainPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.main.MainPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.fragment.BaseFragment;

public class MainFragment extends BaseFragment implements MainView { //TODO Change to better name

    private Button logoutTestButton;
    private MainPresenter mainPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mainPresenter = new MainPresenterImpl();
        mainPresenter.setMainView(this);

        Toast.makeText(getActivity(), " " + CurrentUser.getInstance().getAvatarBlobId(), Toast.LENGTH_LONG).show();

        logoutTestButton = (Button) view.findViewById(R.id.loggout_test_button);
        logoutTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View l) {
                mainPresenter.logout();
            }
        });

        return view;
    }

    @Override
    public void displayUsersChats() {

    }

    @Override
    public void showInviteFriendsScreen() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }
}
