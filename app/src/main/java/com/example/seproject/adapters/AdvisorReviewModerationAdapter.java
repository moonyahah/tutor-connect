package com.example.seproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.models.AdvisorReviewItem;

import java.util.List;

public class AdvisorReviewModerationAdapter extends RecyclerView.Adapter<AdvisorReviewModerationAdapter.ViewHolder> {

    public interface OnFlagClickListener {
        void onFlagClick(AdvisorReviewItem item, int position);
    }

    private final List<AdvisorReviewItem> items;
    private final OnFlagClickListener flagClickListener;

    public AdvisorReviewModerationAdapter(List<AdvisorReviewItem> items, OnFlagClickListener flagClickListener) {
        this.items = items;
        this.flagClickListener = flagClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tutorName;
        TextView studentName;
        TextView rating;
        TextView comment;
        Button flagButton;

        public ViewHolder(View itemView) {
            super(itemView);
            tutorName = itemView.findViewById(R.id.tutorName);
            studentName = itemView.findViewById(R.id.studentName);
            rating = itemView.findViewById(R.id.rating);
            comment = itemView.findViewById(R.id.comment);
            flagButton = itemView.findViewById(R.id.flagButton);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_advisor_review_moderation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AdvisorReviewItem item = items.get(position);
        holder.tutorName.setText("Tutor: " + item.getTutorName());
        holder.studentName.setText("Student: " + item.getStudentName());
        holder.rating.setText("Rating: " + item.getRating() + "★");
        holder.comment.setText(item.getComment());

        if (item.isFlagged()) {
            holder.flagButton.setText("Flagged");
            holder.flagButton.setEnabled(false);
        } else {
            holder.flagButton.setText("Flag Review");
            holder.flagButton.setEnabled(true);
            holder.flagButton.setOnClickListener(v -> {
                int pos = holder.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    flagClickListener.onFlagClick(items.get(pos), pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
