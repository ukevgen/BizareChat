package com.internship.pbt.bizarechat.presentation.view.fragment.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.fragment.BaseFragment;

import rx.Observable;

public class RegistrationFragment extends BaseFragment implements RegistrationView, View.OnClickListener {

    private RegistrationPresenter mRegistrationPresenter;

    private TextInputLayout mEmailLayout;
    private TextInputLayout mPasswordLayout;
    private TextInputLayout mPhoneLayout;

    private TextInputEditText mEmailEditText;
    private TextInputEditText mPasswordEditText;
    private TextInputEditText mPhoneEditText;

    private Button mButton;

    public interface OnRegisterSuccess {
        void onRegisterSuccess();
    }

    private OnRegisterSuccess mOnRegisterSuccess;

    @Override public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnRegisterSuccess)
            mOnRegisterSuccess = (OnRegisterSuccess) context;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegistrationPresenter = new RegistrationPresenterImpl(this);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mEmailLayout = (TextInputLayout) v.findViewById(R.id.text_input_email);
        mPasswordLayout = (TextInputLayout) v.findViewById(R.id.text_input_password);
        mPhoneLayout = (TextInputLayout) v.findViewById(R.id.text_input_phone);

        mEmailEditText = (TextInputEditText) v.findViewById(R.id.register_email);
        mPasswordEditText = (TextInputEditText) v.findViewById(R.id.register_password);
        mPhoneEditText = (TextInputEditText) v.findViewById(R.id.register_phone);

        mButton = (Button) v.findViewById(R.id.register_sign_up);
        mButton.setOnClickListener(this);

        return v;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView txtView = (TextView)getActivity().findViewById(R.id.toolbar_title);
        txtView.setText(R.string.sign_up);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        mRegistrationPresenter.destroy();
    }

    @Override public void showLoading() {

    }

    @Override public void hideLoading() {

    }

    @Override public void showRetry() {

    }

    @Override public void hideRetry() {

    }

    @Override public void showError(String message) {
    }

    @Override public void hideErrorInvalidEmail() {
        mEmailLayout.setError("");
    }

    @Override public void hideErrorInvalidPassword() {
        mPasswordLayout.setError("");

    }

    @Override public void hideErrorInvalidPhone() {
        mPhoneLayout.setError("");

    }

    @Override public void showErrorInvalidEmail() {
        mEmailLayout.setError(getString(R.string.invalid_email));
    }

    @Override public void showErrorInvalidPassword() {
        mPasswordLayout.setError(getString(R.string.invalid_weak_password));
    }

    @Override public void showErrorInvalidPhone() {
        mPhoneLayout.setError(getString(R.string.invalid_phone));
    }

    @Override public void showErrorPasswordLength() {
        mPasswordLayout.setError(getString(R.string.error_password_length));
    }

    @Override public void loginFacebook() {

    }

    @Override public void onRegistrationSuccess() {
        mOnRegisterSuccess.onRegisterSuccess();
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_sign_up:
                this.getInformationForValidation();
                break;
        }
    }

    @Override public void getInformationForValidation() {
        ValidationInformation validationInformation = new ValidationInformation();
        validationInformation.setEmail(mEmailEditText.getText().toString());
        validationInformation.setPassword(mPasswordEditText.getText().toString());
        validationInformation.setPhone(mPhoneEditText.getText().toString());
        mRegistrationPresenter.validateInformation(Observable.just(validationInformation));
    }
}