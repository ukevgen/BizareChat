package com.internship.pbt.bizarechat.presentation.presenter.chatroom;

import com.arellomobile.mvp.ViewStateProvider;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.MvpViewState;

public class ChatRoomPresenterImpl$$ViewStateProvider extends ViewStateProvider {
	
	@Override
	public MvpViewState<? extends MvpView> getViewState() {
		return new com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomView$$State();
	}
}