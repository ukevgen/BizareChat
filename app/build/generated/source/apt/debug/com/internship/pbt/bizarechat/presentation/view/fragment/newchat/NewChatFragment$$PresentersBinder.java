package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;

import java.util.ArrayList;
import java.util.List;

import com.arellomobile.mvp.PresenterBinder;
import com.arellomobile.mvp.presenter.PresenterField;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

public class NewChatFragment$$PresentersBinder extends PresenterBinder<com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment> {
	public class presenterBinder extends PresenterField {
		public presenterBinder() {
			super(null, PresenterType.LOCAL, null, com.internship.pbt.bizarechat.presentation.presenter.newchat.NewChatPresenterImpl.class);
		}

		@Override
		public void bind(Object target, MvpPresenter presenter) {
			((com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment) target).presenter = (com.internship.pbt.bizarechat.presentation.presenter.newchat.NewChatPresenterImpl) presenter;
		}

		@Override
		public MvpPresenter<?> providePresenter(Object delegated) {
			return ((com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment) delegated).provideNewChatPresenter();
		}
	}

	public List<PresenterField<?, ? super com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment>> getPresenterFields() {
		List<PresenterField<?, ? super com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment>> presenters = new ArrayList<>();

		presenters.add(new presenterBinder());

		return presenters;
	}

}
