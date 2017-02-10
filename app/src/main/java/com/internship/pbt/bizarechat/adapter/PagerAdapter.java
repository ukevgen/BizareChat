package com.internship.pbt.bizarechat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.internship.pbt.bizarechat.presentation.view.fragment.privateChat.PrivateChatFragment;
import com.internship.pbt.bizarechat.presentation.view.fragment.publicChat.PublicChatFragment;

/**
 * Created by user on 10.02.2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PublicChatFragment();
            case 1:
                return new PrivateChatFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
