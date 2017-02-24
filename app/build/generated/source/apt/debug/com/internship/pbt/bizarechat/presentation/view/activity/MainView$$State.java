package com.internship.pbt.bizarechat.presentation.view.activity;

import java.util.Set;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class MainView$$State extends MvpViewState<MainView> implements MainView {
	private ViewCommands<MainView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(MainView view, Set<ViewCommand<MainView>> currentState) {
		if (mViewCommands.isEmpty()) {
			return;
		}

		mViewCommands.reapply(view, currentState);
	}

	@Override
	public  void hideEmptyScreen() {
		HideEmptyScreenCommand hideEmptyScreenCommand = new HideEmptyScreenCommand();
		mViewCommands.beforeApply(hideEmptyScreenCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(hideEmptyScreenCommand);
			view.hideEmptyScreen();
		}

		mViewCommands.afterApply(hideEmptyScreenCommand);
	}

	@Override
	public  void showEmptyScreen() {
		ShowEmptyScreenCommand showEmptyScreenCommand = new ShowEmptyScreenCommand();
		mViewCommands.beforeApply(showEmptyScreenCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(showEmptyScreenCommand);
			view.showEmptyScreen();
		}

		mViewCommands.afterApply(showEmptyScreenCommand);
	}

	@Override
	public  void showLackOfFriends() {
		ShowLackOfFriendsCommand showLackOfFriendsCommand = new ShowLackOfFriendsCommand();
		mViewCommands.beforeApply(showLackOfFriendsCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(showLackOfFriendsCommand);
			view.showLackOfFriends();
		}

		mViewCommands.afterApply(showLackOfFriendsCommand);
	}

	@Override
	public  void startNewChatView() {
		StartNewChatViewCommand startNewChatViewCommand = new StartNewChatViewCommand();
		mViewCommands.beforeApply(startNewChatViewCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(startNewChatViewCommand);
			view.startNewChatView();
		}

		mViewCommands.afterApply(startNewChatViewCommand);
	}

	@Override
	public  void showInviteFriendsScreen() {
		ShowInviteFriendsScreenCommand showInviteFriendsScreenCommand = new ShowInviteFriendsScreenCommand();
		mViewCommands.beforeApply(showInviteFriendsScreenCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(showInviteFriendsScreenCommand);
			view.showInviteFriendsScreen();
		}

		mViewCommands.afterApply(showInviteFriendsScreenCommand);
	}

	@Override
	public  void showNavigationElements() {
		ShowNavigationElementsCommand showNavigationElementsCommand = new ShowNavigationElementsCommand();
		mViewCommands.beforeApply(showNavigationElementsCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(showNavigationElementsCommand);
			view.showNavigationElements();
		}

		mViewCommands.afterApply(showNavigationElementsCommand);
	}

	@Override
	public  void hideNavigationElements() {
		HideNavigationElementsCommand hideNavigationElementsCommand = new HideNavigationElementsCommand();
		mViewCommands.beforeApply(hideNavigationElementsCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(hideNavigationElementsCommand);
			view.hideNavigationElements();
		}

		mViewCommands.afterApply(hideNavigationElementsCommand);
	}

	@Override
	public  void navigateToLoginScreen() {
		NavigateToLoginScreenCommand navigateToLoginScreenCommand = new NavigateToLoginScreenCommand();
		mViewCommands.beforeApply(navigateToLoginScreenCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(navigateToLoginScreenCommand);
			view.navigateToLoginScreen();
		}

		mViewCommands.afterApply(navigateToLoginScreenCommand);
	}

	@Override
	public  void startUsersView() {
		StartUsersViewCommand startUsersViewCommand = new StartUsersViewCommand();
		mViewCommands.beforeApply(startUsersViewCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(startUsersViewCommand);
			view.startUsersView();
		}

		mViewCommands.afterApply(startUsersViewCommand);
	}

	@Override
	public  void confirmLogOut() {
		ConfirmLogOutCommand confirmLogOutCommand = new ConfirmLogOutCommand();
		mViewCommands.beforeApply(confirmLogOutCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(confirmLogOutCommand);
			view.confirmLogOut();
		}

		mViewCommands.afterApply(confirmLogOutCommand);
	}

	@Override
	public  void startBackPressed() {
		StartBackPressedCommand startBackPressedCommand = new StartBackPressedCommand();
		mViewCommands.beforeApply(startBackPressedCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(startBackPressedCommand);
			view.startBackPressed();
		}

		mViewCommands.afterApply(startBackPressedCommand);
	}

	@Override
	public  void showDialogs() {
		ShowDialogsCommand showDialogsCommand = new ShowDialogsCommand();
		mViewCommands.beforeApply(showDialogsCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(showDialogsCommand);
			view.showDialogs();
		}

		mViewCommands.afterApply(showDialogsCommand);
	}

	@Override
	public  void showPublicDialogs() {
		ShowPublicDialogsCommand showPublicDialogsCommand = new ShowPublicDialogsCommand();
		mViewCommands.beforeApply(showPublicDialogsCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(showPublicDialogsCommand);
			view.showPublicDialogs();
		}

		mViewCommands.afterApply(showPublicDialogsCommand);
	}

	@Override
	public  void showPrivateDialogs() {
		ShowPrivateDialogsCommand showPrivateDialogsCommand = new ShowPrivateDialogsCommand();
		mViewCommands.beforeApply(showPrivateDialogsCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(MainView view : mViews) {
			getCurrentState(view).add(showPrivateDialogsCommand);
			view.showPrivateDialogs();
		}

		mViewCommands.afterApply(showPrivateDialogsCommand);
	}


	public class HideEmptyScreenCommand extends ViewCommand<MainView> {
		HideEmptyScreenCommand() {
			super("hideEmptyScreen", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.hideEmptyScreen();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowEmptyScreenCommand extends ViewCommand<MainView> {
		ShowEmptyScreenCommand() {
			super("showEmptyScreen", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.showEmptyScreen();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowLackOfFriendsCommand extends ViewCommand<MainView> {
		ShowLackOfFriendsCommand() {
			super("showLackOfFriends", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.showLackOfFriends();
			getCurrentState(mvpView).add(this);
		}
	}

	public class StartNewChatViewCommand extends ViewCommand<MainView> {
		StartNewChatViewCommand() {
			super("startNewChatView", com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.startNewChatView();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowInviteFriendsScreenCommand extends ViewCommand<MainView> {
		ShowInviteFriendsScreenCommand() {
			super("showInviteFriendsScreen", com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.showInviteFriendsScreen();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowNavigationElementsCommand extends ViewCommand<MainView> {
		ShowNavigationElementsCommand() {
			super("showNavigationElements", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.showNavigationElements();
			getCurrentState(mvpView).add(this);
		}
	}

	public class HideNavigationElementsCommand extends ViewCommand<MainView> {
		HideNavigationElementsCommand() {
			super("hideNavigationElements", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.hideNavigationElements();
			getCurrentState(mvpView).add(this);
		}
	}

	public class NavigateToLoginScreenCommand extends ViewCommand<MainView> {
		NavigateToLoginScreenCommand() {
			super("navigateToLoginScreen", com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.navigateToLoginScreen();
			getCurrentState(mvpView).add(this);
		}
	}

	public class StartUsersViewCommand extends ViewCommand<MainView> {
		StartUsersViewCommand() {
			super("startUsersView", com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.startUsersView();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ConfirmLogOutCommand extends ViewCommand<MainView> {
		ConfirmLogOutCommand() {
			super("confirmLogOut", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.confirmLogOut();
			getCurrentState(mvpView).add(this);
		}
	}

	public class StartBackPressedCommand extends ViewCommand<MainView> {
		StartBackPressedCommand() {
			super("startBackPressed", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.startBackPressed();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowDialogsCommand extends ViewCommand<MainView> {
		ShowDialogsCommand() {
			super("showDialogs", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.showDialogs();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowPublicDialogsCommand extends ViewCommand<MainView> {
		ShowPublicDialogsCommand() {
			super("showPublicDialogs", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.showPublicDialogs();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowPrivateDialogsCommand extends ViewCommand<MainView> {
		ShowPrivateDialogsCommand() {
			super("showPrivateDialogs", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(MainView mvpView) {
			mvpView.showPrivateDialogs();
			getCurrentState(mvpView).add(this);
		}
	}
}
