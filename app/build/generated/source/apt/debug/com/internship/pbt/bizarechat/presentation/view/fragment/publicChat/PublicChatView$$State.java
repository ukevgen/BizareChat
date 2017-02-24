package com.internship.pbt.bizarechat.presentation.view.fragment.publicChat;

import java.util.Set;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class PublicChatView$$State extends MvpViewState<PublicChatView> implements PublicChatView {
	private ViewCommands<PublicChatView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(PublicChatView view, Set<ViewCommand<PublicChatView>> currentState) {
		if (mViewCommands.isEmpty()) {
			return;
		}

		mViewCommands.reapply(view, currentState);
	}

}
