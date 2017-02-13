package com.internship.pbt.bizarechat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.domain.model.chatroom.ChatMessageModel;
import com.internship.pbt.bizarechat.domain.model.chatroom.MessageState;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<ChatRoomRecyclerAdapter.MessageHolder> {
    private long currentUserId = CurrentUser.getInstance().getCurrentUserId();
    private List<ChatMessageModel> messageList;
    private Map<Long, Bitmap> occupantsPhotos;
    private int self = Integer.MAX_VALUE;
    private Context context;

    public ChatRoomRecyclerAdapter(Context context,
                                   List<ChatMessageModel> messageList,
                                   Map<Long, Bitmap> occupantsPhotos){
        this.context = context;
        this.messageList = messageList;
        this.occupantsPhotos = occupantsPhotos;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == self){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_outgoing, parent, false);
        } else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_incoming, parent, false);
        }
        return new MessageHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageModel chatMessageModel = messageList.get(position);
        if(chatMessageModel.getUserId() == currentUserId){
            return self;
        }
        return position;
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        ChatMessageModel message = messageList.get(position);
        holder.userName.setText(message.getUserName());
        holder.messageText.setText(message.getText());
        holder.time.setText(message.getTime());
        if(message.getUserId() == currentUserId){
            holder.userName.setText(context.getString(R.string.me));
            switch(message.getState()){
                case MessageState.SENT:
                    holder.deliveryStatus.setImageDrawable(
                            context.getResources().getDrawable(R.drawable.single_check_mark));
                    break;
                case MessageState.DELIVERED:
                    holder.deliveryStatus.setImageDrawable(
                            context.getResources().getDrawable(R.drawable.double_check_mark));
                    break;
                case MessageState.NOT_SENT:
                    holder.deliveryStatus.setVisibility(View.INVISIBLE);
                    break;
            }
        }
        holder.userPhoto.setImageBitmap(occupantsPhotos.get(message.getUserId()));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder{
        private CircleImageView userPhoto;
        private TextView messageText;
        private TextView userName;
        private TextView time;
        private ImageView deliveryStatus;

        MessageHolder(View view){
            super(view);
            userPhoto = (CircleImageView)view.findViewById(R.id.message_user_photo);
            messageText = (TextView)view.findViewById(R.id.message_text);
            userName = (TextView)view.findViewById(R.id.message_user_name);
            time = (TextView)view.findViewById(R.id.message_time);
            deliveryStatus = (ImageView)view.findViewById(R.id.message_status);
        }
    }
}
