package com.internship.pbt.bizarechat.presentation.view.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.fragment.BaseFragment;

public class MainFragment extends BaseFragment { //TODO Change to better name

    private Button logoutTestButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        logoutTestButton = (Button) view.findViewById(R.id.loggout_test_button);
        logoutTestButton.setOnClickListener(l ->{
            UserToken.getInstance().deleteToken();
            CurrentUser.getInstance().setAuthorized(false);
            CurrentUser.getInstance().clearCurrentUser();
        });

        return view;
    }
}
