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
	public  void showLoading() {
		ShowLoadingCommand showLoadingCommand = new ShowLoadingCommand();
		mViewCommands.beforeApply(showLoadingCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(UsersView view : mViews) {
			getCurrentState(view).add(showLoadingCommand);
			view.showLoading();
		}

		mViewCommands.afterApply(showLoadingCommand);
	}

	@Override
	public  void hideLoading() {
		HideLoadingCommand hideLoadingCommand = new HideLoadingCommand();
		mViewCommands.beforeApply(hideLoadingCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(UsersView view : mViews) {
			getCurrentState(view).add(hideLoadingCommand);
			view.hideLoading();
		}

		mViewCommands.afterApply(hideLoadingCommand);
	}

	@Override
	public  void showNetworkError() {
		ShowNetworkErrorCommand showNetworkErrorCommand = new ShowNetworkErrorCommand();
		mViewCommands.beforeApply(showNetworkErrorCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(UsersView view : mViews) {
			getCurrentState(view).add(showNetworkErrorCommand);
			view.showNetworkError();
		}

		mViewCommands.afterApply(showNetworkErrorCommand);
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


	public class ShowLoadingCommand extends ViewCommand<UsersView> {
		ShowLoadingCommand() {
			super("showLoading", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(UsersView mvpView) {
			mvpView.showLoading();
			getCurrentState(mvpView).add(this);
		}
	}

	public class HideLoadingCommand extends ViewCommand<UsersView> {
		HideLoadingCommand() {
			super("hideLoading", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(UsersView mvpView) {
			mvpView.hideLoading();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowNetworkErrorCommand extends ViewCommand<UsersView> {
		ShowNetworkErrorCommand() {
			super("showNetworkError", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(UsersView mvpView) {
			mvpView.showNetworkError();
			getCurrentState(mvpView).add(this);
		}
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
