package com.internship.pbt.bizarechat.presentation.view.fragment.friends;

import java.util.Set;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class InviteFriendsView$$State extends MvpViewState<InviteFriendsView> implements InviteFriendsView {
	private ViewCommands<InviteFriendsView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(InviteFriendsView view, Set<ViewCommand<InviteFriendsView>> currentState) {
		if (mViewCommands.isEmpty()) {
			return;
		}

		mViewCommands.reapply(view, currentState);
	}

	@Override
	public  void sendEmail() {
		SendEmailCommand sendEmailCommand = new SendEmailCommand();
		mViewCommands.beforeApply(sendEmailCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(InviteFriendsView view : mViews) {
			getCurrentState(view).add(sendEmailCommand);
			view.sendEmail();
		}

		mViewCommands.afterApply(sendEmailCommand);
	}


	public class SendEmailCommand extends ViewCommand<InviteFriendsView> {
		SendEmailCommand() {
			super("sendEmail", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(InviteFriendsView mvpView) {
			mvpView.sendEmail();
			getCurrentState(mvpView).add(this);
		}
	}
}
