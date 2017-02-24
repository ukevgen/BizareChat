package com.internship.pbt.bizarechat.presentation.view.fragment.editchat;

import java.util.Set;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class EditChatView$$State extends MvpViewState<EditChatView> implements EditChatView {
	private ViewCommands<EditChatView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(EditChatView view, Set<ViewCommand<EditChatView>> currentState) {
		if (mViewCommands.isEmpty()) {
			return;
		}

		mViewCommands.reapply(view, currentState);
	}

	@Override
	public  void showNoPermissionsToEdit() {
		ShowNoPermissionsToEditCommand showNoPermissionsToEditCommand = new ShowNoPermissionsToEditCommand();
		mViewCommands.beforeApply(showNoPermissionsToEditCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(EditChatView view : mViews) {
			getCurrentState(view).add(showNoPermissionsToEditCommand);
			view.showNoPermissionsToEdit();
		}

		mViewCommands.afterApply(showNoPermissionsToEditCommand);
	}

	@Override
	public  void showOnSaveChangesSuccessfully() {
		ShowOnSaveChangesSuccessfullyCommand showOnSaveChangesSuccessfullyCommand = new ShowOnSaveChangesSuccessfullyCommand();
		mViewCommands.beforeApply(showOnSaveChangesSuccessfullyCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(EditChatView view : mViews) {
			getCurrentState(view).add(showOnSaveChangesSuccessfullyCommand);
			view.showOnSaveChangesSuccessfully();
		}

		mViewCommands.afterApply(showOnSaveChangesSuccessfullyCommand);
	}

	@Override
	public  void loadAvatarToImageView(java.io.File file) {
		LoadAvatarToImageViewCommand loadAvatarToImageViewCommand = new LoadAvatarToImageViewCommand(file);
		mViewCommands.beforeApply(loadAvatarToImageViewCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(EditChatView view : mViews) {
			getCurrentState(view).add(loadAvatarToImageViewCommand);
			view.loadAvatarToImageView(file);
		}

		mViewCommands.afterApply(loadAvatarToImageViewCommand);
	}

	@Override
	public  void showTooLargePicture() {
		ShowTooLargePictureCommand showTooLargePictureCommand = new ShowTooLargePictureCommand();
		mViewCommands.beforeApply(showTooLargePictureCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(EditChatView view : mViews) {
			getCurrentState(view).add(showTooLargePictureCommand);
			view.showTooLargePicture();
		}

		mViewCommands.afterApply(showTooLargePictureCommand);
	}


	public class ShowNoPermissionsToEditCommand extends ViewCommand<EditChatView> {
		ShowNoPermissionsToEditCommand() {
			super("showNoPermissionsToEdit", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(EditChatView mvpView) {
			mvpView.showNoPermissionsToEdit();
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowOnSaveChangesSuccessfullyCommand extends ViewCommand<EditChatView> {
		ShowOnSaveChangesSuccessfullyCommand() {
			super("showOnSaveChangesSuccessfully", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(EditChatView mvpView) {
			mvpView.showOnSaveChangesSuccessfully();
			getCurrentState(mvpView).add(this);
		}
	}

	public class LoadAvatarToImageViewCommand extends ViewCommand<EditChatView> {
		public final java.io.File file;

		LoadAvatarToImageViewCommand(java.io.File file) {
			super("loadAvatarToImageView", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
			this.file = file;
		}

		@Override
		public void apply(EditChatView mvpView) {
			mvpView.loadAvatarToImageView(file);
			getCurrentState(mvpView).add(this);
		}
	}

	public class ShowTooLargePictureCommand extends ViewCommand<EditChatView> {
		ShowTooLargePictureCommand() {
			super("showTooLargePicture", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(EditChatView mvpView) {
			mvpView.showTooLargePicture();
			getCurrentState(mvpView).add(this);
		}
	}
}
