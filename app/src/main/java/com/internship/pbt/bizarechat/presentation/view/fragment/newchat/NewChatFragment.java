package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.adapter.NewChatUsersRecyclerAdapter;
import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.newchat.NewChatPresenterImpl;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewChatFragment extends MvpAppCompatFragment implements
        NewChatView, View.OnClickListener, NewChatUsersRecyclerAdapter.OnCheckBoxClickListener {

    @InjectPresenter
    NewChatPresenterImpl presenter;

    @ProvidePresenter
    NewChatPresenterImpl provideNewChatPresenter() {
        return new NewChatPresenterImpl(
                new GetAllUsersUseCase(
                        new UserDataRepository(BizareChatApp.getInstance().getUserService())),
                new GetPhotoUseCase(new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        CacheSharedPreferences.getInstance(BizareChatApp.getInstance()),
                        CacheUsersPhotos.getInstance(BizareChatApp.getInstance()))));
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

    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_chat, container, false);
        layoutManager = new LinearLayoutManager(getActivity());
        chatNameInputLayout = (TextInputLayout)view.findViewById(R.id.new_chat_name_layout);
        chatNameEditText = (TextInputEditText)view.findViewById(R.id.new_chat_name_edit);
        chatPhoto = (CircleImageView)view.findViewById(R.id.new_chat_image);
        publicRadioButton = (AppCompatRadioButton)view.findViewById(R.id.radio_public);
        publicRadioButton.setOnClickListener(this);
        privateRadioButton = (AppCompatRadioButton)view.findViewById(R.id.radio_private);
        privateRadioButton.setOnClickListener(this);
        recyclerView = (RecyclerView)view.findViewById(R.id.new_chat_members_container);
        createButton = (Button)view.findViewById(R.id.new_chat_button_create);
        membersTitle = (TextView)view.findViewById(R.id.new_chat_members_title);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {
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
        getActivity().supportInvalidateOptionsMenu();
        toolbarTitle = (TextView)getActivity().findViewById(R.id.chat_toolbar_title);
        toolbarTitle.setText(R.string.new_chat_title);
        recyclerView.setAdapter(presenter.getAdapter().setListener(this).setContext(getActivity()));
        presenter.getAllUsers();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.radio_private:
                presenter.onPrivateClick();
                break;
            case R.id.radio_public:
                presenter.onPublicClick();
                break;
            case R.id.new_chat_button_create:

                break;
        }
    }

    public void showUsersView(){
        membersTitle.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void hideUsersView(){
        membersTitle.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    public void showChatPhoto(){
        chatPhoto.setVisibility(View.VISIBLE);
    }

    public void hideChatPhoto(){
        chatPhoto.setVisibility(View.GONE);
    }

    @Override
    public void onCheckBoxClick() {
        presenter.setChatPhotoVisibility();
    }
}
