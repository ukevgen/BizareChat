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

public class EditChatRecyclerViewAdapter extends RecyclerView.Adapter<EditChatRecyclerViewAdapter.UserHolder> {
    private EditChatRecyclerViewAdapter.OnCheckBoxClickListener listener;
    private Context context;
    private List<UserModel> users;
    private Map<Long, Bitmap> usersPhotos;
    private Set<Long> checkedUsers;

    public EditChatRecyclerViewAdapter(List<UserModel> users,
                                       Map<Long, Bitmap> usersPhotos,
                                       Set<Long> checkedUsers) {
        this.users = users;
        this.usersPhotos = usersPhotos;
        this.checkedUsers = checkedUsers;
    }

    public EditChatRecyclerViewAdapter setContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public EditChatRecyclerViewAdapter.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_chat_recycler_item, parent, false);
        return new EditChatRecyclerViewAdapter.UserHolder(view);
    }

    public EditChatRecyclerViewAdapter setListener(EditChatRecyclerViewAdapter.OnCheckBoxClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onBindViewHolder(EditChatRecyclerViewAdapter.UserHolder holder, int position) {
        UserModel user = users.get(position);
        Bitmap photo = usersPhotos.get(user.getUserId());
        if (photo != null) {
            holder.photo.setImageBitmap(photo);
        } else {
            holder.photo.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));
        }
        holder.name.setText(user.getFullName());
        boolean isChecked = checkedUsers.contains(user.getUserId());
        holder.userCheckBox.setChecked(isChecked);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnCheckBoxClickListener {

        void onCheckBoxClickPush(Long id);

        void onCheckBoxClickPull(Long id);
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
                        long userId = users.get(getAdapterPosition()).getUserId();
                        if (isChecked) {
                            checkedUsers.add(userId);
                            name.setTextColor(context.getResources().getColor(R.color.new_chat_member_name_checked));
                            listener.onCheckBoxClickPush(userId);
                        } else {
                            checkedUsers.remove(userId);
                            name.setTextColor(context.getResources().getColor(R.color.new_chat_member_name));
                            listener.onCheckBoxClickPull(userId);
                        }
                    });
        }
    }
}

