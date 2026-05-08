package com.example.seproject.adapters;

import android.content.Intent;
import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.activities.TutorProfileActivity;
import com.example.seproject.models.TutorProfile;

import java.util.List;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.ViewHolder> {
    private List<TutorProfile> tutorList;

    public TutorAdapter(List<TutorProfile> tutorList) {
        this.tutorList = tutorList;
    }

    public void updateList(List<TutorProfile> newList) {
        tutorList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, subjects, ratingText, location;
        com.google.android.material.chip.Chip ratingChip;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tutorName);
            subjects = itemView.findViewById(R.id.tutorSubjects);
            ratingText = itemView.findViewById(R.id.tutorRatingText);
            ratingChip = itemView.findViewById(R.id.ratingChip);
            location = itemView.findViewById(R.id.tutorLocation);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tutor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TutorProfile tutor = tutorList.get(position);

        new com.example.seproject.services.FirestoreService()
                .getUserById(tutor.getUserId(), new com.example.seproject.services.UserCallback() {
                    @Override
                    public void onSuccess(com.example.seproject.models.User user) {
                        holder.name.setText(user.getName());
                    }

                    @Override
                    public void onFailure(String message) {
                        holder.name.setText("Unknown Tutor");
                    }
                });

        holder.subjects.setText(String.join(", ", tutor.getSubjects()));

        if (tutor.getLocation() != null && !tutor.getLocation().isEmpty()) {
            holder.location.setText(tutor.getLocation());
            holder.location.setVisibility(View.VISIBLE);
        } else {
            holder.location.setVisibility(View.GONE);
        }

        if (tutor.getReviewCount() > 0) {
            holder.ratingText.setText(String.format(java.util.Locale.US, "%.1f (%d reviews)", tutor.getAverageRating(), tutor.getReviewCount()));
            holder.ratingChip.setText(tutor.getAverageRating() >= 4.5 ? "Top Rated" : "Rated Tutor");
        } else {
            holder.ratingText.setText("No reviews yet");
            holder.ratingChip.setText("New Tutor");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TutorProfileActivity.class);
            intent.putExtra("tutorId", tutor.getUserId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }
}