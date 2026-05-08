/*
 * Purpose: RecyclerView adapter for student session history rows.
 * Design: Adapter pattern with ViewHolder for list binding.
 * Outstanding issues: Consider caching tutor names to reduce network calls.
 */
package com.example.seproject.adapters;

import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.models.Session;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    private List<Session> sessionList;
    private com.example.seproject.services.FirestoreService firestoreService;

    public SessionAdapter(List<Session> sessionList, com.example.seproject.services.FirestoreService service) {
        this.sessionList = sessionList;
        this.firestoreService = service;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tutorId, date, status;

        public ViewHolder(View itemView) {
            super(itemView);
            tutorId = itemView.findViewById(R.id.tutorId);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_session, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Session session = sessionList.get(position);

        holder.date.setText("Date: " + session.getDate());
        holder.status.setText("Status: " + session.getStatus());

        firestoreService.getUserById(session.getTutorId(), new com.example.seproject.services.UserCallback() {
            @Override
            public void onSuccess(com.example.seproject.models.User user) {
                holder.tutorId.setText("Tutor: " + user.getName());
            }

            @Override
            public void onFailure(String message) {
                holder.tutorId.setText("Tutor: " + session.getTutorId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }
}