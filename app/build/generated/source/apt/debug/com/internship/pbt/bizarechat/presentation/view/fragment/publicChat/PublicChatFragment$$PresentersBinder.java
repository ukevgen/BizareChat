package com.internship.pbt.bizarechat.presentation.view.fragment.publicChat;

import java.util.ArrayList;
import java.util.List;

import com.arellomobile.mvp.PresenterBinder;
import com.arellomobile.mvp.presenter.PresenterField;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

public class PublicChatFragment$$PresentersBinder extends PresenterBinder<com.internship.pbt.bizarechat.presentation.view.fragment.publicChat.PublicChatFragment> {
	public class presenterBinder extends PresenterField {
		public presenterBinder() {
			super(null, PresenterType.LOCAL, null, com.internship.pbt.bizarechat.presentation.presenter.chats.PublicChatPresenter.class);
		}

		@Override
		public void bind(Object target, MvpPresenter presenter) {
			((com.internship.pbt.bizarechat.presentation.view.fragment.publicChat.PublicChatFragment) target).presenter = (com.internship.pbt.bizarechat.presentation.presenter.chats.PublicChatPresenter) presenter;
		}

		@Override
		public MvpPresenter<?> providePresenter(Object delegated) {
			return ((com.internship.pbt.bizarechat.presentation.view.fragment.publicChat.PublicChatFragment) delegated).provideChatPresenter();
		}
	}

	public List<PresenterField<?, ? super com.internship.pbt.bizarechat.presentation.view.fragment.publicChat.PublicChatFragment>> getPresenterFields() {
		List<PresenterField<?, ? super com.internship.pbt.bizarechat.presentation.view.fragment.publicChat.PublicChatFragment>> presenters = new ArrayList<>();

		presenters.add(new presenterBinder());

		return presenters;
	}

}
