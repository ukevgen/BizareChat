package com.internship.pbt.bizarechat.presentation.view.fragment.login;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.executor.JobExecutor;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.GetTokenUseCase;
import com.internship.pbt.bizarechat.presentation.UiThread;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;

import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.fragment.BaseFragment;


public class LoginFragment extends BaseFragment implements LoginView {
    private LoginPresenter loginPresenter;

    private Button signIn;
    private Button signUp;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView forgotPasswordTextView;
    private ProgressBar progressBar;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        signIn = (Button)view.findViewById(R.id.sign_in);
        signUp = (Button)view.findViewById(R.id.sign_up);
        emailEditText = (EditText)view.findViewById(R.id.email);
        passwordEditText = (EditText)view.findViewById(R.id.password);
        forgotPasswordTextView = (TextView)view.findViewById(R.id.forgot_password);
       // progressBar = (ProgressBar) view.findViewById(R.id.progress_bar); TODO Find this

        addTextListener();
        setButtonListeners();

        return view;
    }

    private void addTextListener(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginPresenter.checkFieldsAndSetButtonState(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
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

    @Override public void onStart() {
        super.onResume();
        GetTokenUseCase getToken= new GetTokenUseCase(
                new SessionDataRepository(),
                JobExecutor.getInstance(),
                UiThread.getInstance()
        );
        loginPresenter = new LoginPresenterImpl(getToken);
        loginPresenter.setLoginView(this);
    }

    @Override public void onStop() {
        super.onStop();
        loginPresenter.destroy();
        loginPresenter = null;
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
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override public void showRetry() {

    }

    @Override public void onLoginSuccess() {

    }

    @Override public void hideRetry() {

    }



}
