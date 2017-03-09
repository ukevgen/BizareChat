package com.internship.pbt.bizarechat.presentation.view.fragment.users;


import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.users.UsersPresenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.userinfo.UserInfoFragment;

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
                        CacheUsersPhotos.getInstance(BizareChatApp.getInstance()))));
    }

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private TextView toolbarTitle;
    private EditText filterEditText;
    private MenuItem sortItem;
    private MenuItem filterItem;
    private ProgressBar progressBar;
    private TSnackbar connProblemSnack;
    private TextView aloneMessage;
    private String sortQuery;

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
        aloneMessage = (TextView)view.findViewById(R.id.users_alone_message);
        recyclerView = (RecyclerView)view.findViewById(R.id.users_users_container);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        presenter.getAllUsers();
                    }
                }
            }
        });
        if(savedInstanceState != null){
            sortQuery = presenter.getCurrentFilterQuery();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_users_screen, menu);

        sortItem = menu.findItem(R.id.users_action_sort);
        sortItem.collapseActionView();

        filterItem = menu.findItem(R.id.users_action_filter);
        filterEditText = (EditText)getActivity().getLayoutInflater()
                .inflate(R.layout.users_search_edit_text, null, false);
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
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(sortQuery != null && !sortQuery.isEmpty()) {
            filterItem.expandActionView();
            filterEditText.setText(sortQuery);
            filterEditText.requestFocus();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (v.getId() == R.id.users_search_edit_text) {
                int leftEdgeOfRightDrawable = filterEditText.getRight()
                        - filterEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                if (event.getRawX() >= leftEdgeOfRightDrawable) {
                    filterItem.collapseActionView();
                    presenter.onFilterClose();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.users_action_filter:
                sortItem.collapseActionView();
                item.expandActionView();
                return true;
            case R.id.users_action_sort:
                filterItem.collapseActionView();
                item.expandActionView();
                return true;
            case R.id.filter_default:
                presenter.sortDefault();
                sortItem.collapseActionView();
                return true;
            case R.id.filter_name_asc:
                presenter.sortByNameAsc();
                sortItem.collapseActionView();
                return true;
            case R.id.filter_name_desc:
                presenter.sortByNameDesc();
                sortItem.collapseActionView();
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
        recyclerView.setVisibility(View.GONE);
        aloneMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUserInfo(UserModel user){
        Fragment fragment = new UserInfoFragment();
        ImageView sharedImage = presenter.getAdapter().getClickedUserImage();
        TextView textView = presenter.getAdapter().getClickedTextView();

        Bundle bundle = new Bundle();
        bundle.putLong(UserInfoFragment.ID_BUNDLE_KEY, user.getUserId());
        bundle.putString(UserInfoFragment.EMAIL_BUNDLE_KEY, user.getEmail());
        bundle.putString(UserInfoFragment.PHONE_BUNDLE_KEY, user.getPhone());
        bundle.putString(UserInfoFragment.WEBSITE_BUNDLE_KEY, user.getWebsite());
        bundle.putString(UserInfoFragment.FULL_NAME_BUNDLE_KEY, user.getFullName());
        bundle.putParcelable(UserInfoFragment.AVATAR_BUNDLE_KEY, ((BitmapDrawable)sharedImage.getDrawable()).getBitmap());
        fragment.setArguments(bundle);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet sharedElementTransition = (TransitionSet)TransitionInflater
                    .from(getActivity())
                    .inflateTransition(R.transition.user_shared_element);
            TransitionSet userInfoTransitionIn = (TransitionSet)TransitionInflater
                    .from(getActivity())
                    .inflateTransition(R.transition.user_info_transition_in);
            TransitionSet userInfoTransitionOut = (TransitionSet)TransitionInflater
                    .from(getActivity())
                    .inflateTransition(R.transition.user_info_transition_out);
            TransitionSet usersTransitionIn = (TransitionSet)TransitionInflater
                    .from(getActivity())
                    .inflateTransition(R.transition.users_transition_in);
            TransitionSet usersTransitionOut = (TransitionSet)TransitionInflater
                    .from(getActivity())
                    .inflateTransition(R.transition.users_transition_out);
            fragment.setSharedElementEnterTransition(sharedElementTransition);
            fragment.setEnterTransition(userInfoTransitionIn);
            fragment.setReturnTransition(userInfoTransitionOut);
            fragment.setSharedElementReturnTransition(sharedElementTransition);
            setReenterTransition(usersTransitionIn);
            setExitTransition(usersTransitionOut);
        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_screen_container, fragment)
                .addSharedElement(sharedImage, getString(R.string.transition_user_avatar_name))
                .addSharedElement(textView, getString(R.string.transition_user_full_name))
                .addToBackStack(null)
                .commit();
    }
}
