package com.eslamwael74.inq.samplechatapp;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.eslamwael74.inq.samplechatapp.Model.Chat;
import com.eslamwael74.inq.samplechatapp.Utils.UtilitiesFunctions;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;


/**
 * Created by eslamwael74 on 2/13/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    ArrayList<Chat> chats;
    Context context;
    private static final String TAG = "ChatAdapter";
    FragmentActivity fragmentActivity;


    public ChatAdapter(ArrayList<Chat> chats, Context context, FragmentActivity fragmentActivity) {
        this.chats = chats;
        this.fragmentActivity = fragmentActivity;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.send_message_item, parent, false), 1);
        } else {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.receive_message_item, parent, false), 0);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Chat chat = chats.get(position);

//        Log.e(TAG, "timeStamp: " + chat.getCreatedTime());

        String date = UtilitiesFunctions.timeStampToDate(Long.parseLong(chat.getCreatedTime()));
//        Log.e(TAG, "timeStampToDate: " + date);
        String time = UtilitiesFunctions.timeStampToTime(Long.parseLong(chat.getCreatedTime()));
//        Log.e(TAG, "timeStampToTime: " + time);


        String msg = chats.get(position).getMessage();
        String messageUnescape = StringEscapeUtils.unescapeJava(msg);

        if (chat.getType() == 1) {
            holder.tvMessageSent.setText(messageUnescape);
            holder.tvCreatedDateSender.setText(date + " - " + time);

            if (chat.getTypeOfSend() == 0) {
                holder.tvSent.setText(fragmentActivity.getString(R.string.sending));
                holder.tvSent.setTextColor(fragmentActivity.getColor(R.color.gray));
                holder.resend.setVisibility(View.GONE);

            }

            if (chat.getTypeOfSend() == 1) {
                holder.tvSent.setText(fragmentActivity.getString(R.string.sent));
                holder.tvSent.setTextColor(fragmentActivity.getColor(R.color.green));
                holder.resend.setVisibility(View.GONE);

            }
            if (chat.getTypeOfSend() == 2) {
                holder.tvSent.setText(fragmentActivity.getString(R.string.failed));
                holder.tvSent.setTextColor(fragmentActivity.getColor(R.color.red));
                holder.resend.setVisibility(View.VISIBLE);
                holder.resend.setEnabled(true);
                holder.resend.setOnClickListener(v -> {
                    holder.tvSent.setText(fragmentActivity.getString(R.string.sending));
                    holder.tvSent.setTextColor(fragmentActivity.getColor(R.color.yellow_rcv_message));
//                    presenter.sendNewMessage(chats.get(position).getMessage(), position);
                    holder.resend.setEnabled(false);
                });
            }


        } else if (chat.getType() == 0) {
            holder.tvMessageReceiver.setText(messageUnescape);
            holder.tvCreatedDateReceiver.setText(date + " - " + time);
        }

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chats.get(position).getType();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCreatedDateSender,
                tvCreatedDateReceiver,
                tvMessageSent,
                tvMessageReceiver,
                tvSent;
        ImageView resend;

        public MyViewHolder(View itemView, int type) {
            super(itemView);

            if (type == 1) {
                tvMessageSent = itemView.findViewById(R.id.message_text_view);
                tvCreatedDateSender = itemView.findViewById(R.id.timestamp_text_view);
                tvSent = itemView.findViewById(R.id.sent);
                resend = itemView.findViewById(R.id.resend);
            } else if (type == 0) {
                tvMessageReceiver = itemView.findViewById(R.id.message_text_view);
                tvCreatedDateReceiver = itemView.findViewById(R.id.timestamp_text_view);
            }
        }
    }


}
