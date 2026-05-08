/*
 * Purpose: RecyclerView adapter for incoming tutor requests with accept/decline actions.
 * Design: Adapter pattern with ViewHolder and action callbacks.
 * Outstanding issues: Surface failures from updateRequestStatus to the UI.
 */
package com.example.seproject.adapters;

import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.models.Request;
import com.example.seproject.services.FirestoreCallback;
import com.example.seproject.services.FirestoreService;
import com.example.seproject.services.UserCallback;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private List<Request> requestList;
    private FirestoreService firestoreService = new FirestoreService();

    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentId, message, status;
        Button acceptBtn, declineBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            studentId = itemView.findViewById(R.id.studentId);
            message = itemView.findViewById(R.id.message);
            status = itemView.findViewById(R.id.status);
            acceptBtn = itemView.findViewById(R.id.acceptButton);
            declineBtn = itemView.findViewById(R.id.declineButton);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Request request = requestList.get(position);

        holder.message.setText(request.getMessage());
        holder.status.setText(request.getStatus());

        // Fetch student name
        firestoreService.getUserById(request.getStudentId(), new UserCallback() {
            @Override
            public void onSuccess(com.example.seproject.models.User user) {
                holder.studentId.setText(user.getName());
            }

            @Override
            public void onFailure(String message) {
                holder.studentId.setText("Student");
            }
        });

        // Reset listeners (important to avoid recycling bugs)
        holder.acceptBtn.setOnClickListener(null);
        holder.declineBtn.setOnClickListener(null);

        // Handle UI based on status
        if (request.getStatus().equals("accepted")) {
            holder.acceptBtn.setVisibility(View.VISIBLE);
            holder.acceptBtn.setText("Accepted");
            holder.acceptBtn.setEnabled(false);

            holder.declineBtn.setVisibility(View.GONE);
        }
        else if (request.getStatus().equals("declined")) {
            holder.declineBtn.setVisibility(View.VISIBLE);
            holder.declineBtn.setText("Rejected");
            holder.declineBtn.setEnabled(false);

            holder.acceptBtn.setVisibility(View.GONE);
        }
        else {
            // pending
            holder.acceptBtn.setVisibility(View.VISIBLE);
            holder.declineBtn.setVisibility(View.VISIBLE);

            holder.acceptBtn.setEnabled(true);
            holder.declineBtn.setEnabled(true);

            holder.acceptBtn.setText("Accept");
            holder.declineBtn.setText("Reject");

            // Accept
            holder.acceptBtn.setOnClickListener(v -> {

                int pos = holder.getBindingAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                Request req = requestList.get(pos);

                firestoreService.updateRequestStatus(
                        req.getRequestId(),
                        "accepted",
                        new FirestoreCallback() {
                            @Override
                            public void onSuccess() {

                                createSession(req);

                                req.setStatus("accepted");
                                notifyItemChanged(pos);
                            }

                            @Override
                            public void onFailure(String message) {}
                        }
                );
            });

            // Decline
            holder.declineBtn.setOnClickListener(v -> {

                int pos = holder.getBindingAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                Request req = requestList.get(pos);

                firestoreService.updateRequestStatus(
                        req.getRequestId(),
                        "declined",
                        new FirestoreCallback() {
                            @Override
                            public void onSuccess() {
                                req.setStatus("declined");
                                notifyItemChanged(pos);
                            }

                            @Override
                            public void onFailure(String message) {}
                        }
                );
            });
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    private void createSession(com.example.seproject.models.Request request) {

        String sessionId = java.util.UUID.randomUUID().toString();

        com.example.seproject.models.Session session =
                new com.example.seproject.models.Session(
                        sessionId,
                        request.getStudentId(),
                        request.getTutorId(),
                        request.getDate(),
                        "accepted"
                );

        com.google.firebase.firestore.FirebaseFirestore.getInstance()
                .collection("sessions")
                .document(sessionId)
                .set(session);
    }
}