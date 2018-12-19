package com.example.administrator.travel.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henry on 12/17/2018.
 */

public class CustomRecyclerContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<String> listKeyFriends = new ArrayList<>();

    public CustomRecyclerContactAdapter(List<String> listKeyFriends){
        this.listKeyFriends = listKeyFriends;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        String keyUserID = listKeyFriends.get(position);

        final ViewHolder holder1 = (ViewHolder) holder;

        FirebaseDatabase.getInstance().getReference().child("users/"+keyUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder1.tvName.setText(dataSnapshot.child("name").getValue(String.class));

                Picasso.with(holder1.imgAvatar.getContext())
                        .load(dataSnapshot.child("urlAvatar").getValue(String.class).toString())
                        .into(holder1.imgAvatar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder1.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null)
                    onItemClickListener.onItemClick(holder1.tvName.getText().toString(),position);
            }
        });
    }//end onBindViewHolder

    @Override
    public int getItemCount() {
        if(listKeyFriends != null)
            return listKeyFriends.size();
        return 0;
    }


    public interface OnItemClickListener{
        public void onItemClick(String username, int index);
    }

    private CustomRecyclerContactAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CustomRecyclerContactAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        de.hdodenhof.circleimageview.CircleImageView imgAvatar;
        ConstraintLayout item_contact;
        public ViewHolder(View view){
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_item_searchfriend_name_user);
            imgAvatar = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.img_item_searchfriend_avartar);
            item_contact = (ConstraintLayout) view.findViewById(R.id.constrain_item_friend);
        }
    }
}
