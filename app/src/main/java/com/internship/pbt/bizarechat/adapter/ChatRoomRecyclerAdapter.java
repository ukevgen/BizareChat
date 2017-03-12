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
import com.internship.pbt.bizarechat.data.datamodel.MessageModel;
import com.internship.pbt.bizarechat.domain.model.chatroom.MessageState;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.util.Converter;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<ChatRoomRecyclerAdapter.MessageHolder> {
    private long currentUserId = CurrentUser.getInstance().getCurrentUserId();
    private List<MessageModel> messageList;
    private Map<Long, Bitmap> occupantsPhotos;
    private Map<Long, String> userNames;
    private int self = Integer.MAX_VALUE;
    private Context context;
    private int position;

    public ChatRoomRecyclerAdapter(List<MessageModel> messageList,
                                   Map<Long, Bitmap> occupantsPhotos,
                                   Map<Long, String> userNames) {
        this.messageList = messageList;
        this.occupantsPhotos = occupantsPhotos;
        this.userNames = userNames;
    }

    public void setMessageList(List<MessageModel> messageList) {
        this.messageList = messageList;
    }

    public String getPreviousMesageDay(int position) {
        long date = messageList.get(position).getDateSent();
        return Converter.getLastMessageDay(date);
    }

    public void setOccupantsPhotos(Map<Long, Bitmap> occupantsPhotos) {
        this.occupantsPhotos = occupantsPhotos;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == self) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_outgoing, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_incoming, parent, false);
        }
        return new MessageHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel messageModel = messageList.get(position);
        if (messageModel.getSenderId() == currentUserId) {
            return self;
        }
        return position;
    }


    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        MessageModel message = messageList.get(position);
        this.position = position;
        Bitmap photo = occupantsPhotos.get(message.getSenderId().longValue());
        holder.userName.setText(userNames.get(message.getSenderId().longValue()));
        holder.messageText.setText(message.getMessage());
        holder.time.setText(Converter.longToTime(message.getDateSent() * 1000));
        if (message.getSenderId().longValue() == currentUserId) {
            holder.userName.setText(context.getString(R.string.me));
            switch (message.getRead()) {
                case MessageState.DELIVERED:
                    holder.deliveryStatus.setImageDrawable(
                            context.getResources().getDrawable(R.drawable.single_check_mark));
                    break;
                case MessageState.READ:
                    holder.deliveryStatus.setImageDrawable(
                            context.getResources().getDrawable(R.drawable.double_check_mark));
                    break;
                case MessageState.DEFAULT:
                    holder.deliveryStatus.setVisibility(View.INVISIBLE);
                    break;
            }
        }
        if (photo != null)
            holder.userPhoto.setImageBitmap(occupantsPhotos.get(message.getSenderId().longValue()));
        else
            holder.userPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));

        if (position > 0) {
            setTimeTextVisibility(message.getDateSent(), messageList.get(position - 1).getDateSent(),
                    holder.timeText);
        } else {
            setTimeTextVisibility(message.getDateSent(), 0, holder.timeText);
        }

    }

    private void setTimeTextVisibility(long currentTime, long previousTime, TextView timeText
    ) {
        if (previousTime == 0) {
            timeText.setVisibility(View.VISIBLE);
            timeText.setText(Converter.getLastMessageDay(currentTime));
        } else {
            String cal1 = Converter.getLastMessageDay(currentTime);
            String cal2 = Converter.getLastMessageDay(previousTime);
            boolean sameDay = cal1.equals(cal2);

            if (sameDay) {
                timeText.setVisibility(View.GONE);
                timeText.setText("");
            } else {
                timeText.setVisibility(View.VISIBLE);
                timeText.setText(Converter.getPartOfTheWeek(currentTime));
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        private CircleImageView userPhoto;
        private TextView messageText;
        private TextView userName;
        private TextView time;
        private TextView timeText;
        private ImageView deliveryStatus;

        MessageHolder(View view) {
            super(view);
            timeText = (TextView) view.findViewById(R.id.timeText);
            userPhoto = (CircleImageView) view.findViewById(R.id.message_user_photo);
            messageText = (TextView) view.findViewById(R.id.message_text);
            userName = (TextView) view.findViewById(R.id.message_user_name);
            time = (TextView) view.findViewById(R.id.message_time);
            deliveryStatus = (ImageView) view.findViewById(R.id.message_status);
        }

        public TextView getTime() {
            return time;
        }
    }
}
