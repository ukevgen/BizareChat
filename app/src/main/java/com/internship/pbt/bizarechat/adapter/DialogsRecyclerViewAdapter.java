package com.internship.pbt.bizarechat.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogsRecyclerViewAdapter extends RecyclerSwipeAdapter<DialogsRecyclerViewAdapter.DialogsHolder> {
    private Context context;
    private List<DialogModel> dialogs;
    private Map<String, Bitmap> dialogPhotos;
    OnNewMessageCallback newMessageCallback;
    OnDialogDeleteCallback onDialogDeleteCallback;
    OnDialogClickCallback onDialogClickCallback;

    public DialogsRecyclerViewAdapter(List<DialogModel> dialogs, Map<String, Bitmap> dialogPhotos) {
        this.dialogs = dialogs;
        this.dialogPhotos = dialogPhotos;
        mItemManger.setMode(Attributes.Mode.Single);
    }

    public DialogsRecyclerViewAdapter setContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public DialogsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialogs_recycler_item, parent, false);

        return new DialogsHolder(v);
    }

    @Override
    public void onBindViewHolder(DialogsHolder holder, int position) {
        DialogModel dialog = dialogs.get(position);
        holder.mLastMessage.setText(dialog.getLastMessage());
        holder.mLastMessageDate.setText(String.valueOf(dialog.getLastMessageDateSent()));
        holder.mMessageAuthor.setText(String.valueOf(dialog.getLastMessageUserId()));
        holder.mNewMessageIndicator.setText("+" + dialog.getUnreadMessagesCount());
        holder.mTitle.setText(dialog.getName());
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        Bitmap photo = dialogPhotos.get(dialog.getDialogId());
        if(photo != null)
            holder.imageView.setImageBitmap(photo);
        else
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));

        holder.deleteButton.setOnClickListener(
                (View view) -> {
                    onDialogDeleteCallback.onDialogDelete(position);
                    mItemManger.removeShownLayouts(holder.swipeLayout);
                    dialogs.remove(position);
                    dialogPhotos.remove(dialog.getDialogId());
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, dialogs.size());
                    mItemManger.closeAllItems();
                });

        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.chats_item_swipe_layout;
    }

    @Override
    public int getItemCount() {
        return dialogs.size();
    }

    public interface OnNewMessageCallback {
        void onNewMessageCallback();
    }

    public interface OnDialogClickCallback {
        void onDialogClick();
    }

    public interface OnDialogDeleteCallback {
        void onDialogDelete(int position);
    }

    public DialogsRecyclerViewAdapter setNewMessageCallback(OnNewMessageCallback callback) {
        this.newMessageCallback = callback;
        return this;
    }

    public DialogsRecyclerViewAdapter setOnDialogDeleteCallback(OnDialogDeleteCallback onDialogDeleteCallback) {
        this.onDialogDeleteCallback = onDialogDeleteCallback;
        return this;
    }

    public DialogsRecyclerViewAdapter setOnDialogClickCallback(OnDialogClickCallback onDialogClickCallback) {
        this.onDialogClickCallback = onDialogClickCallback;
        return this;
    }

    public static class DialogsHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        Button deleteButton;
        CircleImageView imageView;
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
            swipeLayout = (SwipeLayout)itemView.findViewById(R.id.chats_item_swipe_layout);
            deleteButton = (Button)itemView.findViewById(R.id.chats_item_delete_button);
        }
    }
}
