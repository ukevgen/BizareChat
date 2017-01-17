package com.internship.pbt.bizarechat.presentation.navigation;

public class Navigator {

    private static Navigator mInstance;

    public static Navigator getInstance(){

        if (mInstance == null)
            mInstance = new Navigator();

        return mInstance;
    }
    private Navigator() {
        super();
    }

    //TODO Navigate method`s
}
