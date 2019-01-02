package com.example.administrator.travel.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.chat.Chats;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Chats> listLastChats = new ArrayList<>();
    List<String> listKeyFriend = new ArrayList<>();
    public CustomRecyclerChatAdapter(List<Chats> listChat,List<String> listFriend){
        this.listLastChats = listChat;
        this.listKeyFriend =listFriend;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_group_friend,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Chats chat = listLastChats.get(position);

        final ChatViewHolder chatViewHolder = (ChatViewHolder) holder;

        FirebaseDatabase.getInstance().getReference().child("users/"+listKeyFriend.get(position))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        chatViewHolder.tvNameFriend.setText(dataSnapshot.child("name").getValue(String.class));
                        Picasso.with(chatViewHolder.imgAvatarFriend.getContext())
                                .load(dataSnapshot.child("urlAvatar").getValue(String.class).toString())
                                .into(chatViewHolder.imgAvatarFriend);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        chatViewHolder.tvLastMessage.setText(chat.getLastMessage());
        chatViewHolder.tvLastTime.setText(((DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT))
                .format(new java.util.Date(chat.getTimestamp()))).toString());

        chatViewHolder.item_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null)
                    onItemClickListener.onItemClick(chatViewHolder.tvNameFriend.getText().toString(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listLastChats != null)
            return listKeyFriend.size();
        return 0;
    }

    public void add(Chats chat){
        listLastChats.add(chat);
        notifyItemInserted(listLastChats.size() - 1);
    }

    private static class ChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameFriend, tvLastMessage, tvLastTime;
        de.hdodenhof.circleimageview.CircleImageView imgAvatarFriend;
        ConstraintLayout item_friend;
        public ChatViewHolder(View view){
            super(view);
            tvNameFriend = (TextView) view.findViewById(R.id.tv_chat_group_item_name_friend);
            tvLastMessage = (TextView) view.findViewById(R.id.tv_chat_group_item_last_message);
            tvLastTime = (TextView) view.findViewById(R.id.tv_chat_group_item_time);
            imgAvatarFriend = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.img_chat_group_item_avatar);
            item_friend = (ConstraintLayout) view.findViewById(R.id.item_chat_group_friend);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String userName, int position);
    }

    private CustomRecyclerChatAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CustomRecyclerChatAdapter.OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
}
