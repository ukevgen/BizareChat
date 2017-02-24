package com.internship.pbt.bizarechat.presentation.view.fragment.privateChat;

import java.util.Set;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class PrivateChatView$$State extends MvpViewState<PrivateChatView> implements PrivateChatView {
	private ViewCommands<PrivateChatView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(PrivateChatView view, Set<ViewCommand<PrivateChatView>> currentState) {
		if (mViewCommands.isEmpty()) {
			return;
		}

		mViewCommands.reapply(view, currentState);
	}

}
