package com.internship.pbt.bizarechat.presentation.view.fragment.chatroom;

import java.util.Set;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class ChatRoomView$$State extends MvpViewState<ChatRoomView> implements ChatRoomView {
	private ViewCommands<ChatRoomView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(ChatRoomView view, Set<ViewCommand<ChatRoomView>> currentState) {
		if (mViewCommands.isEmpty()) {
			return;
		}

		mViewCommands.reapply(view, currentState);
	}

}
