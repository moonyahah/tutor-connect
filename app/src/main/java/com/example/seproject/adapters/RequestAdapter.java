package com.example.seproject.adapters;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.models.Request;
import com.example.seproject.services.FirestoreCallback;
import com.example.seproject.services.FirestoreService;
import com.example.seproject.services.UserCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private List<Request> requestList;
    private FirestoreService firestoreService = new FirestoreService();

    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentId, message, status, dateAndCredits;
        Button acceptBtn, declineBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            studentId = itemView.findViewById(R.id.studentId);
            message = itemView.findViewById(R.id.message);
            status = itemView.findViewById(R.id.status);
            dateAndCredits = itemView.findViewById(R.id.dateAndCredits);
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
    holder.dateAndCredits.setText("Date: " + request.getDate() + "  •  Credits: " + request.getCreditAmount());

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
        if (request.getStatus().equals("accepted") || request.getStatus().equals("rescheduled")) {
            holder.acceptBtn.setVisibility(View.VISIBLE);
            holder.acceptBtn.setText("Reschedule");
            holder.acceptBtn.setEnabled(true);

            holder.declineBtn.setVisibility(View.VISIBLE);
            holder.declineBtn.setText("Cancel Session");
            holder.declineBtn.setEnabled(true);

            holder.acceptBtn.setOnClickListener(v -> showRescheduleDialog(holder, request));
            holder.declineBtn.setOnClickListener(v -> cancelAcceptedRequest(holder, request));
        }
        else if (request.getStatus().equals("cancelled")) {
            holder.declineBtn.setVisibility(View.VISIBLE);
            holder.declineBtn.setText("Cancelled");
            holder.declineBtn.setEnabled(false);

            holder.acceptBtn.setVisibility(View.GONE);
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

                firestoreService.transferCredits(req.getStudentId(), req.getTutorId(), req.getCreditAmount(), new FirestoreCallback() {
                    @Override
                    public void onSuccess() {
                        firestoreService.updateRequestStatus(
                                req.getRequestId(),
                                "accepted",
                                new FirestoreCallback() {
                                    @Override
                                    public void onSuccess() {

                                        createSession(req);

                                        req.setStatus("accepted");
                                        notifyItemChanged(pos);
                                        firestoreService.createNotification(
                                                req.getStudentId(),
                                                "Request Accepted",
                                                "Your session request for " + req.getDate() + " has been accepted.",
                                                "request_accepted"
                                        );
                                        Toast.makeText(holder.itemView.getContext(), "Request accepted and credits transferred", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(String message) {
                                        Toast.makeText(holder.itemView.getContext(), message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(holder.itemView.getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
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
                                firestoreService.createNotification(
                                        req.getStudentId(),
                                        "Request Declined",
                                        "Your session request for " + req.getDate() + " was declined.",
                                        "request_declined"
                                );
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
            request.getRequestId(),
                        request.getStudentId(),
                        request.getTutorId(),
                        request.getDate(),
                        "accepted",
                        request.getCreditAmount()
                );

        com.google.firebase.firestore.FirebaseFirestore.getInstance()
                .collection("sessions")
                .document(sessionId)
                .set(session);
    }

    private void showRescheduleDialog(ViewHolder holder, Request request) {
        Context context = holder.itemView.getContext();
        EditText input = new EditText(context);
        input.setHint("YYYY-MM-DD");
        input.setText(request.getDate());

        new android.app.AlertDialog.Builder(context)
                .setTitle("Reschedule Session")
                .setMessage("Enter a new date")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newDate = input.getText().toString().trim();
                    if (newDate.isEmpty()) {
                        Toast.makeText(context, "Date cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Map<String, Object> requestFields = new HashMap<>();
                    requestFields.put("date", newDate);
                    requestFields.put("status", "rescheduled");

                    firestoreService.updateRequestFields(request.getRequestId(), requestFields, new FirestoreCallback() {
                        @Override
                        public void onSuccess() {
                            firestoreService.updateSessionDateByRequestId(request.getRequestId(), newDate, new FirestoreCallback() {
                                @Override
                                public void onSuccess() {
                                    int pos = holder.getBindingAdapterPosition();
                                    if (pos != RecyclerView.NO_POSITION) {
                                        request.setDate(newDate);
                                        request.setStatus("rescheduled");
                                        notifyItemChanged(pos);
                                        firestoreService.createNotification(
                                                request.getStudentId(),
                                                "Session Rescheduled",
                                                "Tutor proposed a new session date: " + newDate,
                                                "session_rescheduled"
                                        );
                                    }
                                }

                                @Override
                                public void onFailure(String message) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void cancelAcceptedRequest(ViewHolder holder, Request request) {
        Context context = holder.itemView.getContext();

        firestoreService.refundCredits(request.getStudentId(), request.getTutorId(), request.getCreditAmount(), new FirestoreCallback() {
            @Override
            public void onSuccess() {
                Map<String, Object> requestFields = new HashMap<>();
                requestFields.put("status", "cancelled");

                firestoreService.updateRequestFields(request.getRequestId(), requestFields, new FirestoreCallback() {
                    @Override
                    public void onSuccess() {
                        Map<String, Object> sessionFields = new HashMap<>();
                        sessionFields.put("status", "cancelled");

                        firestoreService.updateSessionByRequestId(request.getRequestId(), sessionFields, new FirestoreCallback() {
                            @Override
                            public void onSuccess() {
                                int pos = holder.getBindingAdapterPosition();
                                if (pos != RecyclerView.NO_POSITION) {
                                    request.setStatus("cancelled");
                                    notifyItemChanged(pos);
                                    firestoreService.createNotification(
                                            request.getStudentId(),
                                            "Session Cancelled",
                                            "Tutor cancelled your session scheduled on " + request.getDate() + ". Your credits have been refunded.",
                                            "session_cancelled"
                                    );
                                    Toast.makeText(context, "Session cancelled and credits refunded", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, "Refund failed: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}