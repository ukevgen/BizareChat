package com.internship.pbt.bizarechat.presentation.presenter.main;


import com.internship.pbt.bizarechat.presentation.presenter.Presenter;
import com.internship.pbt.bizarechat.presentation.view.fragment.main.MainView;

public interface MainPresenter extends Presenter {

    void logout();

    void setMainView(MainView view);

    void navigateToNewChat();



}
