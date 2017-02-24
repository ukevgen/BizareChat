package com.internship.pbt.bizarechat.presentation.presenter.chats;

import com.arellomobile.mvp.ViewStateProvider;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.MvpViewState;

public class PrivateChatPresenter$$ViewStateProvider extends ViewStateProvider {
	
	@Override
	public MvpViewState<? extends MvpView> getViewState() {
		return new com.internship.pbt.bizarechat.presentation.view.fragment.privateChat.PrivateChatView$$State();
	}
}