package com.example.seproject.adapters;

import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.models.Session;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    public interface OnSessionActionListener {
        void onCancelClicked(Session session, int position);
        void onReviewClicked(Session session, int position);
    }

    private List<Session> sessionList;
    private com.example.seproject.services.FirestoreService firestoreService;
    private OnSessionActionListener actionListener;
    private boolean isTutorView;

    public SessionAdapter(List<Session> sessionList, com.example.seproject.services.FirestoreService service) {
        this(sessionList, service, null, false);
    }

    public SessionAdapter(List<Session> sessionList,
                          com.example.seproject.services.FirestoreService service,
                          OnSessionActionListener actionListener) {
        this(sessionList, service, actionListener, false);
    }

    public SessionAdapter(List<Session> sessionList,
                          com.example.seproject.services.FirestoreService service,
                          OnSessionActionListener actionListener,
                          boolean isTutorView) {
        this.sessionList = sessionList;
        this.firestoreService = service;
        this.actionListener = actionListener;
        this.isTutorView = isTutorView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView personLabel, date, status;
        Button cancelButton, reviewButton;

        public ViewHolder(View itemView) {
            super(itemView);
            personLabel = itemView.findViewById(R.id.tutorId); // Reusing the same ID for simplicity
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            cancelButton = itemView.findViewById(R.id.cancelSessionButton);
            reviewButton = itemView.findViewById(R.id.reviewSessionButton);
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
        holder.status.setText("Status: " + session.getStatus().toUpperCase());

        boolean canCancel = session.getStatus().equalsIgnoreCase("accepted")
                || session.getStatus().equalsIgnoreCase("rescheduled");

        if (canCancel && actionListener != null) {
            holder.cancelButton.setVisibility(View.VISIBLE);
            holder.cancelButton.setOnClickListener(v -> {
                int pos = holder.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    actionListener.onCancelClicked(sessionList.get(pos), pos);
                }
            });
        } else {
            holder.cancelButton.setVisibility(View.GONE);
            holder.cancelButton.setOnClickListener(null);
        }

        boolean canReview = actionListener != null
                && session.getRating() == 0
                && !session.getStatus().equalsIgnoreCase("cancelled")
                && !session.getStatus().equalsIgnoreCase("declined")
                && !isTutorView; // Tutors don't rate students in this version

        if (canReview) {
            holder.reviewButton.setVisibility(View.VISIBLE);
            holder.reviewButton.setOnClickListener(v -> {
                int pos = holder.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    actionListener.onReviewClicked(sessionList.get(pos), pos);
                }
            });
        } else {
            holder.reviewButton.setVisibility(View.GONE);
            holder.reviewButton.setOnClickListener(null);
        }

        String targetId = isTutorView ? session.getStudentId() : session.getTutorId();
        String labelPrefix = isTutorView ? "Student: " : "Tutor: ";

        firestoreService.getUserById(targetId, new com.example.seproject.services.UserCallback() {
            @Override
            public void onSuccess(com.example.seproject.models.User user) {
                holder.personLabel.setText(labelPrefix + user.getName());
            }

            @Override
            public void onFailure(String message) {
                holder.personLabel.setText(labelPrefix + targetId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }
}