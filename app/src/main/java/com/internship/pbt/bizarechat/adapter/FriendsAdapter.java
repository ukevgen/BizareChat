package com.internship.pbt.bizarechat.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.model.ContactsFriends;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsHolder> {
    private List<ContactsFriends> items;
    private Context context;

    public FriendsAdapter(List<ContactsFriends> items) {
        this.items = items;
    }

    public List<ContactsFriends> getItems() {
        return items;
    }

    @Override
    public FriendsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_invite_friends, parent, false);
        FriendsHolder holder = new FriendsHolder(view, this);
        return holder;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(FriendsHolder holder, int position) {
        final ContactsFriends friend = items.get(position);
        holder.setPosition(position);
        holder.mName.setText(friend.getName());
        holder.mEmail.setText(friend.getEmail());
        if (friend.getUserPic() != null) {
            holder.mImageView.setImageBitmap(friend.getUserPic());
        } else {
            holder.mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));
        }

        if (friend.isChecked()) {
            holder.mName.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.mName.setTextColor(context.getResources().getColor(R.color.chats_item_last_message));
        }

        //in some cases, it will prevent unwanted situations
        holder.mCheckBox.setOnCheckedChangeListener(null);
        //if true, your checkbox will be selected, else unselected
        holder.mCheckBox.setChecked(friend.isChecked());


        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                friend.setChecked(isChecked);
                if (isChecked) {
                    holder.mName.setTextColor(context.getResources().getColor(R.color.black));
                } else {
                    holder.mName.setTextColor(context.getResources().getColor(R.color.chats_item_last_message));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class FriendsHolder extends RecyclerView.ViewHolder {

        ContactsFriends item;
        FriendsAdapter adapter;
        CircleImageView mImageView;
        CheckBox mCheckBox;
        int position;
        TextView mName,
                mEmail;

        public FriendsHolder(View itemView, FriendsAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            mImageView = (CircleImageView) itemView.findViewById(R.id.friend_pic);
            mName = (TextView) itemView.findViewById(R.id.friend_name);
            mEmail = (TextView) itemView.findViewById(R.id.friend_email);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.check_friend);
        }

        public FriendsHolder setPosition(int position) {
            this.position = position;
            return this;
        }

        public void setItem(ContactsFriends item) {
            this.item = item;
        }


    }
}
