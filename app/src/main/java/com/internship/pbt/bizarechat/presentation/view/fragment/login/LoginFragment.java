package com.internship.pbt.bizarechat.presentation.view.fragment.login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
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
import com.internship.pbt.bizarechat.presentation.view.fragment.register.RegistrationFragment;


public class LoginFragment extends BaseFragment implements LoginView {
    private LoginPresenter loginPresenter;

    private Button signIn;
    private Button signUp;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView forgotPasswordTextView;
    private ProgressBar progressBar;

    public LoginFragment() {
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        signIn = (Button) view.findViewById(R.id.sign_in);
        signUp = (Button) view.findViewById(R.id.sign_up);
        emailEditText = (EditText) view.findViewById(R.id.email);
        passwordEditText = (EditText) view.findViewById(R.id.password);
        forgotPasswordTextView = (TextView) view.findViewById(R.id.forgot_password);
        // progressBar = (ProgressBar) view.findViewById(R.id.progress_bar); TODO Find this

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
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString()));

        signUp.setOnClickListener(v -> loginPresenter.goToRegistration());

        forgotPasswordTextView.setOnClickListener(v -> loginPresenter.onPasswordForgot());
    }

    @Override
    public void showError(String message) {
        if (getView() != null)
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginPresenter.destroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView txtView = (TextView) getActivity().findViewById(R.id.toolbar_title);
        txtView.setText(R.string.sign_in);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetTokenUseCase getToken = new GetTokenUseCase(
                new SessionDataRepository(),
                JobExecutor.getInstance(),
                UiThread.getInstance()
        );
        loginPresenter = new LoginPresenterImpl(getToken);
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
    public void showForgotPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle(R.string.restore_password);

        final TextInputEditText email = new TextInputEditText(getActivity());
        email.setHintTextColor(getActivity().getResources().getColor(R.color.email_hint));
        email.setTextColor(getActivity().getResources().getColor(R.color.black));
        email.setHint(R.string.email_address);
        builder.setView(email);

        builder.setPositiveButton(R.string.send_email, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String userEmail = String.valueOf(email.getText());
                // Do something with value!
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        AlertDialog dialog = builder.show();

        Button buttonSend = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonSend.setEnabled(false);

        email.addTextChangedListener(new TextWatcher() {
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
                if (email.getText().length() == 0)
                    buttonSend.setEnabled(false);
            }
        });

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
    public void onLoginSuccess() {

    }

    @Override
    public void hideRetry() {

    }


}
