package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;

import java.util.Set;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class NewChatView$$State extends MvpViewState<NewChatView> implements NewChatView {
	private ViewCommands<NewChatView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(NewChatView view, Set<ViewCommand<NewChatView>> currentState) {
		if (mViewCommands.isEmpty()) {
			return;
		}

		mViewCommands.reapply(view, currentState);
	}

	@Override
	public  void showUsersView() {
		ShowUsersViewCommand showUsersViewCommand = new ShowUsersViewCommand();
		mViewCommands.beforeApply(showUsersViewCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(NewChatView view : mViews) {
			getCurrentState(view).add(showUsersViewCommand);
			view.showUsersView();
		}

		mViewCommands.afterApply(showUsersViewCommand);
	}

	@Override
	public  void hideUsersView() {
		HideUsersViewCommand hideUsersViewCommand = new HideUsersViewCommand();
		mViewCommands.beforeApply(hideUsersViewCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(NewChatView view : mViews) {
			getCurrentState(view).add(hideUsersViewCommand);
			view.hideUsersView();
		}

		mViewCommands.afterApply(hideUsersViewCommand);
	}

	@Override
	public  void showChatPhoto() {
		ShowChatPhotoCommand showChatPhotoCommand = new ShowChatPhotoCommand();
		mViewCommands.beforeApply(showChatPhotoCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(NewChatView view : mViews) {
			getCurrentState(view).add(showChatPhotoCommand);
			view.showChatPhoto();
		}

		mViewCommands.afterApply(showChatPhotoCommand);
	}

	@Override
	public  void hideChatPhoto() {
		HideChatPhotoCommand hideChatPhotoCommand = new HideChatPhotoCommand();
		mViewCommands.beforeApply(hideChatPhotoCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(NewChatView view : mViews) {
			getCurrentState(view).add(hideChatPhotoCommand);
			view.hideChatPhoto();
		}

		mViewCommands.afterApply(hideChatPhotoCommand);
	}


	public class ShowUsersViewCommand extends ViewCommand<NewChatView> {
		ShowUsersViewCommand() {
			super("showUsersView", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(NewChatView mvpView) {
			mvpView.showUsersView();
			getCurrentState(mvpView).add(this);
		}
	}

	public class HideUsersViewCommand extends ViewCommand<NewChatView> {
		HideUsersViewCommand() {
			super("hideUsersView", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(NewChatView mvpView) {
			mvpView.hideUsersView();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowChatPhotoCommand extends ViewCommand<NewChatView> {
		ShowChatPhotoCommand() {
			super("showChatPhoto", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(NewChatView mvpView) {
			mvpView.showChatPhoto();
			getCurrentState(mvpView).add(this);
		}
	}

	public class HideChatPhotoCommand extends ViewCommand<NewChatView> {
		HideChatPhotoCommand() {
			super("hideChatPhoto", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(NewChatView mvpView) {
			mvpView.hideChatPhoto();
			getCurrentState(mvpView).add(this);
		}
	}
}
