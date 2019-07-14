package com.example.administrator.travel.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Henry on 12/17/2018.
 */

public class FriendContactAdapter extends RecyclerView.Adapter<FriendContactAdapter.ViewHolder> implements Listener.OnGetUserInforFinishedListener, Listener.OnGetUserAvatarFinishedListener {

    private FriendContactAdapter.OnItemFriendClickListener itemFriendClickListener;
    private List<String> friendIdList;
    private UserInteractor userInteractor;
    private boolean[] loadFlags;
    private HashMap<String, String> userNameMap;
    private HashMap<String, Bitmap> avatarMap;
    private RecyclerView parent;

    public FriendContactAdapter(List<String> friendIdList) {
        this.friendIdList = friendIdList;
        userInteractor = new UserInteractorImpl();
        if (friendIdList.size() > 0)
            loadFlags = new boolean[friendIdList.size()];
        userNameMap = new HashMap<>();
        avatarMap = new HashMap<>();
    }

    @NonNull
    @Override
    public FriendContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    public void onBindViewHolder(FriendContactAdapter.ViewHolder holder, final int position) {
        String userId = friendIdList.get(position);
        if (!loadFlags[position]) {
            userInteractor.getUserInfor(userId, this);
            loadFlags[position]=true;
        } else {
            if (userNameMap.get(userId) != null) {
                holder.txtUserName.setText(userNameMap.get(userId));
            }
            if (avatarMap.get(userId) != null) {
                holder.imgvAvatar.setImageBitmap(avatarMap.get(userId));
            }
        }

    }

    @Override
    public int getItemCount() {
        return friendIdList.size();
    }

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        if(userNameMap!=null){
            userNameMap.put(user.id,user.name);
            userInteractor.getUserAvatar(user.id,user.urlAvatar,this);
            parent.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        if(avatarMap !=null){
            avatarMap.put(userId, avatar);
            parent.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }


    public interface OnItemFriendClickListener {
        void onItemFriendClick(String friendId);
    }


    public void setOnItemClickListener(FriendContactAdapter.OnItemFriendClickListener itemFriendClickListener) {
        this.itemFriendClickListener = itemFriendClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName;
        de.hdodenhof.circleimageview.CircleImageView imgvAvatar;

        public ViewHolder(View view) {
            super(view);
            txtUserName = view.findViewById(R.id.tv_item_searchfriend_name_user);
            imgvAvatar = view.findViewById(R.id.img_item_searchfriend_avartar);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemFriendClickListener != null)
                        itemFriendClickListener.onItemFriendClick(friendIdList.get(getAdapterPosition()));
                }
            });
        }
    }
}
