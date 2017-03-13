package com.internship.pbt.bizarechat.presentation.view.fragment.editchat;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.adapter.EditChatRecyclerViewAdapter;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.DialogsDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UpdateDialogUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.editchat.EditChatPresenterImpl;
import com.internship.pbt.bizarechat.presentation.util.Converter;
import com.internship.pbt.bizarechat.presentation.util.Validator;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class EditChatFragment extends MvpAppCompatFragment implements EditChatView, View.OnClickListener,
        EditChatRecyclerViewAdapter.OnCheckBoxClickListener {

    public static final String DIALOG_NAME_BUNDLE_KEY = "dialogName";
    public static final String DIALOG_ID_BUNDLE_KEY = "dialogId";
    public static final String DIALOG_OCCUPANTS_BUNDLE_KEY = "dialogOccupants";

    private final static String CHAT_ROOM_FR_TAG = "chatRoomFragment_";
    private final int DEVICE_CAMERA = 0;
    private final int PHOTO_GALLERY = 1;
    @InjectPresenter
    EditChatPresenterImpl presenter;

    private RecyclerView recyclerView;
    private CircleImageView chatImageView;
    private TextInputEditText chatNameEditText;
    private Button saveButton;
    private LinearLayoutManager layoutManager;

    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private TSnackbar connProblemSnack;


    @ProvidePresenter
    EditChatPresenterImpl provideEditChatPresenter() {
        return new EditChatPresenterImpl(
                new UpdateDialogUseCase(
                        new DialogsDataRepository(BizareChatApp.getInstance().getDialogsService(),
                                BizareChatApp.getInstance().getDaoSession())),
                new GetAllUsersUseCase(
                        new UserDataRepository(BizareChatApp.getInstance().getUserService())),
                new GetPhotoUseCase(
                        new ContentDataRepository(
                                BizareChatApp.getInstance().getContentService(),
                                CacheUsersPhotos.getInstance(BizareChatApp.getInstance()))),
                new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        BizareChatApp.getInstance().getCacheUsersPhotos()),
                new Converter(BizareChatApp.getInstance().getApplicationContext()),
                new Validator());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_chat, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.edit_chat_members_container);
        chatImageView = (CircleImageView) view.findViewById(R.id.edit_chat_image);
        chatNameEditText = (TextInputEditText) view.findViewById(R.id.edit_chat_name_edit);
        chatNameEditText.setText(getArguments().getString(DIALOG_NAME_BUNDLE_KEY));
        presenter.setOccupantsIds(getArguments().getIntegerArrayList(DIALOG_OCCUPANTS_BUNDLE_KEY));
        saveButton = (Button) view.findViewById(R.id.edit_chat_button_create);
        layoutManager = new LinearLayoutManager(getActivity());
        saveButton.setOnClickListener(this);
        chatImageView.setOnClickListener(this);
        presenter.setDialogId(getArguments().getString(DIALOG_ID_BUNDLE_KEY));
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
    public void onStart() {
        super.onStart();
        recyclerView.setAdapter(presenter.getAdapter().setListener(this).setContext(getActivity()));
        presenter.getAllUsers();
    }

    @Override
    public void showNoPermissionsToEdit() {
        Snackbar.make(getView(), getString(R.string.you_have_no_permissions_for_edit_this_chat),
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showOnSaveChangesSuccessfully() {
        Snackbar.make(getView(), getString(R.string.successfully_edited),
                Snackbar.LENGTH_SHORT).show();
        String tag = CHAT_ROOM_FR_TAG + presenter.getDialogId();
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        getActivity().getSupportFragmentManager().popBackStackImmediate();

        if (fragment != null) {
            transaction.replace(R.id.main_screen_container, fragment, tag)
                    .commit();
            return;
        }

    }

    @Override
    public void showTooLargePicture() {
        Snackbar.make(getView(), getText(R.string.too_large_picture_please_select_anouther),
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showChatRoom(DialogUpdateResponseModel dialogModel) {
    }

    @Override
    public void loadAvatarToImageView(File file) {
        Glide.with(this).load(file).centerCrop().into(chatImageView);
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.edit_chat_button_create:
                presenter.editChatTitle(chatNameEditText.getText().toString());
                presenter.saveChanges();
                break;
            case R.id.edit_chat_image:
                this.showPictureChooser();
                break;
        }
    }

    @Override
    public void onCheckBoxClickPush(Long id) {
        presenter.onCheckBoxClickPush(id);
    }

    @Override
    public void onCheckBoxClickPull(Long id) {
        presenter.onCheckBoxClickPull(id);
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