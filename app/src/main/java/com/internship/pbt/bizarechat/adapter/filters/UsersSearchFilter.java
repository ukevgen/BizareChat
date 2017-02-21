package com.internship.pbt.bizarechat.adapter.filters;


import android.text.TextUtils;
import android.widget.Filter;

import com.internship.pbt.bizarechat.adapter.UsersRecyclerAdapter;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UsersSearchFilter extends Filter {
    private List<UserModel> userList;
    private List<UserModel> filteredUserList;
    private UsersRecyclerAdapter adapter;

    public UsersSearchFilter(List<UserModel> userList, UsersRecyclerAdapter adapter) {
        this.userList = userList;
        this.adapter = adapter;
        filteredUserList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredUserList.clear();
        FilterResults results = new FilterResults();

        if(TextUtils.isEmpty(constraint)){
            results.values = userList;
            results.count = userList.size();
            return results;
        }

        for(UserModel user : userList){
            if(user.getFullName() == null) continue;

            if(user.getFullName().toLowerCase().contains(constraint.toString().toLowerCase())){
                filteredUserList.add(user);
            }
        }
        results.values = filteredUserList;
        results.count = filteredUserList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setUsers((List<UserModel>)results.values);
        adapter.notifyDataSetChanged();
    }
}
