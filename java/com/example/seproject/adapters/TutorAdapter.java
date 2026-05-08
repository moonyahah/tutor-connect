/*
 * Purpose: RecyclerView adapter for displaying tutor cards on the student dashboard.
 * Design: Adapter pattern with ViewHolder for efficient list rendering.
 * Outstanding issues: Cache user names to avoid repeated Firestore lookups.
 */
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
        TextView name, subjects;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tutorName);
            subjects = itemView.findViewById(R.id.tutorSubjects);
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

        holder.subjects.setText(tutor.getSubjects().toString());

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