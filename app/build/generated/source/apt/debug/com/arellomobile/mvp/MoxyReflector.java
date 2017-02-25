package com.arellomobile.mvp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoxyReflector {

	private static Map<Class<?>, Object> sViewStateProviders;
	private static Map<Class<?>, List<Object>> sPresenterBinders;
	private static Map<Class<?>, Object> sStrategies;

	static {
		sViewStateProviders = new HashMap<>();
		sViewStateProviders.put(com.internship.pbt.bizarechat.presentation.presenter.users.UsersPresenter.class, new com.internship.pbt.bizarechat.presentation.presenter.users.UsersPresenter$$ViewStateProvider());
		sViewStateProviders.put(com.internship.pbt.bizarechat.presentation.presenter.friends.InviteFriendsPresenterImpl.class, new com.internship.pbt.bizarechat.presentation.presenter.friends.InviteFriendsPresenterImpl$$ViewStateProvider());
		sViewStateProviders.put(com.internship.pbt.bizarechat.presentation.presenter.dialogs.DialogsPresenterImp.class, new com.internship.pbt.bizarechat.presentation.presenter.dialogs.DialogsPresenterImp$$ViewStateProvider());
		sViewStateProviders.put(com.internship.pbt.bizarechat.presentation.presenter.newchat.NewChatPresenterImpl.class, new com.internship.pbt.bizarechat.presentation.presenter.newchat.NewChatPresenterImpl$$ViewStateProvider());
		sViewStateProviders.put(com.internship.pbt.bizarechat.presentation.presenter.editchat.EditChatPresenterImpl.class, new com.internship.pbt.bizarechat.presentation.presenter.editchat.EditChatPresenterImpl$$ViewStateProvider());
		sViewStateProviders.put(com.internship.pbt.bizarechat.presentation.presenter.chatroom.ChatRoomPresenterImpl.class, new com.internship.pbt.bizarechat.presentation.presenter.chatroom.ChatRoomPresenterImpl$$ViewStateProvider());
		sViewStateProviders.put(com.internship.pbt.bizarechat.presentation.presenter.main.MainPresenterImpl.class, new com.internship.pbt.bizarechat.presentation.presenter.main.MainPresenterImpl$$ViewStateProvider());
		
		sPresenterBinders = new HashMap<>();
		sPresenterBinders.put(com.internship.pbt.bizarechat.presentation.view.fragment.users.UsersFragment.class, Arrays.<Object>asList(new com.internship.pbt.bizarechat.presentation.view.fragment.users.UsersFragment$$PresentersBinder()));
		sPresenterBinders.put(com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment.class, Arrays.<Object>asList(new com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatFragment$$PresentersBinder()));
		sPresenterBinders.put(com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PrivateDialogsFragment.class, Arrays.<Object>asList(new com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PrivateDialogsFragment$$PresentersBinder()));
		sPresenterBinders.put(com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PublicDialogsFragment.class, Arrays.<Object>asList(new com.internship.pbt.bizarechat.presentation.view.fragment.dialogs.PublicDialogsFragment$$PresentersBinder()));
		sPresenterBinders.put(com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomFragment.class, Arrays.<Object>asList(new com.internship.pbt.bizarechat.presentation.view.fragment.chatroom.ChatRoomFragment$$PresentersBinder()));
		sPresenterBinders.put(com.internship.pbt.bizarechat.presentation.view.activity.MainActivity.class, Arrays.<Object>asList(new com.internship.pbt.bizarechat.presentation.view.activity.MainActivity$$PresentersBinder()));
		sPresenterBinders.put(com.internship.pbt.bizarechat.presentation.view.fragment.editchat.EditChatFragment.class, Arrays.<Object>asList(new com.internship.pbt.bizarechat.presentation.view.fragment.editchat.EditChatFragment$$PresentersBinder()));
		sPresenterBinders.put(com.internship.pbt.bizarechat.presentation.view.fragment.friends.InviteFriendsFragment.class, Arrays.<Object>asList(new com.internship.pbt.bizarechat.presentation.view.fragment.friends.InviteFriendsFragment$$PresentersBinder()));
		
		sStrategies = new HashMap<>();
		sStrategies.put(com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy.class, new com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy());
		sStrategies.put(com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class, new com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy());
	}
	
	public static Object getViewState(Class<?> presenterClass) {
		ViewStateProvider viewStateProvider = (ViewStateProvider) sViewStateProviders.get(presenterClass);
		if (viewStateProvider == null) {
			return null;
		}
		
		return viewStateProvider.getViewState();
	}

	public static List<Object> getPresenterBinders(Class<?> delegated) {
		return sPresenterBinders.get(delegated);
	}
	public static Object getStrategy(Class<?> strategyClass) {
		return sStrategies.get(strategyClass);
	}
}
