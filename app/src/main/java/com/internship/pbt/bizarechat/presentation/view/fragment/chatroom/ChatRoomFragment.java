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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.DialogsDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.events.DisplayedEvent;
import com.internship.pbt.bizarechat.domain.events.PrivateMessageEvent;
import com.internship.pbt.bizarechat.domain.events.PublicMessageEvent;
import com.internship.pbt.bizarechat.domain.events.PublicMessageSentEvent;
import com.internship.pbt.bizarechat.domain.events.ReceivedEvent;
import com.internship.pbt.bizarechat.domain.interactor.GetUsersByIdsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUsersPhotosByIdsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.MarkMessagesAsReadUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.chatroom.ChatRoomPresenterImpl;
import com.internship.pbt.bizarechat.presentation.view.activity.MainActivity;
import com.internship.pbt.bizarechat.presentation.view.fragment.editchat.EditChatFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;


public class ChatRoomFragment extends MvpAppCompatFragment
        implements ChatRoomView, View.OnClickListener {
    public static final String DIALOG_ID_BUNDLE_KEY = "dialogId";
    public static final String DIALOG_ADMIN_BUNDLE_KEY = "adminId";
    public static final String DIALOG_NAME_BUNDLE_KEY = "dialogName";
    public static final String DIALOG_ROOM_JID_BUNDLE_KEY = "dialogRoomJid";
    public static final String DIALOG_TYPE_BUNDLE_KEY = "dialogType";
    public static final String OCCUPANTS_IDS_BUNDLE_KEY = "occupantsIds";
    private final int editItemId = 400;


    @InjectPresenter
    ChatRoomPresenterImpl presenter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private ImageButton sendButton;
    private EmojiconEditText messageEditText;
    private ImageView emojiButton;
    private EmojIconActions emojIconActions;
    private ProgressBar progressBar;
    private TextView toolbarTitle;
    private TextView messageDay;

    @ProvidePresenter
    ChatRoomPresenterImpl provideChatRoomPresenter() {
        return new ChatRoomPresenterImpl(
                BizareChatApp.getInstance().getDaoSession(),
                new GetUsersPhotosByIdsUseCase(new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        BizareChatApp.getInstance().getCacheUsersPhotos())),
                new GetUsersByIdsUseCase(new UserDataRepository(
                        BizareChatApp.getInstance().getUserService())),
                new MarkMessagesAsReadUseCase(new DialogsDataRepository(
                        BizareChatApp.getInstance().getDialogsService(),
                        BizareChatApp.getInstance().getDaoSession()
                ))
        );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        setHasOptionsMenu(true);
        presenter.setMessageService(((MainActivity) getActivity()).getMessageService());
        presenter.setDialogId(getArguments().getString(DIALOG_ID_BUNDLE_KEY));
        presenter.setDialogRoomJid(getArguments().getString(DIALOG_ROOM_JID_BUNDLE_KEY));
        presenter.setOccupantsIds(getArguments().getIntegerArrayList(OCCUPANTS_IDS_BUNDLE_KEY));
        presenter.setType(getArguments().getInt(DIALOG_TYPE_BUNDLE_KEY));
        presenter.setAdminId(getArguments().getLong(DIALOG_ADMIN_BUNDLE_KEY));
        presenter.setChatName(getArguments().getString(DIALOG_NAME_BUNDLE_KEY));
        mLayoutManager = new LinearLayoutManager(getActivity());
        messageDay = (TextView) view.findViewById(R.id.message_day);
        recyclerView = (RecyclerView) view.findViewById(R.id.chat_room_messages_container);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                final int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    messageDay.setVisibility(View.VISIBLE);
                    messageDay.setText(presenter.getAdapter().getPreviousMesageDay(firstVisibleItemPosition - 1));
                } else {
                    messageDay.setVisibility(View.GONE);
                }
            }
        });
        progressBar = (ProgressBar) getActivity().findViewById(R.id.main_progress_bar);
        sendButton = (ImageButton) view.findViewById(R.id.chat_room_send_button);
        sendButton.setOnClickListener(this);
        messageEditText = (EmojiconEditText) view.findViewById(R.id.chat_room_enter_message);
        toolbarTitle = (TextView) getActivity().findViewById(R.id.chat_toolbar_title);
        emojiButton = (ImageView) view.findViewById(R.id.chat_room_emoji_button);
        emojIconActions = new EmojIconActions(getActivity(), view, messageEditText, emojiButton);
        emojIconActions.ShowEmojIcon();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chat_room_send_button) {
            presenter.sendMessage(messageEditText.getText().toString());
            messageEditText.setText("");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (presenter.getType() != DialogsType.PRIVATE_CHAT) {
            menu.add(0, editItemId, 0, "Search").setIcon(R.drawable.edit_icon)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == editItemId) {
            presenter.showEditChat();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void scrollToEnd() {
        recyclerView.scrollToPosition(presenter.getAdapter().getItemCount() - 1);
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbarTitle.setText(presenter.getChatName());
        presenter.getAdapter().setContext(getActivity());
        recyclerView.setAdapter(presenter.getAdapter());
        if (presenter.getAdapter().getItemCount() > 0) {
            scrollToEnd();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPrivateMessageEvent(PrivateMessageEvent event) {
        if (presenter.getType() == DialogsType.PRIVATE_CHAT) {
            presenter.processPrivateMessage(event.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPublicMessageEvent(PublicMessageEvent event) {
        if (presenter.getType() != DialogsType.PRIVATE_CHAT) {
            presenter.processPublicMessage(event.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeliveredReceipt(ReceivedEvent event) {
        presenter.processDeliveredReceipt(event.getMessages());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReadReceipt(DisplayedEvent event) {
        presenter.processReadReceipt(event.getMessages());
    }

    @Subscribe
    public void onSentPublicMessage(PublicMessageSentEvent event) {
        presenter.processSentPublicMessageEvent(event.getMessageId());
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
    public void showEditChat() {
        Fragment fragment = new EditChatFragment();
        Bundle args = new Bundle();
        args.putString(DIALOG_NAME_BUNDLE_KEY, presenter.getChatName());
        args.putString(DIALOG_ID_BUNDLE_KEY, presenter.getDialogId());
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_screen_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showNotAdminError() {
        Toast.makeText(getActivity(), R.string.not_admin_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToLargeMessage() {
        Toast.makeText(getContext(), R.string.to_large_message, Toast.LENGTH_SHORT).show();
    }
}
