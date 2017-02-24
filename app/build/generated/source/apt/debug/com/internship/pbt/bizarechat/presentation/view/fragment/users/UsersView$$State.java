package com.internship.pbt.bizarechat.presentation.view.fragment.users;

import java.util.Set;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class UsersView$$State extends MvpViewState<UsersView> implements UsersView {
	private ViewCommands<UsersView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(UsersView view, Set<ViewCommand<UsersView>> currentState) {
		if (mViewCommands.isEmpty()) {
			return;
		}

		mViewCommands.reapply(view, currentState);
	}

	@Override
	public  void showAloneMessage() {
		ShowAloneMessageCommand showAloneMessageCommand = new ShowAloneMessageCommand();
		mViewCommands.beforeApply(showAloneMessageCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(UsersView view : mViews) {
			getCurrentState(view).add(showAloneMessageCommand);
			view.showAloneMessage();
		}

		mViewCommands.afterApply(showAloneMessageCommand);
	}


	public class ShowAloneMessageCommand extends ViewCommand<UsersView> {
		ShowAloneMessageCommand() {
			super("showAloneMessage", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(UsersView mvpView) {
			mvpView.showAloneMessage();
			getCurrentState(mvpView).add(this);
		}
	}
}
