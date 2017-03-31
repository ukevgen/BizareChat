package com.internship.pbt.bizarechat.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogsRecyclerViewAdapter extends RecyclerSwipeAdapter<DialogsRecyclerViewAdapter.DialogsHolder> {
    OnDialogDeleteCallback onDialogDeleteCallback;
    OnDialogClickCallback onDialogClickCallback;
    private long currentUserId = CurrentUser.getInstance().getCurrentUserId();
    private Context context;
    private List<DialogModel> dialogs;
    private Map<String, Bitmap> dialogPhotos;
    private Map<Long, String> userNames;

    public DialogsRecyclerViewAdapter(List<DialogModel> dialogs, Map<String, Bitmap> dialogPhotos, Map<Long, String> userNames) {
        this.dialogs = dialogs;
        this.dialogPhotos = dialogPhotos;
        this.userNames = userNames;
        mItemManger.setMode(Attributes.Mode.Single);
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
        if (dialog.getLastMessageUserId() == currentUserId) {
            holder.mMessageAuthor.setText(R.string.me);
        } else {
            String userName = userNames.get(dialog.getLastMessageUserId().longValue());
            if(userName != null)
                holder.mMessageAuthor.setText(userNames.get(dialog.getLastMessageUserId().longValue()));
        }
        holder.mTitle.setText(dialog.getName());
        if (dialog.getUnreadMessagesCount() != null && dialog.getUnreadMessagesCount() != 0) {
            holder.mLastMessageDate.setTextColor(context.getResources().getColor(R.color.chats_item_last_message_date_new));
            holder.mTitle.setTextColor(context.getResources().getColor(R.color.chats_item_name_new));
            holder.mNewMessageIndicator.setVisibility(View.VISIBLE);
            holder.mNewMessageIndicator.setText(String.format("+%d", dialog.getUnreadMessagesCount()));
            ViewCompat.setZ(holder.mNewMessageIndicator, 1000000);
        } else {
            holder.mLastMessageDate.setTextColor(context.getResources().getColor(R.color.chats_item_last_message_date));
            holder.mTitle.setTextColor(context.getResources().getColor(R.color.chats_item_name));
            holder.mNewMessageIndicator.setVisibility(View.GONE);
        }

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        Bitmap photo = dialogPhotos.get(dialog.getDialogId());
        if (photo != null) {
            holder.imageView.setImageBitmap(photo);
        } else {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));
        }

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

    public DialogsRecyclerViewAdapter setOnDialogDeleteCallback(OnDialogDeleteCallback onDialogDeleteCallback) {
        this.onDialogDeleteCallback = onDialogDeleteCallback;
        return this;
    }

    public DialogsRecyclerViewAdapter setOnDialogClickCallback(OnDialogClickCallback onDialogClickCallback) {
        this.onDialogClickCallback = onDialogClickCallback;
        return this;
    }

    private Context getContext() {
        return context;
    }

    public DialogsRecyclerViewAdapter setContext(Context context) {
        this.context = context;
        return this;
    }

    public interface OnDialogClickCallback {
        void onDialogClick(int position);
    }

    public interface OnDialogDeleteCallback {
        void onDialogDelete(int position);
    }

    public static class DialogsHolder extends RecyclerView.ViewHolder {
        public int position;
        SwipeLayout swipeLayout;
        DialogsRecyclerViewAdapter adapter;
        Button deleteButton;
        RelativeLayout surfaceLayout;
        CircleImageView imageView;
        TextView mMessageAuthor,
                mLastMessage,
                mLastMessageDate,
                mNewMessageIndicator,
                mTitle;

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
            surfaceLayout = (RelativeLayout) itemView.findViewById(R.id.chats_item_surface_view);
            surfaceLayout.setOnClickListener(v -> {
                adapter.onDialogClickCallback.onDialogClick(getAdapterPosition());
            });
        }

        public DialogsHolder setPosition(int position) {
            this.position = position;
            return this;
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
}