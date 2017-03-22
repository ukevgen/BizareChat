package com.internship.pbt.bizarechat.presentation.presenter.friends;


import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.FriendsAdapter;
import com.internship.pbt.bizarechat.presentation.model.ContactsFriends;
import com.internship.pbt.bizarechat.presentation.util.ContactDetails;
import com.internship.pbt.bizarechat.presentation.view.fragment.friends.InviteFriendsView;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class InviteFriendsPresenterImpl extends MvpPresenter<InviteFriendsView> implements
        InviteFriendsPresenter {

    private FriendsAdapter adapter;
    private List<ContactsFriends> friendsList;
    private ContactDetails contactDetails;
    private Context context;
    private List<String> emails;

    public InviteFriendsPresenterImpl(Context context) {
        this.context = context;
        contactDetails = new ContactDetails(context);
        friendsList = contactDetails.getContactsEmailDetails();
        adapter = new FriendsAdapter(friendsList);
        emails = new ArrayList<>();
    }

    public FriendsAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void getUserPhoneContacts() {

    }

    public String[] getEmail() {
        for (ContactsFriends c : adapter.getItems()) {
            if (c.isChecked()) {
                emails.add(c.getEmail().trim());
            }
        }
        return emails.toArray(new String[emails.size()]);
    }
}
