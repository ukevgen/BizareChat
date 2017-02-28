package com.internship.pbt.bizarechat.presentation.presenter.editchat;


import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.EditChatRecyclerViewAdapter;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UpdateDialogUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UploadFileUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UseCase;
import com.internship.pbt.bizarechat.domain.repository.ContentRepository;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.util.Converter;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.editchat.EditChatView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Subscriber;

@InjectViewState
public class EditChatPresenterImpl extends MvpPresenter<EditChatView> implements EditChatPresenter {

    private DialogUpdateRequestModel requestModel;
    private DialogUpdateRequestModel.PushAll pushAll;
    private DialogUpdateRequestModel.PullAll pullAll;
    private Set<Long> pushAllSet;
    private Set<Long> pullAllSet;
    private String dialogId;

    private UpdateDialogUseCase updateDialogUseCase;
    private GetAllUsersUseCase allUsersUseCase;
    private UseCase uploadFileUseCase;
    private ContentRepository contentRepository;
    private Long currentUserId = CurrentUser.getInstance().getCurrentUserId();
    private Integer currentUsersPage = 0;
    private Integer usersCount = 0;
    private GetPhotoUseCase photoUseCase;
    private List<UserModel> users;
    private Map<Long, Bitmap> usersPhotos;
    private Set<Long> checkedUsers;
    private EditChatRecyclerViewAdapter adapter;
    private File fileToUpload;
    private Converter converter;
    private Validator validator;

    public EditChatPresenterImpl(UpdateDialogUseCase updateDialogUseCase,
                                 GetAllUsersUseCase allUsersUseCase,
                                 GetPhotoUseCase photoUseCase,
                                 ContentRepository contentRepository,
                                 Converter converter,
                                 Validator validator) {

        this.allUsersUseCase = allUsersUseCase;
        this.photoUseCase = photoUseCase;
        this.updateDialogUseCase = updateDialogUseCase;
        this.contentRepository = contentRepository;
        this.converter = converter;
        this.validator = validator;
        this.users = new ArrayList<>();
        this.usersPhotos = new HashMap<>();
        this.checkedUsers = new HashSet<>();
        this.adapter = new EditChatRecyclerViewAdapter(users, usersPhotos, checkedUsers);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if (updateDialogUseCase != null)
            updateDialogUseCase.unsubscribe();
        if (photoUseCase != null)
            photoUseCase.unsubscribe();
        if (uploadFileUseCase != null)
            uploadFileUseCase.unsubscribe();
        if (allUsersUseCase != null)
            allUsersUseCase.unsubscribe();

        this.allUsersUseCase = null;
        this.photoUseCase = null;
        this.updateDialogUseCase = null;
        this.contentRepository = null;
        this.converter = null;
        this.validator = null;
        this.users = null;
        this.usersPhotos = null;
        this.checkedUsers = null;
        this.adapter = null;
    }

    @Override
    public void stop() {

    }

    public void getConcreteDialogInf() {
        //TODO implement retrieving occupants of a concrete dialog and dialog id
        // and impl checkedUsers = retrievedUsers;
        // dialogId = dialogId

    }

    public void getAllUsers() {
        if (usersCount != 0 && currentUsersPage * ApiConstants.USERS_PER_PAGE >= usersCount) return;

        allUsersUseCase.setPage(++currentUsersPage);
        allUsersUseCase.execute(new Subscriber<AllUsersResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(AllUsersResponse response) {
                UserModel user;
                int insertCounter = 0;
                for (AllUsersResponse.Item item : response.getItems()) {
                    user = item.getUser();

                    if (user.getUserId().equals(currentUserId))
                        continue;

                    users.add(user);
                    insertCounter++;

                    if (user.getBlobId() != null) {
                        getAndAddPhoto(users.size() - 1, user.getUserId(), user.getBlobId());
                    } else {
                        usersPhotos.put(user.getUserId(), null);
                    }
                }
                if (usersCount == 0) {
                    usersCount = response.getTotalEntries();
                } else {
                    adapter.notifyItemRangeInserted(users.size() - insertCounter, insertCounter);
                }
            }
        });
    }

    private void getAndAddPhoto(int position, Long userId, Integer blobId) {
        photoUseCase.setBlobId(blobId);
        photoUseCase.execute(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Bitmap bitmap) {
                usersPhotos.put(userId, bitmap);
                adapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void saveChanges() {
        if (requestModel == null)
            requestModel = new DialogUpdateRequestModel();
        if (pushAll != null && pushAllSet != null)
            requestModel.setPushAll(new DialogUpdateRequestModel.PushAll(pushAllSet));
        if (pullAll != null && pullAllSet != null)
            requestModel.setPullAll(new DialogUpdateRequestModel.PullAll(pullAllSet));
        if (dialogId != null && updateDialogUseCase != null) {
            updateDialogUseCase.setDialogId(dialogId);
            updateDialogUseCase.setRequestModel(requestModel);

            if (fileToUpload == null) {

                this.updateDialogUseCase.execute(new Subscriber<DialogUpdateResponseModel>() {
                    @Override
                    public void onCompleted() {
                        onSaveChanges();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onSaveChangesFailed();
                    }

                    @Override
                    public void onNext(DialogUpdateResponseModel responseModel) {

                    }
                });
            } else {
                uploadAvatar();
            }
        }
    }


    private void uploadAvatar() {
        if (fileToUpload != null) {
            this.uploadFileUseCase = new UploadFileUseCase(contentRepository,
                    ApiConstants.CONTENT_TYPE_IMAGE_JPEG,
                    fileToUpload,
                    null);
            uploadFileUseCase.execute(new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                    saveChanges();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(Integer blobId) {
                    if (requestModel == null)
                        requestModel = new DialogUpdateRequestModel();
                    requestModel.setPhotoBlobId(blobId);
                }
            });
        }
    }

    public void verifyAndLoadAvatar(Uri uri) {
        // mRegisterView.setPermission(uri);
        fileToUpload = converter.compressPhoto(converter.convertUriToFile(uri));

        if (validator.isValidAvatarSize(fileToUpload)) {
            loadAvatar();
        } else {
            showTooLargeImage();
            fileToUpload = null;
        }
    }

    private void loadAvatar() {
        if (fileToUpload != null)
            getViewState().loadAvatarToImageView(fileToUpload);
    }

    private void showTooLargeImage() {
    }

    @Override
    public void onSaveChanges() {
        pullAll = null;
        pushAll = null;
        getViewState().showOnSaveChangesSuccessfully();
    }

    @Override
    public void onSaveChangesFailed() {
        getViewState().showNoPermissionsToEdit();
    }

    @Override
    public void editChatTitle(String newTitle) {
        if (requestModel == null)
            requestModel = new DialogUpdateRequestModel();
        requestModel.setName(newTitle);
    }

    @Override
    public void onCheckBoxClickPush(Long id) {
        if (pushAllSet == null)
            pushAllSet = new HashSet<>();
        pushAllSet.add(id);
    }

    @Override
    public void onCheckBoxClickPull(Long id) {
        if (pullAllSet == null)
            pullAllSet = new HashSet<>();
        if (!pullAllSet.add(id))
            pullAllSet.remove(id);
    }

    public EditChatRecyclerViewAdapter getAdapter() {
        return adapter;
    }
}