package com.internship.pbt.bizarechat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;


public class NewChatUsersRecyclerAdapter extends RecyclerView.Adapter<NewChatUsersRecyclerAdapter.UserHolder> {
    private OnCheckBoxClickListener listener;
    private Context context;
    private List<UserModel> users;
    private Map<Long, Bitmap> usersPhotos;
    private Set<Long> checkedUsers;

    public NewChatUsersRecyclerAdapter(List<UserModel> users,
                                       Map<Long, Bitmap> usersPhotos,
                                       Set<Long> checkedUsers) {
        this.users = users;
        this.usersPhotos = usersPhotos;
        this.checkedUsers = checkedUsers;
    }

    public NewChatUsersRecyclerAdapter setContext(Context context) {
        this.context = context;
        return this;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_chat_recycler_item, parent, false);
        return new UserHolder(view);
    }

    public NewChatUsersRecyclerAdapter setListener(OnCheckBoxClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        UserModel user = users.get(position);
        Bitmap photo = usersPhotos.get(user.getUserId());
        if (photo != null)
            holder.photo.setImageBitmap(photo);
        else
            holder.photo.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));
        holder.name.setText(user.getFullName());
        boolean isChecked = checkedUsers.contains(user.getUserId());
        holder.userCheckBox.setChecked(isChecked);
        if (isChecked)
            holder.name.setTextColor(context.getResources().getColor(R.color.new_chat_member_name_checked));
        else
            holder.name.setTextColor(context.getResources().getColor(R.color.new_chat_member_name));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private CircleImageView photo;
        private TextView name;
        private AppCompatCheckBox userCheckBox;

        public UserHolder(View itemView) {
            super(itemView);
            photo = (CircleImageView) itemView.findViewById(R.id.new_chat_member_image);
            name = (TextView) itemView.findViewById(R.id.new_chat_member_name);
            userCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.new_chat_member_checkbox);
            userCheckBox.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        if (isChecked) {
                            checkedUsers.add(users.get(getAdapterPosition()).getUserId());
                            name.setTextColor(context.getResources().getColor(R.color.new_chat_member_name_checked));
                        } else {
                            checkedUsers.remove(users.get(getAdapterPosition()).getUserId());
                            name.setTextColor(context.getResources().getColor(R.color.new_chat_member_name));
                        }
                        listener.onCheckBoxClick();
                    });
        }
    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick();
    }
}