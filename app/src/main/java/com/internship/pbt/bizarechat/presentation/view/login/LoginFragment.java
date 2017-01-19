package com.internship.pbt.bizarechat.presentation.view.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.BasicFragment;

public class LoginFragment extends BasicFragment implements LoginView{
    private LoginPresenter loginPresenter;

    private Button signIn;
    private Button signUp;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView forgotPasswordTextView;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        signIn = (Button)view.findViewById(R.id.sign_in);
        signUp = (Button)view.findViewById(R.id.sign_up);
        emailEditText = (EditText)view.findViewById(R.id.email);
        passwordEditText = (EditText)view.findViewById(R.id.password);
        forgotPasswordTextView = (TextView)view.findViewById(R.id.forgot_password);

        addTextListener();
        setButtonListeners();

        return view;
    }

    private void addTextListener(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginPresenter.checkFieldsAndSetButtonState(emailEditText.getText(),
                        passwordEditText.getText());
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        emailEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
    }

    private void setButtonListeners(){
        signIn.setOnClickListener(
                v -> loginPresenter.requestLogin(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString()));

        signUp.setOnClickListener(v -> loginPresenter.goToRegistration());

        forgotPasswordTextView.setOnClickListener(v -> loginPresenter.onPasswordForgot());
    }

    @Override public void showError(String message) {
        if(getView() != null)
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override public void onResume() {
        super.onResume();
        loginPresenter = new LoginPresenterImpl();
        loginPresenter.setLoginView(this);
    }

    @Override public void navigateToRegistration() {
        //TODO after registration fragment creation need to be implemented
//        getActivity().getFragmentManager().beginTransaction()
//                .replace(R.id.activityLayoutFragmentContainer, new RegistrationFragment())
//                .commit();
    }

    @Override public void showForgotPassword() {
        //TODO need to be implemented
    }

    @Override public void setButtonSignInEnabled(boolean enabled) {
        signIn.setEnabled(enabled);
    }

    @Override public void setPresenter(LoginPresenter presenter) {
        this.loginPresenter = presenter;
    }

    @Override public void showLoading() {

    }

    @Override public void hideLoading() {

    }

    @Override public void showRetry() {

    }

    @Override public void hideRetry() {

    }

}
