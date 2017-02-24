package com.internship.pbt.bizarechat.presentation.view.fragment.dialogs;

import java.util.Set;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class DialogsView$$State extends MvpViewState<DialogsView> implements DialogsView {
	private ViewCommands<DialogsView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(DialogsView view, Set<ViewCommand<DialogsView>> currentState) {
		if (mViewCommands.isEmpty()) {
			return;
		}

		mViewCommands.reapply(view, currentState);
	}

	@Override
	public  void showDialogs() {
		ShowDialogsCommand showDialogsCommand = new ShowDialogsCommand();
		mViewCommands.beforeApply(showDialogsCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(DialogsView view : mViews) {
			getCurrentState(view).add(showDialogsCommand);
			view.showDialogs();
		}

		mViewCommands.afterApply(showDialogsCommand);
	}

	@Override
	public  void updateDialogs() {
		UpdateDialogsCommand updateDialogsCommand = new UpdateDialogsCommand();
		mViewCommands.beforeApply(updateDialogsCommand);

		if (mViews == null || mViews.isEmpty()) {
			return;
		}

		for(DialogsView view : mViews) {
			getCurrentState(view).add(updateDialogsCommand);
			view.updateDialogs();
		}

		mViewCommands.afterApply(updateDialogsCommand);
	}


	public class ShowDialogsCommand extends ViewCommand<DialogsView> {
		ShowDialogsCommand() {
			super("showDialogs", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(DialogsView mvpView) {
			mvpView.showDialogs();
			getCurrentState(mvpView).add(this);
		}
	}

	public class UpdateDialogsCommand extends ViewCommand<DialogsView> {
		UpdateDialogsCommand() {
			super("updateDialogs", com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class);
		}

		@Override
		public void apply(DialogsView mvpView) {
			mvpView.updateDialogs();
			getCurrentState(mvpView).add(this);
		}
	}
}
