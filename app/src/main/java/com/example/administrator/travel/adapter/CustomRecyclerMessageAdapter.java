package com.example.administrator.travel.adapter;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.chat.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomRecyclerMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Message> listMessages = new ArrayList<>();

    String AuthID;
    MediaPlayer mediaPlayer;
    int mpLength;//miliseconds
    public CustomRecyclerMessageAdapter(List<Message> list,String mAuthID){
        this.listMessages = list;
        this.AuthID = mAuthID;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case 1://me
                View viewMyChat = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_send,parent,false);
                viewHolder = new MyChatViewHolder(viewMyChat);
                break;
            case 2://other
                View viewOtherChat = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received,parent,false);
                viewHolder = new OtherChatViewHolder(viewOtherChat);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if((listMessages.get(position).getUsersID()).equals(AuthID))
            setupMyChatViewHoler((MyChatViewHolder) holder, position);
        else
            setupOtherChatViewHolder((OtherChatViewHolder) holder, position);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupMyChatViewHoler(final MyChatViewHolder myChatViewHolder, int position){

        final Message chat = listMessages.get(position);

        if (android.webkit.URLUtil.isValidUrl(chat.getMessage()) && chat.getType().trim().equals("image")){
            myChatViewHolder.imgImageSend.setVisibility(View.VISIBLE);
            myChatViewHolder.tvTimeSendImage.setVisibility(View.VISIBLE);

            Picasso.with(myChatViewHolder.imgImageSend.getContext())
                    .load(chat.getMessage())
                    .into(myChatViewHolder.imgImageSend);
            myChatViewHolder.tvTimeSendImage.setText((DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT))
            .format(new Date(chat.getTimestamp())).toString());//day format && time format
        }
        else if (android.webkit.URLUtil.isValidUrl(chat.getMessage()) && chat.getType().trim().equals("audio")){
            myChatViewHolder.tvTimeSendRecord.setVisibility(View.VISIBLE);
            myChatViewHolder.tvTotalRecord.setVisibility(View.VISIBLE);
            myChatViewHolder.ibtnPlayRecord.setVisibility(View.VISIBLE);

            myChatViewHolder.tvTimeSendRecord.setText((DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT))
            .format(new Date(chat.getTimestamp())).toString());

            myChatViewHolder.ibtnPlayRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mediaPlayer.setDataSource(chat.getMessage());
                        mediaPlayer.prepare();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    mpLength = mediaPlayer.getDuration();

                    if(mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                    else
                        mediaPlayer.start();

                    changeTimePlayingRecord(myChatViewHolder);
//                    144 ing
                }
            });
        }else {
            myChatViewHolder.tvMessage.setVisibility(View.VISIBLE);
            myChatViewHolder.tvTimeSendMessage.setVisibility(View.VISIBLE);

            myChatViewHolder.tvMessage.setText(chat.getMessage());
            myChatViewHolder.tvTimeSendMessage.setText((DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT))
                    .format(new Date(chat.getTimestamp())).toString());
        }
    }

    Handler handler = new Handler();
    void changeTimePlayingRecord(final MyChatViewHolder myChatViewHolder){
        myChatViewHolder.tvTotalRecord.setText((new SimpleDateFormat("mm:ss")).format(new Date(mpLength)));

        if(mediaPlayer.isPlaying()){
            Runnable setupTime = new Runnable() {
                @Override
                public void run() {
                    changeTimePlayingRecord(myChatViewHolder);
                }
            };
            handler.postDelayed(setupTime,1000);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupOtherChatViewHolder(final OtherChatViewHolder otherChatViewHolder, int position){
        final Message chat = listMessages.get(position);

        FirebaseDatabase.getInstance().getReference().child("users/"+chat.getUsersID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                otherChatViewHolder.tvNameFriend.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (android.webkit.URLUtil.isValidUrl(chat.getMessage()) && chat.getType().trim().equals("image")){
            otherChatViewHolder.imgImageRe.setVisibility(View.VISIBLE);
            otherChatViewHolder.tvTimeReImage.setVisibility(View.VISIBLE);

            Picasso.with(otherChatViewHolder.imgImageRe.getContext())
                    .load(chat.getMessage())
                    .into(otherChatViewHolder.imgImageRe);
            otherChatViewHolder.tvTimeReImage.setText((DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT))
                    .format(new Date(chat.getTimestamp())).toString());//day format && time format
        }
        else if (android.webkit.URLUtil.isValidUrl(chat.getMessage()) && chat.getType().trim().equals("audio")){
            otherChatViewHolder.tvTimeReRecord.setVisibility(View.VISIBLE);
            otherChatViewHolder.tvTotalRecord.setVisibility(View.VISIBLE);
            otherChatViewHolder.ibtnPlayReRecord.setVisibility(View.VISIBLE);

            otherChatViewHolder.tvTimeReRecord.setText((DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT))
                    .format(new Date(chat.getTimestamp())).toString());

            otherChatViewHolder.ibtnPlayReRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mediaPlayer.setDataSource(chat.getMessage());
                        mediaPlayer.prepare();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    mpLength = mediaPlayer.getDuration();

                    if(mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                    else
                        mediaPlayer.start();

                    changeTimePlayingRecord(otherChatViewHolder);
//                    144 ing
                }
            });
        }else {
            otherChatViewHolder.tvMessage.setVisibility(View.VISIBLE);
            otherChatViewHolder.tvTimeMessage.setVisibility(View.VISIBLE);

            otherChatViewHolder.tvMessage.setText(chat.getMessage());
            otherChatViewHolder.tvTimeMessage.setText((DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT))
                    .format(new Date(chat.getTimestamp())).toString());
        }
    }
    void changeTimePlayingRecord(final OtherChatViewHolder otherChatViewHolder){
        otherChatViewHolder.tvTotalRecord.setText((new SimpleDateFormat("mm:ss")).format(new Date(mpLength)));

        if(mediaPlayer.isPlaying()){
            Runnable setupTime = new Runnable() {
                @Override
                public void run() {
                    changeTimePlayingRecord(otherChatViewHolder);
                }
            };
            handler.postDelayed(setupTime,1000);
        }
    }

    public void add(Message chat){
        listMessages.add(chat);
        notifyItemInserted(listMessages.size() - 1);
    }

    @Override
    public int getItemCount() {
        if(listMessages != null)
            return listMessages.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if((listMessages.get(position).getUsersID()).equals(AuthID))
            return 1;//me
        else
            return 2;//other
    }

    private static class MyChatViewHolder extends RecyclerView.ViewHolder{

        TextView tvMessage,tvTimeSendMessage,tvTimeSendImage,tvTotalRecord,tvTimeSendRecord;
        ImageView imgImageSend;
        ImageButton ibtnPlayRecord;
        public MyChatViewHolder(View itemView) {
            super(itemView);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_itemmessage_message_send);
            tvTimeSendMessage = (TextView) itemView.findViewById(R.id.tv_itemmessage_timesend_message);
            tvTimeSendImage = (TextView) itemView.findViewById(R.id.tv_itemmessage_timesend_image);
            tvTotalRecord = (TextView) itemView.findViewById(R.id.tv_itemmessage_totaltime_record);
            tvTimeSendRecord = (TextView) itemView.findViewById(R.id.tv_itemmessage_timesend_record);
            imgImageSend = (ImageView) itemView.findViewById(R.id.img_itemmessage_image_send);
            ibtnPlayRecord = (ImageButton) itemView.findViewById(R.id.ibtn_itemmessage_play_record);
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder{
     TextView tvNameFriend,tvMessage, tvTimeMessage,tvTimeReImage,tvTotalRecord,tvTimeReRecord;
     ImageView imgImageRe;
     ImageButton ibtnPlayReRecord;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
            tvNameFriend = (TextView) itemView.findViewById(R.id.tv_itemmessage_re_name);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_itemmessage_re_message);
            tvTimeMessage = (TextView) itemView.findViewById(R.id.tv_itemmessage_re_time_message);
            tvTimeReImage = (TextView) itemView.findViewById(R.id.tv_itemmessage_re_time_image);
            tvTotalRecord = (TextView) itemView.findViewById(R.id.tv_itemmessage_re_totaltime_record);
            tvTimeReRecord = (TextView) itemView.findViewById(R.id.tv_itemmessage_re_receive_record);
            imgImageRe = (ImageView) itemView.findViewById(R.id.img_itemmessage_re_image);
            ibtnPlayReRecord = (ImageButton) itemView.findViewById(R.id.ibtn_itemmessage_re_play_record);
        }
    }
}
