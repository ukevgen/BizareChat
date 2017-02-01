package com.internship.pbt.bizarechat.presentation.view.fragment.register;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpUserM;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.model.FacebookLinkInform;
import com.internship.pbt.bizarechat.presentation.model.RegistrationModel;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenterImpl;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.activity.MainActivity;
import com.internship.pbt.bizarechat.presentation.view.fragment.BaseFragment;

import java.io.File;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import ru.tinkoff.decoro.watchers.FormatWatcher;

import static android.app.Activity.RESULT_OK;

public class RegistrationFragment extends BaseFragment implements RegistrationView, View.OnClickListener {

    private static final String PACKAGE_PATH = "com.internship.pbt.bizarechat.presentation.view.fragment.register";
    private final int DEVICE_CAMERA = 0;
    private final int PHOTO_GALLERY = 1;

    private RegistrationPresenter mRegistrationPresenter;

    private TextInputLayout mEmailLayout,
            mPasswordLayout,
            mPasswordConfLayout,
            mPhoneLayout;
    private FrameLayout mImageWrapper;

    private ProgressBar mProgressBar;

    private EditText mEmailEditText,
            mFullName,
            mWebSite;

    private TextInputEditText mPasswordEditText,
            mPasswordConfirm,
            mPhoneEditText;

    private Button mSignUpButton,
            mFacebookLinkButton;
    private OnRegisterSuccess mOnRegisterSuccess;


    private CircleImageView mAvatarImage;

