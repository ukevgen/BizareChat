package com.internship.pbt.bizarechat.presentation.presenter.editchat;

import com.arellomobile.mvp.ViewStateProvider;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.MvpViewState;

public class EditChatPresenterImpl$$ViewStateProvider extends ViewStateProvider {
	
	@Override
	public MvpViewState<? extends MvpView> getViewState() {
		return new com.internship.pbt.bizarechat.presentation.view.fragment.editchat.EditChatView$$State();
	}
}