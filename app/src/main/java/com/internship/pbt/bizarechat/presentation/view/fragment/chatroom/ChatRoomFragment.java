package com.internship.pbt.bizarechat.presentation.view.fragment.chatroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.events.DisplayedEvent;
import com.internship.pbt.bizarechat.domain.events.PrivateMessageEvent;
import com.internship.pbt.bizarechat.domain.events.PublicMessageEvent;
import com.internship.pbt.bizarechat.domain.events.ReceivedEvent;
import com.internship.pbt.bizarechat.domain.interactor.GetUsersByIdsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUsersPhotosByIdsUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.chatroom.ChatRoomPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.activity.MainActivity;
import com.internship.pbt.bizarechat.presentation.view.fragment.editchat.EditChatFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;


public class ChatRoomFragment extends MvpAppCompatFragment
        implements ChatRoomView, View.OnClickListener {
    public static final String DIALOG_ID_BUNDLE_KEY = "dialogId";
    public static final String DIALOG_NAME_BUNDLE_KEY = "dialogName";
    public static final String DIALOG_ROOM_JID_BUNDLE_KEY = "dialogRoomJid";
    public static final String DIALOG_TYPE_BUNDLE_KEY = "dialogType";
    public static final String OCCUPANTS_IDS_BUNDLE_KEY = "occupantsIds";
    private final int editItemId = 400;


    @InjectPresenter
    ChatRoomPresenterImpl presenter;

    @ProvidePresenter
    ChatRoomPresenterImpl provideChatRoomPresenter(){
        return new ChatRoomPresenterImpl(
                BizareChatApp.getInstance().getDaoSession(),
                new GetUsersPhotosByIdsUseCase(new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        BizareChatApp.getInstance().getCacheUsersPhotos())),
                new GetUsersByIdsUseCase(new UserDataRepository(
                        BizareChatApp.getInstance().getUserService()))
        );
    }

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private ImageButton sendButton;
    private EmojiconEditText messageEditText;
    private ImageView emojiButton;
    private EmojIconActions emojIconActions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        setHasOptionsMenu(true);
        presenter.setMessageService(((MainActivity)getActivity()).getMessageService());
        presenter.setDialogId(getArguments().getString(DIALOG_ID_BUNDLE_KEY));
        presenter.setDialogRoomJid(getArguments().getString(DIALOG_ROOM_JID_BUNDLE_KEY));
        presenter.setOccupantsIds(getArguments().getIntegerArrayList(OCCUPANTS_IDS_BUNDLE_KEY));
        presenter.setType(getArguments().getInt(DIALOG_TYPE_BUNDLE_KEY));
        presenter.init();
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.chat_room_messages_container);
        recyclerView.setLayoutManager(mLayoutManager);
        sendButton = (ImageButton)view.findViewById(R.id.chat_room_send_button);
        sendButton.setOnClickListener(this);
        messageEditText = (EmojiconEditText)view.findViewById(R.id.chat_room_enter_message);
        emojiButton = (ImageView)view.findViewById(R.id.chat_room_emoji_button);
        emojIconActions = new EmojIconActions(getActivity(), view, messageEditText, emojiButton);
        emojIconActions.ShowEmojIcon();
        return view;
    }

    @Override public void onClick(View v) {
        if(v.getId() == R.id.chat_room_send_button){
            presenter.sendMessage(messageEditText.getText().toString());
            messageEditText.setText("");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, editItemId, 0, "Search").setIcon(R.drawable.edit_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == editItemId){
            Fragment fragment = new EditChatFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_screen_container, fragment)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void scrollToEnd(){
        recyclerView.scrollToPosition(presenter.getAdapter().getItemCount()-1);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getAdapter().setContext(getActivity());
        recyclerView.setAdapter(presenter.getAdapter());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onPrivateMessageEvent(PrivateMessageEvent event){
        if(presenter.getType() == DialogsType.PRIVATE_CHAT) {
            presenter.processPrivateMessage(event.getMessage());
        }
    }

    @Subscribe
    public void onPublicMessageEvent(PublicMessageEvent event){
        if(presenter.getType() != DialogsType.PRIVATE_CHAT) {
            //TODO handle public message
        }
    }

    @Subscribe
    public void onDeliveredReceipt(ReceivedEvent event){
        presenter.processDeliveredReceipt(event.getMessages());
    }

    @Subscribe
    public void onReadReceipt(DisplayedEvent event){
        presenter.processReadReceipt(event.getMessages());
    }
}
