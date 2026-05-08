package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seproject.R;
import com.example.seproject.services.FirestoreService;
import com.example.seproject.services.TutorProfileCallback;
import com.example.seproject.services.UserCallback;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class TutorProfileActivity extends AppCompatActivity {
    private TextView name, bio, ratingSummary, reviewPreview, location;
    private Button requestButton;
    private ChipGroup subjectsChipGroup;
    private String tutorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        subjectsChipGroup = findViewById(R.id.subjectsChipGroup);
        name = findViewById(R.id.tutorName);
        location = findViewById(R.id.tutorLocation);
        bio = findViewById(R.id.tutorBio);
        ratingSummary = findViewById(R.id.ratingSummary);
        reviewPreview = findViewById(R.id.reviewPreview);
        requestButton = findViewById(R.id.requestButton);

        // Get data from intent
        tutorId = getIntent().getStringExtra("tutorId");

        FirestoreService firestoreService = new FirestoreService();

        // Load tutor profile
        firestoreService.getTutorProfile(tutorId, new TutorProfileCallback() {
            @Override
            public void onSuccess(com.example.seproject.models.TutorProfile profile) {
                runOnUiThread(() -> {
                    subjectsChipGroup.removeAllViews();
                    for (String subject : profile.getSubjects()) {
                        Chip chip = new Chip(TutorProfileActivity.this);
                        chip.setText(subject);
                        chip.setClickable(false);
                        chip.setCheckable(false);
                        subjectsChipGroup.addView(chip);
                    }
                    bio.setText(profile.getBio());

                    if (profile.getLocation() != null && !profile.getLocation().isEmpty()) {
                        location.setText(profile.getLocation());
                    } else {
                        location.setText("Location not specified");
                    }

                    if (profile.getReviewCount() > 0) {
                        ratingSummary.setText(String.format(java.util.Locale.US,
                                "%.1f ★ (%d reviews)",
                                profile.getAverageRating(),
                                profile.getReviewCount()));
                    } else {
                        ratingSummary.setText("No ratings yet");
                    }
                });

                firestoreService.getSessionsForTutor(tutorId, new com.example.seproject.services.SessionsCallback() {
                    @Override
                    public void onSuccess(java.util.List<com.example.seproject.models.Session> list) {
                        runOnUiThread(() -> {
                            StringBuilder preview = new StringBuilder();
                            int shown = 0;
                            for (com.example.seproject.models.Session session : list) {
                                if (session.getRating() > 0 && session.getReviewComment() != null && !session.getReviewComment().trim().isEmpty()) {
                                    preview.append("• ")
                                            .append(session.getReviewComment())
                                            .append(" (")
                                            .append(session.getRating())
                                            .append("★)")
                                            .append("\n");
                                    shown++;
                                }
                                if (shown == 3) {
                                    break;
                                }
                            }

                            if (shown == 0) {
                                reviewPreview.setText("No written reviews yet.");
                            } else {
                                reviewPreview.setText(preview.toString().trim());
                            }
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() -> reviewPreview.setText("Reviews unavailable"));
                    }
                });

                // Fetch name
                firestoreService.getUserById(tutorId, new UserCallback() {
                    @Override
                    public void onSuccess(com.example.seproject.models.User user) {
                        runOnUiThread(() -> name.setText(user.getName()));
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() -> name.setText("Tutor"));
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(TutorProfileActivity.this, message, Toast.LENGTH_SHORT).show());
            }
        });

        requestButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, RequestSessionActivity.class);
            intent.putExtra("tutorId", tutorId);
            startActivity(intent);
        });

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}