package com.internship.pbt.bizarechat.presentation.view.fragment.login;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.ResetPasswordUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.login.LoginPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.fragment.BaseFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationFragment;


public class LoginFragment extends BaseFragment implements LoginView {
    public static final int notifID = 33;
    private LoginPresenter loginPresenter;
    private Button signIn;
    private Button signUp;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView forgotPasswordTextView;
    private AlertDialog dialog;
    private TextInputEditText emailEditTextInPasswordRecovery;
    private ProgressBar progressBar;
    private CheckBox keepMeSignIn;
    private NotificationManager notificationManager;
    private OnLoginSuccess onLoginSuccess;

    // TODO: 1/30/17 [Code Review] is there some good reason to set retainInstance to true?
    public LoginFragment() {
        setRetainInstance(true);
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginSuccess) {
            Log.d("123", "OnAttach");
            this.onLoginSuccess = (OnLoginSuccess) context;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23)
            if (activity instanceof OnLoginSuccess) {
                Log.d("123", "OnAttach");
                this.onLoginSuccess = (OnLoginSuccess) activity;
            }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (onLoginSuccess != null)
            onLoginSuccess = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);

        loginPresenter.setLoginView(this);

        addTextListener();
        setButtonListeners();

        return view;
    }

    private void addTextListener() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginPresenter.checkFieldsAndSetButtonState(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        emailEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
    }

    private void setButtonListeners() {
        signIn.setOnClickListener(
                v -> loginPresenter.requestLogin(
                        emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString()));

        signUp.setOnClickListener(v -> loginPresenter.goToRegistration());

        forgotPasswordTextView.setOnClickListener(v -> loginPresenter.onForgotPasswordClicked());

        keepMeSignIn.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked)
                loginPresenter.onKeepMeSignInFalse();
        });
    }

    @Override
    public void showError(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginPresenter.destroy();
    }

    @Override
    public Context getContextActivity() {
        return getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResetPasswordUseCase resetPassword = new ResetPasswordUseCase(
                new UserDataRepository(
                        BizareChatApp.getInstance().getUserService()));

        loginPresenter = new LoginPresenterImpl(
                resetPassword,
                new SessionDataRepository(
                        BizareChatApp.getInstance().getSessionService()));
    }

    @Override
    public void navigateToRegistration() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right,
                        R.animator.enter_from_right, R.animator.exit_to_left)
                .replace(R.id.activity_layout_fragment_container, new RegistrationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showCheckBoxModalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle(R.string.keep_me_sign_in);
        builder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void showForgotPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle(R.string.restore_password);

        emailEditTextInPasswordRecovery = new TextInputEditText(getActivity());
        emailEditTextInPasswordRecovery.setHintTextColor(getActivity().getResources().getColor(R.color.email_hint));
        emailEditTextInPasswordRecovery.setTextColor(getActivity().getResources().getColor(R.color.black));
        emailEditTextInPasswordRecovery.setHint(R.string.email_address);

        builder.setPositiveButton(R.string.send_email, (dialog1, whichButton) -> {

        });

        builder.setNegativeButton(R.string.cancel, (dialog12, whichButton) -> dialog12.dismiss());

        dialog = builder.create();
        dialog.setView(emailEditTextInPasswordRecovery, 30, 30, 30, 0);
        dialog.show();

        Button buttonSend = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonSend.setOnClickListener(
                v -> loginPresenter
                        .checkIsEmailValid(emailEditTextInPasswordRecovery
                                .getText().toString())
        );
        buttonSend.setEnabled(false);

        emailEditTextInPasswordRecovery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0)
                    buttonSend.setEnabled(true);
                if (emailEditTextInPasswordRecovery.getText().length() == 0)
                    buttonSend.setEnabled(false);
            }
        });
    }

    @Override
    public void showErrorOnPasswordRecovery() {
        emailEditTextInPasswordRecovery.setError(getString(R.string.invalid_email));
    }

    @Override
    public void showSuccessOnPasswordRecovery() {
        dialog.cancel();
        if (getView() != null)
            Snackbar.make(getView(), R.string.password_recovery_sent, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setButtonSignInEnabled(boolean enabled) {
        signIn.setEnabled(enabled);
    }

    @Override
    public void setPresenter(LoginPresenter presenter) {
        this.loginPresenter = presenter;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void NavigateToMainActivity() {
        this.onLoginSuccess.onLoginSuccess();
    }

    @Override
    public void hideRetry() {

    }

    private void stopNotification() {
        notificationManager.cancel(notifID);
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.resume();
        stopNotification();
    }

    private void initView(View view) {
        signIn = (Button) view.findViewById(R.id.sign_in);
        signUp = (Button) view.findViewById(R.id.sign_up);
        emailEditText = (EditText) view.findViewById(R.id.email);
        passwordEditText = (EditText) view.findViewById(R.id.password);
        forgotPasswordTextView = (TextView) view.findViewById(R.id.forgot_password);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
        keepMeSignIn = (CheckBox) view.findViewById(R.id.keep_me_check);
        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        TextView txtView = (TextView) getActivity().findViewById(R.id.toolbar_title);
        txtView.setText(R.string.sign_in);
    }

    public interface OnLoginSuccess {
        void onLoginSuccess();
    }
}
