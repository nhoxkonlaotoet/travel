package com.example.administrator.travel.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.UserInformation;

import java.util.HashMap;
import java.util.List;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.squareup.picasso.Picasso;


public class CustomRecyclerSearchAdapter extends RecyclerView.Adapter<CustomRecyclerSearchAdapter.ViewHolder> implements Listener.OnGetUserAvatarFinishedListener {
    private LayoutInflater mInflater;
    private OnItemClickedListener onItemClickedListener;
    private List<UserInformation> userInformationList;
    private HashMap<String, Bitmap> avatarMap;
    private boolean[] downloadFlags;
    private RecyclerView parent;

    private UserInteractor userInteractor;

    public CustomRecyclerSearchAdapter(List<UserInformation> userInformationList) {
        this.userInformationList = userInformationList;
        userInteractor = new UserInteractorImpl();
        avatarMap = new HashMap<>();
        downloadFlags = new boolean[userInformationList.size()];
    }

    @NonNull
    @Override
    public CustomRecyclerSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_friend, parent, false);
        return new CustomRecyclerSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerSearchAdapter.ViewHolder holder, final int position) {

        UserInformation userInformation = userInformationList.get(position);
        holder.tvUserName.setText(userInformation.name);
        if (!downloadFlags[position]) {
            userInteractor.getUserAvatar(userInformation.id, userInformation.urlAvatar, this);
            downloadFlags[position] = true;
        } else if (avatarMap.get(userInformation.id) != null) {
//            Picasso.with(holder.itemView.getContext())
//                    .load(userInformation.getUrlAvatar())
//                    .into(holder.imgAvatar);
            holder.imgAvatar.setImageBitmap(avatarMap.get(userInformation.id));
        }
    }

    @Override
    public int getItemCount() {
        if (userInformationList != null)
            return userInformationList.size();
        return 0;
    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        avatarMap.put(userId,avatar);
        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName;
        de.hdodenhof.circleimageview.CircleImageView imgAvatar;
        ConstraintLayout item_user;

        public ViewHolder(View view) {
            super(view);
            tvUserName = view.findViewById(R.id.tv_item_searchfriend_name_user);
            imgAvatar = view.findViewById(R.id.img_item_searchfriend_avartar);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickedListener != null)
                        onItemClickedListener.onItemFriendClick(userInformationList.get(getAdapterPosition()).id);
                }
            });
        }
    }

    public interface OnItemClickedListener {
        void onItemFriendClick(String friendId);
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
