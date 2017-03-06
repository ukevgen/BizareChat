package com.internship.pbt.bizarechat.presentation.presenter.newchat;


import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.NewChatUsersRecyclerAdapter;
import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.NewDialog;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.db.QueryBuilder;
import com.internship.pbt.bizarechat.domain.interactor.CreateDialogUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UploadFileUseCase;
import com.internship.pbt.bizarechat.domain.interactor.UseCase;
import com.internship.pbt.bizarechat.domain.repository.ContentRepository;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.util.Converter;
import com.internship.pbt.bizarechat.presentation.util.Validator;
import com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import rx.Subscriber;

@InjectViewState
public class NewChatPresenterImpl extends MvpPresenter<NewChatView> implements NewChatPresenter {
    private final ContentRepository contentRepository;
    private Long currentUserId = CurrentUser.getInstance().getCurrentUserId();
    private Integer currentUsersPage = 0;
    private Integer usersCount = 0;
    private GetAllUsersUseCase allUsersUseCase;
    private GetPhotoUseCase photoUseCase;
    private CreateDialogUseCase createDialogUseCase;
    private List<UserModel> users;
    private Map<Long, Bitmap> usersPhotos;
    private QueryBuilder queryBuilder;
    //use this field to get all checked users and add them to "create chat request"
    private Set<Long> checkedUsers;
    private NewChatUsersRecyclerAdapter adapter;
    private boolean isPublicButtonChecked = true;
    private UseCase uploadFileUseCase;
    private File fileToUpload;
    private Converter converter;
    private Validator validator;
    private String blobId;

    public NewChatPresenterImpl(Converter converter,
                                GetAllUsersUseCase allUsersUseCase,
                                GetPhotoUseCase photoUseCase,
                                CreateDialogUseCase createDialogUseCase,
                                ContentRepository contentRepository) {
        this.createDialogUseCase = createDialogUseCase;
        this.converter = converter;
        this.allUsersUseCase = allUsersUseCase;
        this.photoUseCase = photoUseCase;
        this.contentRepository = contentRepository;
        users = new ArrayList<>();
        usersPhotos = new HashMap<>();
        checkedUsers = new HashSet<>();
        adapter = new NewChatUsersRecyclerAdapter(users, usersPhotos, checkedUsers);
        validator = new Validator();
        queryBuilder = QueryBuilder.getQueryBuilder(BizareChatApp.getInstance().getDaoSession());
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
                    queryBuilder.addUserToUsersDao(user);
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

    public void onPrivateClick() {
        isPublicButtonChecked = false;
        fileToUpload = null;
        setChatPhotoVisibility();
        getViewState().showUsersView();
    }

    public void onPublicClick() {
        isPublicButtonChecked = true;
        setChatPhotoVisibility();
        getViewState().hideUsersView();
    }

    public void setChatPhotoVisibility() {
        if (!isPublicButtonChecked && checkedUsers.size() > 1)
            getViewState().showChatPhoto();
        else if (isPublicButtonChecked)
            getViewState().showChatPhoto();
        else {
            getViewState().hideChatPhoto();
        }
    }

    public NewChatUsersRecyclerAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void verifyAndLoadAvatar(Uri uri) {
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

    @Override
    public void showTooLargeImage() {
        getViewState().showTooLargePicture();
    }

    @Override
    public void createNewChat() {


    }

    @Override
    public void createRequestForNewChat(String chatName) {
        //  uploadChatPhoto();
        NewDialog dialog;
        String occupants = converter.getOccupantsArray(checkedUsers);
        if (!isPublicButtonChecked && checkedUsers.size() == 1) {
            dialog = new NewDialog(DialogsType.PRIVATE_CHAT, chatName, occupants);
        }
        else if (!isPublicButtonChecked && checkedUsers.size() > 1) {
            dialog = new NewDialog(DialogsType.GROUP_CHAT, chatName, occupants, blobId);
        }
        else {
            dialog = new NewDialog(DialogsType.PUBLIC_GROUP_CHAT, chatName, occupants, blobId);
        }

        createDialogUseCase.setDialog(dialog);
        createDialogUseCase.execute(new Subscriber<DialogModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", e.toString());
                getViewState().hideLoading();
            }

            @Override
            public void onNext(DialogModel response) {
                Log.d("TAG", response.toString());
                queryBuilder.saveNewDialog(response);
                getViewState().hideLoading();
                getViewState().showChatRoom();
            }
        });
    }

    public void uploadChatPhoto() {
        String fileName = UUID.randomUUID().toString();
        if (fileToUpload != null) {
            getViewState().showLoading();
            this.uploadFileUseCase = new UploadFileUseCase(contentRepository,
                    ApiConstants.CONTENT_TYPE_IMAGE_JPEG,
                    fileToUpload,
                    fileName);

            uploadFileUseCase.execute(new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("TAG", e.getLocalizedMessage());
                    if (getViewState() != null) {
                        getViewState().hideLoading();
                        getViewState().showErrorMassage(e.getLocalizedMessage());
                        getViewState().getChatProperties();
                    }
                }

                @Override
                public void onNext(Integer response) {
                    Log.d("TAG", "blod id = " + response);
                    blobId = String.valueOf(response);
                    getViewState().getChatProperties();
                }
            });

        }
        //  getViewState().getChatProperties();
    }

    @Override
    public void checkConnection() {
        if (BizareChatApp.getInstance().isNetworkConnected()) {
            uploadChatPhoto();
            //getViewState().getChatProperties();
        } else {
            getViewState().showNetworkError();
        }
    }
}



