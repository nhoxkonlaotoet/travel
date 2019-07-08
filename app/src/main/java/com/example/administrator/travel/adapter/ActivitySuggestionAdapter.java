package com.example.administrator.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.ActivitySuggestion;

import java.util.List;

public class ActivitySuggestionAdapter extends RecyclerView.Adapter<ActivitySuggestionAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ActivitySuggestionAdapter.ActivtySuggestionListener mClickListener;
    private List<ActivitySuggestion> suggestList;
    private int clickPost=-1;
    private RecyclerView parent;
    public ActivitySuggestionAdapter(Context context, List<ActivitySuggestion> suggestList) {
        if (context == null)
            return;
        this.mInflater = LayoutInflater.from(context);
        this.suggestList = suggestList;

    }

    @Override
    public ActivitySuggestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_activity_suggest, parent, false);
        return new ActivitySuggestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == clickPost)
            holder.itemView.setEnabled(false);
        else
            holder.itemView.setEnabled(true);
        holder.txtSuggest.setText(suggestList.get(position).display);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }
    @Override
    public int getItemCount() {
        return suggestList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtSuggest;

        public ViewHolder(View itemView) {
            super(itemView);
            txtSuggest = itemView.findViewById(R.id.txtSuggest);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                clickPost = getAdapterPosition();
                parent.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
                mClickListener.onActivtySuggestionClick(view, suggestList.get(getAdapterPosition()));
            }
        }
    }

    public void setClickListener(ActivitySuggestionAdapter.ActivtySuggestionListener activtySuggestionListener) {
        this.mClickListener = activtySuggestionListener;
    }

    public interface ActivtySuggestionListener {
        void onActivtySuggestionClick(View view, ActivitySuggestion activitySuggestion);
    }
}
