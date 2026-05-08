package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.adapters.AdvisorReviewModerationAdapter;
import com.example.seproject.adapters.AdvisorStudentStatAdapter;
import com.example.seproject.adapters.AdvisorTutorStatAdapter;
import com.example.seproject.models.AdvisorReviewItem;
import com.example.seproject.models.AdvisorStudentStat;
import com.example.seproject.models.AdvisorTutorStat;
import com.example.seproject.models.Request;
import com.example.seproject.models.Session;
import com.example.seproject.models.TutorProfile;
import com.example.seproject.models.User;
import com.example.seproject.services.FirestoreCallback;
import com.example.seproject.services.FirestoreService;
import com.example.seproject.services.RequestsCallback;
import com.example.seproject.services.SessionsCallback;
import com.example.seproject.services.TutorProfilesCallback;
import com.example.seproject.services.UsersCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdvisorDashboardActivity extends AppCompatActivity {

    private TextView totalRequestsValue;
    private TextView totalSessionsValue;
    private TextView avgRatingValue;
    private TextView flaggedValue;

    private RecyclerView frequentStudentsRecyclerView;
    private RecyclerView tutorRatingsRecyclerView;
    private RecyclerView moderationRecyclerView;

    private AdvisorStudentStatAdapter studentStatAdapter;
    private AdvisorTutorStatAdapter tutorStatAdapter;
    private AdvisorReviewModerationAdapter reviewModerationAdapter;

    private List<AdvisorStudentStat> studentStats = new ArrayList<>();
    private List<AdvisorTutorStat> tutorStats = new ArrayList<>();
    private List<AdvisorReviewItem> reviewItems = new ArrayList<>();

    private FirestoreService firestoreService;
    private String advisorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_dashboard);

        totalRequestsValue = findViewById(R.id.totalRequestsValue);
        totalSessionsValue = findViewById(R.id.totalSessionsValue);
        avgRatingValue = findViewById(R.id.avgRatingValue);
        flaggedValue = findViewById(R.id.flaggedValue);

        frequentStudentsRecyclerView = findViewById(R.id.frequentStudentsRecyclerView);
        tutorRatingsRecyclerView = findViewById(R.id.tutorRatingsRecyclerView);
        moderationRecyclerView = findViewById(R.id.moderationRecyclerView);

        frequentStudentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tutorRatingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moderationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentStatAdapter = new AdvisorStudentStatAdapter(studentStats);
        tutorStatAdapter = new AdvisorTutorStatAdapter(tutorStats);
        reviewModerationAdapter = new AdvisorReviewModerationAdapter(reviewItems, this::flagReview);

        frequentStudentsRecyclerView.setAdapter(studentStatAdapter);
        tutorRatingsRecyclerView.setAdapter(tutorStatAdapter);
        moderationRecyclerView.setAdapter(reviewModerationAdapter);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Advisor Dashboard");
        }

        ImageButton logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(AdvisorDashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        firestoreService = new FirestoreService();
        advisorId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadAdvisorData();
    }

    private void loadAdvisorData() {
        firestoreService.getAllUsers(new UsersCallback() {
            @Override
            public void onSuccess(List<User> users) {
                Map<String, User> userMap = new HashMap<>();
                for (User user : users) {
                    userMap.put(user.getId(), user);
                }

                loadRequestsAndSessions(userMap);
                loadTutorRatings(userMap);
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(AdvisorDashboardActivity.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void loadRequestsAndSessions(Map<String, User> userMap) {
        firestoreService.getAllRequests(new RequestsCallback() {
            @Override
            public void onSuccess(List<Request> requests) {
                runOnUiThread(() -> totalRequestsValue.setText(String.valueOf(requests.size())));

                Map<String, Integer> requestCounts = new HashMap<>();
                for (Request request : requests) {
                    requestCounts.put(request.getStudentId(), requestCounts.getOrDefault(request.getStudentId(), 0) + 1);
                }

                List<AdvisorStudentStat> computedStudentStats = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : requestCounts.entrySet()) {
                    String studentId = entry.getKey();
                    int count = entry.getValue();
                    String name = userMap.containsKey(studentId) ? userMap.get(studentId).getName() : studentId;
                    computedStudentStats.add(new AdvisorStudentStat(studentId, name, count));
                }

                computedStudentStats.sort((a, b) -> Integer.compare(b.getRequestCount(), a.getRequestCount()));

                runOnUiThread(() -> {
                    studentStats.clear();
                    studentStats.addAll(computedStudentStats);
                    studentStatAdapter.notifyDataSetChanged();
                });

                loadSessionsForModeration(userMap);
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(AdvisorDashboardActivity.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void loadSessionsForModeration(Map<String, User> userMap) {
        firestoreService.getAllSessions(new SessionsCallback() {
            @Override
            public void onSuccess(List<Session> sessions) {
                int flaggedCount = 0;
                int ratedCount = 0;
                int totalRating = 0;
                List<AdvisorReviewItem> moderationItems = new ArrayList<>();

                for (Session session : sessions) {
                    if (session.isFlagged()) {
                        flaggedCount++;
                    }

                    if (session.getRating() > 0) {
                        ratedCount++;
                        totalRating += session.getRating();
                    }

                    boolean hasFeedback = session.getRating() > 0
                            || (session.getReviewComment() != null && !session.getReviewComment().trim().isEmpty());

                    if (hasFeedback) {
                        String tutorName = userMap.containsKey(session.getTutorId())
                                ? userMap.get(session.getTutorId()).getName()
                                : session.getTutorId();
                        String studentName = userMap.containsKey(session.getStudentId())
                                ? userMap.get(session.getStudentId()).getName()
                                : session.getStudentId();

                        moderationItems.add(new AdvisorReviewItem(
                                session.getSessionId(),
                                session.getTutorId(),
                                session.getStudentId(),
                                tutorName,
                                studentName,
                                session.getRating(),
                                session.getReviewComment() == null ? "" : session.getReviewComment(),
                                session.isFlagged()
                        ));
                    }
                }

                double average = ratedCount == 0 ? 0 : (double) totalRating / ratedCount;
                moderationItems.sort(Comparator.comparing(AdvisorReviewItem::isFlagged));

                int finalFlaggedCount = flaggedCount;
                runOnUiThread(() -> {
                    totalSessionsValue.setText(String.valueOf(sessions.size()));
                    avgRatingValue.setText(String.format(Locale.US, "%.1f", average));
                    flaggedValue.setText(String.valueOf(finalFlaggedCount));

                    reviewItems.clear();
                    reviewItems.addAll(moderationItems);
                    reviewModerationAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(AdvisorDashboardActivity.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void loadTutorRatings(Map<String, User> userMap) {
        firestoreService.getAllTutorProfiles(new TutorProfilesCallback() {
            @Override
            public void onSuccess(List<TutorProfile> profiles) {
                List<AdvisorTutorStat> computedTutorStats = new ArrayList<>();
                for (TutorProfile profile : profiles) {
                    String tutorId = profile.getUserId();
                    String tutorName = userMap.containsKey(tutorId)
                            ? userMap.get(tutorId).getName()
                            : tutorId;

                    computedTutorStats.add(new AdvisorTutorStat(
                            tutorId,
                            tutorName,
                            profile.getAverageRating(),
                            profile.getReviewCount()
                    ));
                }

                computedTutorStats.sort((a, b) -> Double.compare(b.getAverageRating(), a.getAverageRating()));

                runOnUiThread(() -> {
                    tutorStats.clear();
                    tutorStats.addAll(computedTutorStats);
                    tutorStatAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(AdvisorDashboardActivity.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void flagReview(AdvisorReviewItem item, int position) {
        EditText reasonInput = new EditText(this);
        reasonInput.setHint("Reason for flagging");

        new android.app.AlertDialog.Builder(this)
                .setTitle("Flag this review")
                .setView(reasonInput)
                .setPositiveButton("Flag", (dialog, which) -> {
                    String reason = reasonInput.getText().toString().trim();
                    if (reason.isEmpty()) {
                        reason = "Inappropriate review content";
                    }

                    firestoreService.flagReview(item.getSessionId(), advisorId, reason, new FirestoreCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> {
                                item.setFlagged(true);
                                reviewModerationAdapter.notifyItemChanged(position);
                                flaggedValue.setText(String.valueOf(Integer.parseInt(flaggedValue.getText().toString()) + 1));
                                Toast.makeText(AdvisorDashboardActivity.this, "Review flagged", Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onFailure(String message) {
                            runOnUiThread(() -> Toast.makeText(AdvisorDashboardActivity.this, message, Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
