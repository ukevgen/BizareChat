package com.internship.pbt.bizarechat.presentation.view.fragment.chatroom;

import java.util.ArrayList;
import java.util.List;

import com.arellomobile.mvp.PresenterBinder;
import com.arellomobile.mvp.presenter.PresenterField;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

public class ChatRoomFragment$$PresentersBinder extends PresenterBinder<com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomFragment> {
	public class presenterBinder extends PresenterField {
		public presenterBinder() {
			super(null, PresenterType.LOCAL, null, com.internship.pbt.bizarechat.presentation.presenter.chatroom.ChatRoomPresenterImpl.class);
		}

		@Override
		public void bind(Object target, MvpPresenter presenter) {
			((com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomFragment) target).presenter = (com.internship.pbt.bizarechat.presentation.presenter.chatroom.ChatRoomPresenterImpl) presenter;
		}

		@Override
		public MvpPresenter<?> providePresenter(Object delegated) {
			return ((com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomFragment) delegated).provideChatRoomPresenter();
		}
	}

	public List<PresenterField<?, ? super com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomFragment>> getPresenterFields() {
		List<PresenterField<?, ? super com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomFragment>> presenters = new ArrayList<>();

		presenters.add(new presenterBinder());

		return presenters;
	}

}
