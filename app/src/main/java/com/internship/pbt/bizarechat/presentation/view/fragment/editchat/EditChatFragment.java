package com.internship.pbt.bizarechat.presentation.view.fragment.editchat;


import android.support.design.widget.Snackbar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.editchat.EditChatPresenterImpl;


public class EditChatFragment extends MvpAppCompatFragment implements EditChatView {
    @InjectPresenter
    EditChatPresenterImpl presenter;

    @ProvidePresenter
    EditChatPresenterImpl provideEditChatPresenter(){
        return new EditChatPresenterImpl();
    }

    @Override
    public void showNoPermissionsToEdit() {
        Snackbar.make(getView(),getString(R.string.you_have_no_permissions_for_edit_this_chat), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showOnSaveChagesSuccessfully() {
        Snackbar.make(getView(), getString(R.string.successfully_edited), Snackbar.LENGTH_SHORT);
    }
}
