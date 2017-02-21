package com.internship.pbt.bizarechat.presentation.view.fragment.users;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUsersByFullName;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.users.UsersPresenter;

public class UsersFragment extends MvpAppCompatFragment
        implements UsersView, View.OnTouchListener{

    @InjectPresenter
    UsersPresenter presenter;

    @ProvidePresenter
    UsersPresenter provideUsersPresenter(){
        return new UsersPresenter(
                new GetAllUsersUseCase(
                        new UserDataRepository(BizareChatApp.getInstance().getUserService())),
                new GetPhotoUseCase(new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        CacheSharedPreferences.getInstance(BizareChatApp.getInstance()),
                        CacheUsersPhotos.getInstance(BizareChatApp.getInstance()))),
                new GetUsersByFullName(
                        new UserDataRepository(BizareChatApp.getInstance().getUserService())));
    }

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private TextView toolbarTitle;
    private EditText filterEditText;
    private EditText searchEditText;
    private MenuItem filterItem;
    private MenuItem searchItem;
    private ProgressBar progressBar;
    private TSnackbar connProblemSnack;


    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        progressBar = (ProgressBar)getActivity().findViewById(R.id.main_progress_bar);
        recyclerView = (RecyclerView)view.findViewById(R.id.users_users_container);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_users_screen, menu);

        filterItem = menu.findItem(R.id.users_action_filter);
        filterEditText = (EditText)getActivity().getLayoutInflater()
                .inflate(R.layout.users_filter_edit_text, null, false);
        filterItem.setActionView(filterEditText);
        filterItem.collapseActionView();
        filterEditText.setOnTouchListener(this);
        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.performFilter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchItem = menu.findItem(R.id.users_action_search);
        searchEditText = (EditText)getActivity().getLayoutInflater()
                .inflate(R.layout.users_search_edit_text, null, false);
        searchItem.setActionView(searchEditText);
        searchItem.collapseActionView();
        searchEditText.setOnTouchListener(this);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.getUsersByFullName(searchEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (v.getId() == R.id.users_search_edit_text) {
                int leftEdgeOfRightDrawable = searchEditText.getRight()
                        - searchEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                if (event.getRawX() >= leftEdgeOfRightDrawable) {
                    searchItem.collapseActionView();
                    presenter.onSearchClose();
                    return true;
                }
            } else if (v.getId() == R.id.users_filter_edit_text) {
                int leftEdgeOfRightDrawable = filterEditText.getRight()
                        - filterEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                if (event.getRawX() >= leftEdgeOfRightDrawable) {
                    filterItem.collapseActionView();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.users_action_search:
                filterItem.collapseActionView();
                item.expandActionView();
                return true;
            case R.id.users_action_filter:
                searchItem.collapseActionView();
                item.expandActionView();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbarTitle = (TextView)getActivity().findViewById(R.id.chat_toolbar_title);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbarTitle.setText(R.string.users_title);
        recyclerView.setAdapter(presenter.getAdapter().setContext(getActivity()));
        presenter.getAllUsers();
    }

    @Override
    public void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError(){
        if(connProblemSnack == null) {
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

    @Override
    public void showAloneMessage() {

    }
}
