package com.internship.pbt.bizarechat.presentation.view.fragment.dialogs;

import java.util.ArrayList;
import java.util.List;

import com.arellomobile.mvp.PresenterBinder;
import com.arellomobile.mvp.presenter.PresenterField;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

public class PrivateDialogsFragment$$PresentersBinder extends PresenterBinder<com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PrivateDialogsFragment> {
	public class presenterBinder extends PresenterField {
		public presenterBinder() {
			super(null, PresenterType.LOCAL, null, com.internship.pbt.bizarechat.presentation.presenter.dialogs.DialogsPresenterImp.class);
		}

		@Override
		public void bind(Object target, MvpPresenter presenter) {
			((com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PrivateDialogsFragment) target).presenter = (com.internship.pbt.bizarechat.presentation.presenter.dialogs.DialogsPresenterImp) presenter;
		}

		@Override
		public MvpPresenter<?> providePresenter(Object delegated) {
			return ((com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PrivateDialogsFragment) delegated).provideNewDialogsPresenter();
		}
	}

	public List<PresenterField<?, ? super com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PrivateDialogsFragment>> getPresenterFields() {
		List<PresenterField<?, ? super com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PrivateDialogsFragment>> presenters = new ArrayList<>();

		presenters.add(new presenterBinder());

		return presenters;
	}

}