    private SignUpUserM userModel;


    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterSuccess) {
            mOnRegisterSuccess = (OnRegisterSuccess) context;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23)
            if (activity instanceof OnRegisterSuccess) {
                mOnRegisterSuccess = (OnRegisterSuccess) activity;
            }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mOnRegisterSuccess != null)
            mOnRegisterSuccess = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("123", "Fragment OnCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("123", "Fragment OnCreateView");
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mRegistrationPresenter = new RegistrationPresenterImpl(
                new RegistrationModel(),
                new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        BizareChatApp.getInstance().getCache()),
                new SessionDataRepository(
                        BizareChatApp.getInstance().getSessionService()));
        mRegistrationPresenter.setRegistrationView(this);

        init(v);


        mFacebookLinkButton.setOnClickListener(this);
        mImageWrapper.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);

        mRegistrationPresenter.logoutFacebookSdk();
        mRegistrationPresenter.setCallbackToLoginFacebookButton();
        return v;
    }


    @Override
    public void addPhoneNumberFormatting(FormatWatcher formatWatcher) {
        formatWatcher.installOn(mPhoneEditText);
        mPhoneEditText.setSelection(mPhoneEditText.getText().length());
    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView txtView = (TextView) getActivity().findViewById(R.id.toolbar_title);
        txtView.setText(R.string.sign_up);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRegistrationPresenter.destroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        hideLoading();
        mRegistrationPresenter.stop();
    }

    @Override
    public void startFailedSignUpAnim() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void startOnFacebookLinkSuccessAnim() {
        mFacebookLinkButton.setEnabled(false);
        mFacebookLinkButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.disappear));
        mFacebookLinkButton.setText(getText(R.string.linked));
        mFacebookLinkButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.appear));
    }

    @Override
    public void startOnFailedFacebooLinkkAnim() {
    }

    @Override
    public void startSuccessSignUpAnim() {

    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
    }

    @Override
    public void showError(String message) {
        if (getView() != null)
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override

    public void hideErrorInvalidEmail() {
        mEmailLayout.setError(null);
    }

    @Override
    public void hideErrorInvalidPassword() {
        mPasswordLayout.setError(null);
    }


    public void hideErrorPasswordConfirm() {
        mPasswordConfLayout.setError(null);
    }

    @Override
    public void hideErrorInvalidPhone() {
        mPhoneLayout.setError(null);
    }

    @Override
    public void showErrorInvalidEmail() {
        mEmailLayout.setError(getString(R.string.invalid_email));
    }

    @Override
    public void showErrorInvalidPassword() {
        mPasswordLayout.setError(getString(R.string.invalid_weak_password));
    }

    @Override
    public void showErrorInvalidPhone() {
        mPhoneLayout.setError(getString(R.string.invalid_phone));
    }

    @Override
    public void showErrorPasswordLength() {
        mPasswordLayout.setError(getString(R.string.error_password_length));
    }

    @Override
    public void refreshInfAfterFacebookLink(FacebookLinkInform linkInform) {
        Log.d("123", "RegistrationFragment" + linkInform.toString());
        this.startOnFacebookLinkSuccessAnim();
    }

    @Override
    public void onRegistrationSuccess() {
        mOnRegisterSuccess.onRegisterSuccess();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_sign_up:
                this.getInformationForValidation();
                break;
            case R.id.image_wrapper:
                this.showPictureChooser();
                break;
            case R.id.login_facebook_button:
                this.facebookLoginWithPermissions();
                break;
        }
    }

    @Override
    public void showPictureChooser() {
        final CharSequence[] items = {getText(R.string.device_camera),
                getText(R.string.photo_gallery)};
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getText(R.string.choose_source_for_getting_image));
        builder.setNegativeButton(R.string.back, null);
        builder.setItems(items, (dialogInterface, i) -> {
            if (items[i].equals(getText(R.string.device_camera))) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, DEVICE_CAMERA);
            } else if (items[i].equals(getText(R.string.photo_gallery))) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, PHOTO_GALLERY);
            }
        });
        builder.show();
    }

    @Override
    public void getInformationForValidation() {
        if (userModel == null)
            userModel = new SignUpUserM();
        userModel.setEmail(mEmailEditText.getText().toString());
        userModel.setFullName(mFullName.getText().toString());
        userModel.setPassword(mPasswordEditText.getText().toString());
        userModel.setWebsite(mWebSite.getText().toString());
        userModel.setPhone(mPhoneEditText.getText().toString());

        userModel.setFacebookId(CurrentUser.getInstance().getCurrentFacebookId());

        String passwordConf = mPasswordConfirm.getText().toString();

        mRegistrationPresenter.validateInformation(userModel, passwordConf);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null && resultCode == RESULT_OK && requestCode == DEVICE_CAMERA) {
            mRegistrationPresenter.verifyAndLoadAvatar(data.getData());
        }

        if (data != null && resultCode == RESULT_OK && requestCode == PHOTO_GALLERY) {
            mRegistrationPresenter.verifyAndLoadAvatar(data.getData());
        }

        if (resultCode == RESULT_OK)
            mRegistrationPresenter.setOnActivityResultInFacebookCallback(requestCode, resultCode, data);
    }

    @Override
    public void loadAvatarToImageView(File file) {
        Glide.with(this).load(file).centerCrop().into(mAvatarImage);
    }

    @Override
    public Context getContextActivity() {
        return getActivity();
    }

    public void showErrorPasswordConfirm() {
        mPasswordConfirm.setText("");
        mPasswordConfLayout.setError(getString(R.string.do_not_match_password));
    }

    private void facebookLoginWithPermissions() {
        LoginManager.getInstance()
                .logInWithReadPermissions(RegistrationFragment.this, Arrays.asList("public_profile"));
    }


    private void init(View v) {
        mAvatarImage = (CircleImageView) v.findViewById(R.id.user_pic);
        mImageWrapper = (FrameLayout) v.findViewById(R.id.image_wrapper);

        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);

        mEmailLayout = (TextInputLayout) v.findViewById(R.id.text_input_email);
        mPasswordLayout = (TextInputLayout) v.findViewById(R.id.text_input_password);
        mPasswordConfLayout = (TextInputLayout) v.findViewById(R.id.text_input_password_confirm);
        mPhoneLayout = (TextInputLayout) v.findViewById(R.id.text_input_phone);

        mPasswordLayout = (TextInputLayout) v.findViewById(R.id.text_input_password);
        mPasswordConfLayout = (TextInputLayout) v.findViewById(R.id.text_input_password_confirm);
        mPhoneLayout = (TextInputLayout) v.findViewById(R.id.text_input_phone);

        mEmailEditText = (EditText) v.findViewById(R.id.register_email);
        //mEmailEditText.requestFocus(View.VISIBLE);

        mPasswordEditText = (TextInputEditText) v.findViewById(R.id.register_password);
        mPasswordConfirm = (TextInputEditText) v.findViewById(R.id.register_confirm_password);
        mPhoneEditText = (TextInputEditText) v.findViewById(R.id.register_phone);

        mFullName = (EditText) v.findViewById(R.id.register_full_name);
        mWebSite = (EditText) v.findViewById(R.id.register_website);

        mFacebookLinkButton = (Button) v.findViewById(R.id.login_facebook_button);
        mSignUpButton = (Button) v.findViewById(R.id.register_sign_up);
        mRegistrationPresenter.createFormatWatcher();

        mImageWrapper = (FrameLayout) v.findViewById(R.id.image_wrapper);

    }

    public interface OnRegisterSuccess {
        void onRegisterSuccess();
    }

}