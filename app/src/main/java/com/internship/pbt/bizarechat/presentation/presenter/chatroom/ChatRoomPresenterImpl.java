package com.internship.pbt.bizarechat.presentation.presenter.chatroom;


import android.graphics.Bitmap;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.ChatRoomRecyclerAdapter;
import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.MessageModel;
import com.internship.pbt.bizarechat.data.datamodel.MessageModelDao;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.executor.JobExecutor;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.domain.interactor.GetUsersByIdsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUsersPhotosByIdsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.MarkMessagesAsReadUseCase;
import com.internship.pbt.bizarechat.domain.model.chatroom.MessageState;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomView;
import com.internship.pbt.bizarechat.service.messageservice.BizareChatMessageService;

import org.jivesoftware.smack.packet.id.StanzaIdUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import rx.Subscriber;

@InjectViewState
public class ChatRoomPresenterImpl extends MvpPresenter<ChatRoomView> implements ChatRoomPresenter {
    private static final String TAG = ChatRoomPresenterImpl.class.getSimpleName();
    private static final int MESSAGE_LENGTH = 500;

    private long currentUserId = CurrentUser.getInstance().getCurrentUserId();
    private int type;
    private String dialogId;
    private String dialogRoomJid;
    private long adminId;
    private String chatName;
    private List<Integer> occupantsIds;
    private GetUsersPhotosByIdsUseCase usersPhotosUseCase;
    private GetUsersByIdsUseCase usersByIdsUseCase;
    private MarkMessagesAsReadUseCase markMessagesAsReadUseCase;
    private WeakReference<BizareChatMessageService> messageService;
    private ChatRoomRecyclerAdapter adapter;
    private List<MessageModel> messages;
    private Map<Long, Bitmap> occupantsPhotos;
    private Map<Long, String> userNames;
    private List<UserModel> users;
    private DaoSession daoSession;
    //only for private dialogs
    private String privateOccupantJid = "";

    public ChatRoomPresenterImpl(DaoSession daoSession,
                                 GetUsersPhotosByIdsUseCase usersPhotosUseCase,
                                 GetUsersByIdsUseCase usersByIdsUseCase,
                                 MarkMessagesAsReadUseCase markMessagesAsReadUseCase) {
        this.daoSession = daoSession;
        this.usersPhotosUseCase = usersPhotosUseCase;
        this.usersByIdsUseCase = usersByIdsUseCase;
        this.markMessagesAsReadUseCase = markMessagesAsReadUseCase;
        messages = new ArrayList<>();
        occupantsPhotos = new HashMap<>();
        userNames = new HashMap<>();
        adapter = new ChatRoomRecyclerAdapter(messages, occupantsPhotos, userNames);
    }


    public void init() {
        getViewState().showLoading();
        initUsers();
        sendReadRequestToServer();
    }

