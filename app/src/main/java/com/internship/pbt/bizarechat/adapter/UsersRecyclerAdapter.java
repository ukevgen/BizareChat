package com.internship.pbt.bizarechat.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.adapter.filters.UsersSearchFilter;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserHolder>{
    private UsersSearchFilter filter;
    private List<UserModel> users;
    private Map<Long, Bitmap> usersPhotos;
    private Context context;

    public UsersRecyclerAdapter(List<UserModel> users, Map<Long, Bitmap> usersPhotos) {
        this.users = users;
        this.usersPhotos = usersPhotos;
        filter = new UsersSearchFilter(users, this);
    }

    public UsersRecyclerAdapter setContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_recycler_item, parent, false);

        return new UserHolder(view);
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public void filterList(String value){
        filter.filter(value);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        UserModel user = users.get(position);
        Bitmap photo = usersPhotos.get(user.getUserId());
        holder.userName.setText(user.getFullName());
        if(photo != null)
            holder.userPhoto.setImageBitmap(photo);
        else
            holder.userPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserHolder extends RecyclerView.ViewHolder{
        private CircleImageView userPhoto;
        private TextView userName;

        public UserHolder(View itemView) {
            super(itemView);
            userPhoto = (CircleImageView)itemView.findViewById(R.id.users_user_avatar);
            userName = (TextView)itemView.findViewById(R.id.users_user_name);
        }
    }
}
