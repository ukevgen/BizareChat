package com.internship.pbt.bizarechat.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogsRecyclerViewAdapter extends RecyclerView.Adapter<DialogsRecyclerViewAdapter.DialogsHolder> {

    private List<DialogModel> dialogs;
    OnNewMessageCallback newMessageCallback;
    DialogDelete dialogDelete;
    DialogClick dialogClick;

    @Override
    public DialogsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialogs_recycler_item, parent, false);

        return new DialogsHolder(v);
    }

    @Override
    public void onBindViewHolder(DialogsHolder holder, int position) {
        holder.setDialogClick(dialogClick);
        holder.setDialogDelete(dialogDelete);
        holder.setNewMessageCallback(newMessageCallback);
        holder.setPosition(position);
        holder.mLastMessage.setText(dialogs.get(position).getLastMessage());
        holder.mLastMessageDate.setText(dialogs.get(position).getLastMessage());
        holder.mMessageAuthor.setText(dialogs.get(position).getLastMessageUserId());
        holder.mNewMessageIndicator.setText(dialogs.get(position).getUnreadMessagesCount());
        holder.mTitle.setText(dialogs.get(position).getName());


        // TODO implement logic
    }

    @Override
    public int getItemCount() {
        return dialogs == null ? 0 : dialogs.size();
    }

    public interface OnNewMessageCallback {
        void onNewMessageCallback();
    }

    public interface DialogClick {
        void chatClick();
    }

    public interface DialogDelete {
        void chatDelete();
    }


    public DialogsRecyclerViewAdapter setNewMessageCallback(OnNewMessageCallback callback) {
        this.newMessageCallback = callback;
        return this;
    }

    public DialogsRecyclerViewAdapter setDialogDelete(DialogDelete dialogDelete) {
        this.dialogDelete = dialogDelete;
        return this;
    }

    public DialogsRecyclerViewAdapter setDialogClick(DialogClick dialogClick) {
        this.dialogClick = dialogClick;
        return this;
    }

    public void setDialogs(List<DialogModel> dialogs) {
        this.dialogs = dialogs;
    }


    public static class DialogsHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        int position;
        TextView mMessageAuthor,
                mLastMessage,
                mLastMessageDate,
                mNewMessageIndicator,
                mTitle;


        public DialogsHolder(View itemView) {
            super(itemView);
            mLastMessage = (TextView) itemView.findViewById(R.id.chats_item_last_message);
            mLastMessageDate = (TextView) itemView.findViewById(R.id.chats_item_last_message_date);
            mNewMessageIndicator = (TextView) itemView.findViewById(R.id.new_message_indicator);
            mMessageAuthor = (TextView) itemView.findViewById(R.id.chats_item_last_message_author);
            imageView = (CircleImageView) itemView.findViewById(R.id.chats_item_chat_image);
            mTitle = (TextView) itemView.findViewById(R.id.chats_item_name);

        }

        public DialogsHolder setPosition(int position) {
            this.position = position;
            return this;
        }

        public DialogsHolder setNewMessageCallback(OnNewMessageCallback messageCallback) {
            return this;
        }

        public DialogsHolder setDialogDelete(DialogDelete dialogDelete) {
            return this;
        }

        public DialogsHolder setDialogClick(DialogClick dialogClick) {
            return this;
        }

        public void setImageView(CircleImageView imageView) {
            this.imageView = imageView;
        }

        public void setmMessageAuthor(TextView mMessageAuthor) {
            this.mMessageAuthor = mMessageAuthor;
        }

        public void setmLastMessage(TextView mLastMessage) {
            this.mLastMessage = mLastMessage;
        }

        public void setmLastMessageDate(TextView mLastMessageDate) {
            this.mLastMessageDate = mLastMessageDate;
        }

        public void setmNewMessageIndicator(TextView mNewMessageIndicator) {
            this.mNewMessageIndicator = mNewMessageIndicator;
        }
    }
}
