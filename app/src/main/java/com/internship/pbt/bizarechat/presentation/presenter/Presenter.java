package com.internship.pbt.bizarechat.presentation.presenter;

public interface Presenter {

    void resume();

    void pause();

    void destroy();

    // TODO: 1/30/17 [Code Review] You do not need showViewLoading() and hideViewLoading() methods everywhere
    // You'd better add these methods manually with concrete names like 'showDataLoadingView()' or 'showEmailValidationProcessView()'
    // or maybe there's an option to create LoadDataPresenter similarly with LoadDataView and extend it

    void showViewLoading();

    void hideViewLoading();

}