    private void sendReadRequestToServer() {
        markMessagesAsReadUseCase.setDialogId(dialogId);
        markMessagesAsReadUseCase.execute(new Subscriber<Response<Void>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(Response<Void> response) {
                response.body();
            }
        });
    }

    private void initUsers() {
        usersByIdsUseCase.setIds(occupantsIds);
        usersByIdsUseCase.execute(new Subscriber<List<UserModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getViewState().hideLoading();
                Log.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(List<UserModel> models) {
                users = models;
                for (UserModel user : models) {
                    userNames.put(user.getUserId(), user.getFullName());
                }
                initUsersPhotos();
            }
        });
    }

    private void initUsersPhotos() {
        usersPhotosUseCase.setUsers(users);
        usersPhotosUseCase.execute(new Subscriber<Map<Long, Bitmap>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getViewState().hideLoading();
                Log.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(Map<Long, Bitmap> photos) {
                occupantsPhotos = photos;
                adapter.setOccupantsPhotos(occupantsPhotos);
                initMessages();
            }
        });
    }

    private void initMessages() {
        messages = daoSession.getMessageModelDao()
                .queryBuilder()
                .where(MessageModelDao.Properties.ChatDialogId.eq(dialogId))
                .orderAsc(MessageModelDao.Properties.DateSent)
                .list();
        adapter.setMessageList(messages);
        adapter.notifyDataSetChanged();
        getViewState().scrollToEnd();
        getViewState().hideLoading();
        if (messages != null && messages.size() > 0) {
            MessageModel lastMessage;
            for (int i = messages.size() - 1; i >= 0; i--) {
                lastMessage = messages.get(i);
                if (lastMessage.getSenderId() != currentUserId && lastMessage.getRead() != MessageState.READ) {
                    messageService.get().sendPrivateReadStatusMessage(
                            privateOccupantJid,
                            lastMessage.getMessageId(),
                            dialogId
                    );
                    break;
                }
            }
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ChatRoomRecyclerAdapter getAdapter() {
        return adapter;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

    public void setDialogRoomJid(String dialogRoomJid) {
        this.dialogRoomJid = dialogRoomJid;
    }

    public void setOccupantsIds(List<Integer> occupantsIds) {
        this.occupantsIds = occupantsIds;
        int id;
        if ((id = getPrivateDialogOccupant()) != 0) {
            privateOccupantJid = id + "-" + ApiConstants.APP_ID + "@" + ApiConstants.CHAT_END_POINT;
        }
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setMessageService(BizareChatMessageService messageService) {
        this.messageService = new WeakReference<>(messageService);
    }

    public void showEditChat() {
        if (currentUserId == adminId) {
            getViewState().showEditChat();
        } else {
            getViewState().showNotAdminError();
        }
    }

    private boolean isMessageLengthValid(String message) {
        return message.length() < MESSAGE_LENGTH;
    }

    public void sendMessage(String message) {
        if (!isMessageLengthValid(message)) {
            getViewState().showToLargeMessage();
            return;
        }
        long timestamp = System.currentTimeMillis() / 1000;
        String stanzaId = StanzaIdUtil.newStanzaId();
        saveOutgoingMessageToDb(message, timestamp, stanzaId);
        if (type == DialogsType.PRIVATE_CHAT) {
            sendPrivateMessage(message, timestamp, stanzaId);
        } else {
            sendPublicMessage(message, timestamp, stanzaId);
        }
    }

    public void sendPublicMessage(String message, long timestamp, String stanzaId) {
        messageService.get().sendPublicMessage(message, dialogRoomJid, timestamp, stanzaId)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }

    private void sendPrivateMessage(String message, long timestamp, String stanzaId) {
        messageService.get().sendPrivateMessage(message, privateOccupantJid, timestamp, stanzaId)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }

    private void saveOutgoingMessageToDb(String message, long timestamp, String stanzaId) {
        final MessageModel messageModel = new MessageModel(
                stanzaId,
                "",
                "",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                dialogId,
                timestamp,
                message,
                0,
                (int) currentUserId,
                MessageState.DEFAULT
        );
        messages.add(messageModel);
        adapter.notifyItemInserted(messages.lastIndexOf(messageModel));
        getViewState().scrollToEnd();
        JobExecutor.getInstance().execute(() -> daoSession.getMessageModelDao().insertInTx(messageModel));
    }

    private int getPrivateDialogOccupant() {
        if (occupantsIds.isEmpty() && occupantsIds.size() > 2) {
            return 0;
        }
        for (Integer id : occupantsIds) {
            if (id != currentUserId) {
                return id;
            }
        }
        return 0;
    }

    public void processPrivateMessage(MessageModel message) {
        if (message.getSenderId() == getPrivateDialogOccupant()) {
            messages.add(message);
            adapter.notifyItemInserted(messages.lastIndexOf(message));
            getViewState().scrollToEnd();
            messageService.get().sendPrivateReadStatusMessage(privateOccupantJid, message.getMessageId(), dialogId);
        }
    }

    public void processPublicMessage(MessageModel message) {
        if (message.getChatDialogId().equals(dialogId)) {
            messages.add(message);
            adapter.notifyItemInserted(messages.lastIndexOf(message));
            getViewState().scrollToEnd();
        }
    }

    public void processDeliveredReceipt(List<MessageModel> messages) {
        if (messages != null && !messages.isEmpty() && messages.get(0).getChatDialogId().equals(dialogId)) {
            for (MessageModel message : messages) {
                for (int i = 0; i < this.messages.size(); i++) {
                    if (this.messages.get(i).getMessageId().equals(message.getMessageId()) &&
                            messages.get(i).getRead() != MessageState.READ) {
                        this.messages.get(i).setRead(MessageState.DELIVERED);
                        adapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        }
    }

    public void processReadReceipt(List<MessageModel> messages) {
        if (messages != null && !messages.isEmpty() && messages.get(0).getChatDialogId().equals(dialogId)) {
            for (MessageModel message : messages) {
                for (int i = 0; i < this.messages.size(); i++) {
                    if (this.messages.get(i).getMessageId().equals(message.getMessageId())) {
                        this.messages.get(i).setRead(MessageState.READ);
                        adapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        }
    }

    public void processSentPublicMessageEvent(String messageId) {
        for (int i = 0; i < this.messages.size(); i++) {
            if (messages.get(i).getMessageId().equals(messageId)) {
                this.messages.get(i).setRead(MessageState.DELIVERED);
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        if (type != DialogsType.PRIVATE_CHAT) {
            messageService.get().leavePublicChat(dialogRoomJid);
        }
    }
}
