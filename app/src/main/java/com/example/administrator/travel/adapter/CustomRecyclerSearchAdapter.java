package com.example.administrator.travel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.models.entities.UserInformation;

import java.util.ArrayList;
import java.util.List;
import com.example.administrator.travel.R;
import com.squareup.picasso.Picasso;



public class CustomRecyclerSearchAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private List<UserInformation> listUser = new ArrayList<>();
    private int Sum = 0;
    private CustomRecyclerSearchAdapter.OnItemClickedListener onItemClickedListener;

    public CustomRecyclerSearchAdapter(List<UserInformation> listUser){
        this.listUser = listUser;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_friend, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position){

        UserInformation userInformation = listUser.get(position);
        final  UsersViewHolder holder1 = (UsersViewHolder) holder;
        holder1.tvUserName.setText(userInformation.getName());
        Sum++;
        Picasso.with(holder1.itemView.getContext())
                .load(userInformation.getUrlAvatar().toString())
                .into(holder1.imgAvatar);

        holder1.item_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickedListener != null)
                    onItemClickedListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listUser != null )
            return listUser.size();
        return 0;
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserName;
        de.hdodenhof.circleimageview.CircleImageView imgAvatar;
        ConstraintLayout item_user;
        public UsersViewHolder(View view){
            super(view);
            tvUserName = (TextView) view.findViewById(R.id.tv_item_searchfriend_name_user);
            imgAvatar = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.img_item_searchfriend_avartar);
            item_user = (ConstraintLayout) view.findViewById(R.id.constrain_item_friend);
        }
    }

    public interface OnItemClickedListener{
        void onItemClick(int index);
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener =onItemClickedListener;
    }
}
