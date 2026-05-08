package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.seproject.R;
import com.example.seproject.adapters.SessionAdapter;
import com.example.seproject.models.Session;
import com.example.seproject.services.FirestoreCallback;
import com.example.seproject.services.FirestoreService;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentSessionsActivity extends AppCompatActivity {

    private enum SessionFilter {
        UPCOMING,
        HISTORY
    }

    private RecyclerView recyclerView;
    private SessionAdapter adapter;
    private List<Session> allSessions;
    private List<Session> visibleSessions;
    private TextView emptyStateText;
    private SessionFilter currentFilter = SessionFilter.UPCOMING;
    private FirestoreService firestoreService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sessions);

        recyclerView = findViewById(R.id.sessionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyStateText = findViewById(R.id.emptyStateText);
        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.sessionFilterToggle);

        allSessions = new ArrayList<>();
        visibleSessions = new ArrayList<>();
        firestoreService = new FirestoreService();
        adapter = new SessionAdapter(visibleSessions, firestoreService, new SessionAdapter.OnSessionActionListener() {
            @Override
            public void onCancelClicked(Session session, int position) {
                cancelSession(session, position);
            }

            @Override
            public void onReviewClicked(Session session, int position) {
                showReviewDialog(session);
            }
        });
        recyclerView.setAdapter(adapter);

        String studentId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestoreService
                .getSessionsForStudent(studentId, new com.example.seproject.services.SessionsCallback() {
                    @Override
                    public void onSuccess(List<Session> list) {
                        runOnUiThread(() -> {
                            allSessions.clear();
                            allSessions.addAll(list);
                            applyFilter();
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() ->
                                Toast.makeText(StudentSessionsActivity.this, message, Toast.LENGTH_SHORT).show()
                        );
                    }
                });

        toggleGroup.check(R.id.upcomingSessionsButton);
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) return;
            if (checkedId == R.id.upcomingSessionsButton) {
                currentFilter = SessionFilter.UPCOMING;
            } else if (checkedId == R.id.historySessionsButton) {
                currentFilter = SessionFilter.HISTORY;
            }
            applyFilter();
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(StudentSessionsActivity.this, StudentDashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void applyFilter() {
        visibleSessions.clear();

        for (Session session : allSessions) {
            if (currentFilter == SessionFilter.UPCOMING && isUpcoming(session)) {
                visibleSessions.add(session);
            } else if (currentFilter == SessionFilter.HISTORY && isHistory(session)) {
                visibleSessions.add(session);
            }
        }

        adapter.notifyDataSetChanged();
        emptyStateText.setVisibility(visibleSessions.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
    }

    private boolean isUpcoming(Session session) {
        String status = session.getStatus() == null ? "" : session.getStatus().toLowerCase();
        if (status.equals("cancelled") || status.equals("completed") || status.equals("declined")) {
            return false;
        }

        Date sessionDate = parseDate(session.getDate());
        if (sessionDate == null) return true;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return !sessionDate.before(cal.getTime());
    }

    private boolean isHistory(Session session) {
        return !isUpcoming(session);
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            format.setLenient(false);
            return format.parse(dateStr.trim());
        } catch (ParseException e) {
            return null;
        }
    }

    private void cancelSession(Session session, int position) {
        // Refund credits first
        firestoreService.refundCredits(session.getStudentId(), session.getTutorId(), session.getCreditAmount(), new FirestoreCallback() {
            @Override
            public void onSuccess() {
                firestoreService.updateSessionStatus(session.getSessionId(), "cancelled", new FirestoreCallback() {
                    @Override
                    public void onSuccess() {
                        session.setStatus("cancelled");

                        String requestId = session.getRequestId();
                        if (requestId != null && !requestId.trim().isEmpty()) {
                            firestoreService.updateRequestStatus(requestId, "cancelled", new FirestoreCallback() {
                                @Override
                                public void onSuccess() {
                                    runOnUiThread(() -> {
                                        Toast.makeText(StudentSessionsActivity.this, "Session cancelled and credits refunded", Toast.LENGTH_SHORT).show();
                                        applyFilter();
                                    });
                                }

                                @Override
                                public void onFailure(String message) {
                                    runOnUiThread(() -> {
                                        Toast.makeText(StudentSessionsActivity.this, "Session cancelled and credits refunded", Toast.LENGTH_SHORT).show();
                                        applyFilter();
                                    });
                                }
                            });
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(StudentSessionsActivity.this, "Session cancelled and credits refunded", Toast.LENGTH_SHORT).show();
                                applyFilter();
                            });
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() -> Toast.makeText(StudentSessionsActivity.this, message, Toast.LENGTH_SHORT).show());
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(StudentSessionsActivity.this, "Refund failed: " + message, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void showReviewDialog(Session session) {
        android.view.View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_review, null);
        android.widget.RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        android.widget.EditText reviewInput = dialogView.findViewById(R.id.reviewInput);

        new android.app.AlertDialog.Builder(this)
                .setTitle("Rate this tutor")
                .setView(dialogView)
                .setPositiveButton("Submit", (dialog, which) -> {
                    int rating = Math.round(ratingBar.getRating());
                    String comment = reviewInput.getText().toString().trim();

                    if (rating <= 0) {
                        Toast.makeText(this, "Please provide a rating", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    firestoreService.submitReviewForSession(
                            session.getSessionId(),
                            session.getTutorId(),
                            rating,
                            comment,
                            new FirestoreCallback() {
                                @Override
                                public void onSuccess() {
                                    runOnUiThread(() -> {
                                        session.setRating(rating);
                                        session.setReviewComment(comment);
                                        session.setStatus("completed");
                                        Toast.makeText(StudentSessionsActivity.this, "Review submitted", Toast.LENGTH_SHORT).show();
                                        applyFilter();
                                    });
                                }

                                @Override
                                public void onFailure(String message) {
                                    runOnUiThread(() ->
                                            Toast.makeText(StudentSessionsActivity.this, message, Toast.LENGTH_SHORT).show()
                                    );
                                }
                            }
                    );
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}