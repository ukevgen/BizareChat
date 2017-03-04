package com.internship.pbt.bizarechat.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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

        return new DialogsHolder(v, this);
    }

    @Override
    public void onBindViewHolder(DialogsHolder holder, int position) {
        DialogModel dialog = dialogs.get(position);
        holder.setPosition(position);
        holder.mLastMessage.setText(dialog.getLastMessage());
        holder.mLastMessageDate.setText(String.valueOf(dialog.getLastMessageTime()));
        holder.mMessageAuthor.setText(String.valueOf(dialog.getLastMessageUserId()));
        if(dialog.getUnreadMessagesCount() != 0) {
            holder.mNewMessageIndicator.setVisibility(View.VISIBLE);
            holder.mNewMessageIndicator.setText("+" + dialog.getUnreadMessagesCount());
        } else {
            holder.mNewMessageIndicator.setVisibility(View.GONE);
        }
        holder.mTitle.setText(dialog.getName());
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        Bitmap photo = dialogPhotos.get(dialog.getDialogId());
        if (photo != null)
            holder.imageView.setImageBitmap(photo);
        else
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));

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

    public interface OnDialogClickCallback {
        void onDialogClick(int position);
    }

    public interface OnDialogDeleteCallback {
        void onDialogDelete(int position);
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
        DialogsRecyclerViewAdapter adapter;
        Button deleteButton;
        FrameLayout surfaceLayout;
        CircleImageView imageView;
        TextView mMessageAuthor,
                mLastMessage,
                mLastMessageDate,
                mNewMessageIndicator,
                mTitle;
        public int position;

        public DialogsHolder setPosition(int position) {
            this.position = position;
            return this;
        }

        public DialogsHolder(View itemView, DialogsRecyclerViewAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            mLastMessage = (TextView) itemView.findViewById(R.id.chats_item_last_message);
            mLastMessageDate = (TextView) itemView.findViewById(R.id.chats_item_last_message_date);
            mNewMessageIndicator = (TextView) itemView.findViewById(R.id.new_message_indicator);
            mMessageAuthor = (TextView) itemView.findViewById(R.id.chats_item_last_message_author);
            imageView = (CircleImageView) itemView.findViewById(R.id.chats_item_chat_image);
            mTitle = (TextView) itemView.findViewById(R.id.chats_item_name);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.chats_item_swipe_layout);
            deleteButton = (Button) itemView.findViewById(R.id.chats_item_delete_button);
            deleteButton.setOnClickListener(v -> {
                //deleteItem();
                confirm();
            });
            surfaceLayout = (FrameLayout)itemView.findViewById(R.id.chats_item_surface_view);
            surfaceLayout.setOnClickListener(v -> {
                adapter.onDialogClickCallback.onDialogClick(getAdapterPosition());
            });
        }

        private void deleteItem() {
            adapter.onDialogDeleteCallback.onDialogDelete(position);
            adapter.mItemManger.removeShownLayouts(swipeLayout);
            adapter.dialogPhotos.remove(adapter.dialogs.get(position).getDialogId());
            adapter.dialogs.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, adapter.dialogs.size());
            adapter.mItemManger.closeAllItems();
        }

        private void confirm() {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext(),
                    R.style.AlertDialogConfirmStyle);
            builder.setTitle(R.string.are_you_sure);
            builder.setPositiveButton(R.string.delete, (dialog1, whichButton) -> {

            });

            builder.setNegativeButton(R.string.cancel, (dialog12, whichButton) -> {
                dialog12.dismiss();
                adapter.mItemManger.closeAllItems();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            Button delete = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            delete.setOnClickListener(
                    v -> {
                        dialog.dismiss();
                        deleteItem();
                    }
            );
        }
    }

    private Context getContext() {
        return context;
    }
}
