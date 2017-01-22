package com.internship.pbt.bizarechat.presentation.view.fragment.register;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.model.ValidationInformation;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.registration.RegistrationPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.fragment.BaseFragment;

import static android.app.Activity.RESULT_OK;


public class RegistrationFragment extends BaseFragment implements RegistrationView, View.OnClickListener {

    private final int DEVICE_CAMERA = 0;
    private final int PHOTO_GALLERY = 1;

    private RegistrationPresenter mRegistrationPresenter;

    private TextInputLayout mEmailLayout;
    private TextInputLayout mPasswordLayout;
    private TextInputLayout mPhoneLayout;

    private EditText mEmailEditText;
    private TextInputEditText mPasswordEditText;
    private TextInputEditText mPhoneEditText;

    private Button mSignUpButton;
    private Button mFacebookLinkButton;
    private OnRegisterSuccess mOnRegisterSuccess;


    private ImageView mAvatarImage;
    private Animation mSuccessFacebookButtonAnim;
    private Animation mFailButtonAnim;
    private Animation getmSuccessSignUpAnim;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnRegisterSuccess && context != null) {
            mOnRegisterSuccess = (OnRegisterSuccess) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRegistrationPresenter = new RegistrationPresenterImpl();
        Log.d("123", "Fragment OnCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("123", "Fragment OnStart");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("123", "Fragment OnCreateView");

        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mRegistrationPresenter.setRegistrationView(this);

        mAvatarImage = (ImageView) v.findViewById(R.id.user_pic);

        mEmailLayout = (TextInputLayout) v.findViewById(R.id.text_input_email);
        mPasswordLayout = (TextInputLayout) v.findViewById(R.id.text_input_password);
        mPhoneLayout = (TextInputLayout) v.findViewById(R.id.text_input_phone);

        mEmailEditText = (EditText) v.findViewById(R.id.register_email);
        mPasswordEditText = (TextInputEditText) v.findViewById(R.id.register_password);
        mPhoneEditText = (TextInputEditText) v.findViewById(R.id.register_phone);

        mFacebookLinkButton = (Button) v.findViewById(R.id.login_facebook_button);
        mSignUpButton = (Button) v.findViewById(R.id.register_sign_up);

        mAvatarImage.setOnClickListener(this);
        mFacebookLinkButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);

        this.setAnimation();

        return v;
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
    public void setAnimation() {

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
    public void startFailedSignUpAnim() {
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

    @Override
    public void hideErrorInvalidEmail() {
        mEmailLayout.setError(null);
        mEmailLayout.setErrorEnabled(false);
    }

    @Override
    public void hideErrorInvalidPassword() {
        mPasswordLayout.setError(null);
        mPasswordLayout.setErrorEnabled(false);
    }

    @Override
    public void hideErrorInvalidPhone() {
        mPhoneLayout.setError(null);
        mPhoneLayout.setErrorEnabled(false);
    }

    @Override
    public void showErrorInvalidEmail() {
        mEmailLayout.setErrorEnabled(true);
        mEmailLayout.setError(getString(R.string.invalid_email));

    }

    @Override
    public void showErrorInvalidPassword() {
        mPasswordLayout.setErrorEnabled(true);
        mPasswordLayout.setError(getString(R.string.invalid_weak_password));
    }

    @Override
    public void showErrorInvalidPhone() {
        mPhoneLayout.setErrorEnabled(true);
        mPhoneLayout.setError(getString(R.string.invalid_phone));
    }

    @Override
    public void showErrorPasswordLength() {
        mPasswordLayout.setError(getString(R.string.error_password_length));
    }

    @Override
    public void loginFacebook() {

    }

    @Override
    public void onRegistrationSuccess() {
        mOnRegisterSuccess.onRegisterSuccess();
    }

    @Override
    public void onClick(View view) {
       switch (view.getId())
       {
           case R.id.register_sign_up:
               this.getInformationForValidation();
               break;
           case R.id.login_facebook_button:
               mRegistrationPresenter.facebookLink();
               break;
           case R.id.user_pic:
               this.showPictureChooser();
               break;
       }
    }



    @Override
    public void showPictureChooser() {
        final String[] items = {"Device Camera", "Photo Gallery"};
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getText(R.string.choose_source_for_getting_image));
        builder.setNegativeButton(R.string.back, null);
        builder.setItems(items, (dialogInterface, i) -> {
            if(items[i].equals("Device Camera")){
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, DEVICE_CAMERA);
            }
            else if(items[i].equals("Photo Gallery")){
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, PHOTO_GALLERY);
            }
        });
        builder.show();
    }

    @Override
    public void getInformationForValidation() {
        ValidationInformation validationInformation = new ValidationInformation();
        validationInformation.setEmail(mEmailEditText.getText().toString());
        validationInformation.setPassword(mPasswordEditText.getText().toString());
        validationInformation.setPhone(mPhoneEditText.getText().toString());
        Log.d("123", "Fragment GetValidInf" + validationInformation.toString());
        mRegistrationPresenter.validateInformation(validationInformation);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    mAvatarImage.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    mAvatarImage.setImageURI(selectedImage);
                }
                break;
        }
    }

    public interface OnRegisterSuccess {
        void onRegisterSuccess();
    }

}
