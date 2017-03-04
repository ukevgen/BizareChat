package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.adapter.NewChatUsersRecyclerAdapter;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.DialogsDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.CreateDialogUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.newchat.NewChatPresenterImpl;
import com.internship.pbt.bizarechat.presentation.util.Converter;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class NewChatFragment extends MvpAppCompatFragment implements
        NewChatView, View.OnClickListener, NewChatUsersRecyclerAdapter.OnCheckBoxClickListener {

    @InjectPresenter
    NewChatPresenterImpl presenter;

    private static final int DEVICE_CAMERA = 0;
    private static final int PHOTO_GALLERY = 1;
    private ProgressBar mProgressBar;

    @ProvidePresenter
    NewChatPresenterImpl provideNewChatPresenter() {
        return new NewChatPresenterImpl(new Converter(getActivity()),
                new GetAllUsersUseCase(
                        new UserDataRepository(BizareChatApp.getInstance().getUserService())),
                new GetPhotoUseCase(new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        CacheUsersPhotos.getInstance(BizareChatApp.getInstance()))),
                new CreateDialogUseCase(new DialogsDataRepository(BizareChatApp.getInstance()
                        .getDialogsService())),
                new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        CacheUsersPhotos.getInstance(getActivity()))
        );
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private LinearLayoutManager layoutManager;
    private TextInputLayout chatNameInputLayout;
    private TextInputEditText chatNameEditText;
    private CircleImageView chatPhoto;
    private AppCompatRadioButton publicRadioButton;
    private AppCompatRadioButton privateRadioButton;
    private RecyclerView recyclerView;
    private Button createButton;
    private TextView toolbarTitle;
    private TextView membersTitle;
    private TSnackbar connProblemSnack;

    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_chat, container, false);
        layoutManager = new LinearLayoutManager(getActivity());
        chatNameInputLayout = (TextInputLayout) view.findViewById(R.id.new_chat_name_layout);
        chatNameEditText = (TextInputEditText) view.findViewById(R.id.new_chat_name_edit);
        chatPhoto = (CircleImageView) view.findViewById(R.id.new_chat_image);
        chatPhoto.setOnClickListener(this);
        publicRadioButton = (AppCompatRadioButton) view.findViewById(R.id.radio_public);
        publicRadioButton.setOnClickListener(this);
        privateRadioButton = (AppCompatRadioButton) view.findViewById(R.id.radio_private);
        privateRadioButton.setOnClickListener(this);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.main_progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.new_chat_members_container);
        createButton = (Button) view.findViewById(R.id.new_chat_button_create);
        createButton.setOnClickListener(this);
        membersTitle = (TextView) view.findViewById(R.id.new_chat_members_title);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            presenter.getAllUsers();
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbarTitle = (TextView) getActivity().findViewById(R.id.chat_toolbar_title);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().supportInvalidateOptionsMenu();
        toolbarTitle = (TextView) getActivity().findViewById(R.id.chat_toolbar_title);
        toolbarTitle.setText(R.string.new_chat_title);
        recyclerView.setAdapter(presenter.getAdapter().setListener(this).setContext(getActivity()));
        presenter.getAllUsers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_private:
                presenter.onPrivateClick();
                break;
            case R.id.radio_public:
                presenter.onPublicClick();
                break;
            case R.id.new_chat_image:
                showPictureChooser();
                break;
            case R.id.new_chat_button_create:
                /*presenter.uploadChatPhoto();
                presenter.createNewChat();*/
                presenter.checkConnection();
                break;
        }
    }

    public void showUsersView() {
        membersTitle.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void hideUsersView() {
        membersTitle.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    public void showChatPhoto() {
        chatPhoto.setVisibility(View.VISIBLE);
    }

    public void hideChatPhoto() {
        chatPhoto.setVisibility(View.GONE);
    }

    @Override
    public void showTooLargePicture() {
        Snackbar.make(getView(), getText(R.string.too_large_picture_please_select_anouther),
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void loadAvatarToImageView(File file) {
        Glide.with(this).load(file).centerCrop().into(chatPhoto);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getChatProperties() {
        String chatName = String.valueOf(chatNameEditText.getText());
        presenter.createRequestForNewChat(chatName);
    }

    @Override
    public void showErrorMassage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setChatType(int type) {
        this.type = type;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getChatProperties() {
        String chatName = String.valueOf(chatNameEditText.getText());
        presenter.createRequestForNewChat(chatName, type);
    }


    @Override
    public void showErrorMassage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckBoxClick() {
        presenter.setChatPhotoVisibility();
    }

    private void showPictureChooser() {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK && requestCode == DEVICE_CAMERA) {
            presenter.verifyAndLoadAvatar(data.getData());
        }

        if (data != null && resultCode == RESULT_OK && requestCode == PHOTO_GALLERY) {
            presenter.verifyAndLoadAvatar(data.getData());
        }
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
    public void showChatRoom() {
        // TODO start new dialog
    }

    @Override
    public void showNetworkError() {
        if (connProblemSnack == null) {
            connProblemSnack = TSnackbar.make(recyclerView,
                    R.string.main_connection_problem, TSnackbar.LENGTH_SHORT);
            connProblemSnack.getView().setBackgroundColor(getResources()
                    .getColor(R.color.main_screen_connection_problem_background));
            TextView message = (TextView) connProblemSnack.getView()
                    .findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            message.setTextColor(getResources().getColor(R.color.main_screen_connection_problem_text));
            message.setGravity(Gravity.CENTER);
        }
        connProblemSnack.show();
    }
}




