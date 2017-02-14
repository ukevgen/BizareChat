package com.internship.pbt.bizarechat.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.net.requests.Chats;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsRecyclerViewAdapter extends RecyclerView.Adapter<ChatsRecyclerViewAdapter.ChatsHolder> {

    private List<Chats> chats;
    OnNewMessageCallback newMessageCallback;
    ChatDelete chatDelete;
    ChatClick chatClick;

    @Override
    public ChatsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chats_recycler_item, parent, false);

        return new ChatsHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatsHolder holder, int position) {
        holder.setChatClick(chatClick);
        holder.setChatDelete(chatDelete);
        holder.setNewMessageCallback(newMessageCallback);
        holder.setPosition(position);
        // TODO implement logic
    }

    @Override
    public int getItemCount() {
        return chats == null ? 0 : chats.size();
    }

    public interface OnNewMessageCallback {
        void onNewMessageCallback();
    }

    public interface ChatClick {
        void chatClick();
    }

    public interface ChatDelete {
        void chatDelete();
    }


    public ChatsRecyclerViewAdapter setNewMessageCallback(OnNewMessageCallback callback) {
        this.newMessageCallback = callback;
        return this;
    }

    public ChatsRecyclerViewAdapter setChatDelete(ChatDelete chatDelete) {
        this.chatDelete = chatDelete;
        return this;
    }

    public ChatsRecyclerViewAdapter setChatClick(ChatClick chatClick) {
        this.chatClick = chatClick;
        return this;
    }

    public void setChats(List<Chats> chats) {
        this.chats = chats;
    }


    public static class ChatsHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        int position;
        TextView mMessageAuthor,
                mLastMessage,
                mLastMessageDate,
                mNewMessageIndicator;


        public ChatsHolder(View itemView) {
            super(itemView);
            mLastMessage = (TextView) itemView.findViewById(R.id.chats_item_last_message);
            mLastMessageDate = (TextView) itemView.findViewById(R.id.chats_item_last_message_date);
            mNewMessageIndicator = (TextView) itemView.findViewById(R.id.new_message_indicator);
            mMessageAuthor = (TextView) itemView.findViewById(R.id.chats_item_last_message_author);
            imageView = (CircleImageView) itemView.findViewById(R.id.chats_item_chat_image);
        }

        public ChatsHolder setPosition(int position) {
            this.position = position;
            return this;
        }

        public ChatsHolder setNewMessageCallback(OnNewMessageCallback messageCallback) {
            return this;
        }

        public ChatsHolder setChatDelete(ChatDelete chatDelete) {
            return this;
        }

        public ChatsHolder setChatClick(ChatClick chatClick) {
            return this;
        }

    }
}
