package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seproject.R;
import com.example.seproject.models.Request;
import com.example.seproject.models.TutorProfile;
import com.example.seproject.services.FirestoreService;
import com.example.seproject.services.TutorProfileCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class RequestSessionActivity extends AppCompatActivity {
    private EditText dateInput, messageInput, creditsInput;
    private Button sendButton;
    private String tutorId;
    private FirestoreService firestoreService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_session);

        dateInput = findViewById(R.id.dateInput);
        messageInput = findViewById(R.id.messageInput);
    creditsInput = findViewById(R.id.creditsInput);
        sendButton = findViewById(R.id.sendRequestButton);

        tutorId = getIntent().getStringExtra("tutorId");
        firestoreService = new FirestoreService();

        sendButton.setOnClickListener(v -> sendRequest());
    }

    private void sendRequest() {
        String date = dateInput.getText().toString().trim();
        String message = messageInput.getText().toString().trim();
        String creditsText = creditsInput.getText().toString().trim();

        if (TextUtils.isEmpty(date) || TextUtils.isEmpty(message) || TextUtils.isEmpty(creditsText)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int credits;
        try {
            credits = Integer.parseInt(creditsText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Credits must be a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (credits <= 0) {
            Toast.makeText(this, "Credits must be greater than zero", Toast.LENGTH_SHORT).show();
            return;
        }

        sendButton.setEnabled(false);

        firestoreService.getTutorProfile(tutorId, new TutorProfileCallback() {
            @Override
            public void onSuccess(TutorProfile profile) {
                runOnUiThread(() -> {
                    java.util.List<String> slots = profile.getAvailabilitySlots();
                    if (slots != null && !slots.isEmpty() && !slots.contains(date)) {
                        sendButton.setEnabled(true);
                        Toast.makeText(RequestSessionActivity.this,
                                "Tutor is not available on this date. Choose one of: " + slots,
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    createRequest(date, message, credits);
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    sendButton.setEnabled(true);
                    Toast.makeText(RequestSessionActivity.this, error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void createRequest(String date, String message, int credits) {
        String studentId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String requestId = UUID.randomUUID().toString();

        Request request = new Request(
                requestId,
                studentId,
                tutorId,
                message,
                date,
        "pending",
        credits
        );

        FirebaseFirestore.getInstance()
                .collection("requests")
                .document(requestId)
                .set(request)
                .addOnSuccessListener(unused -> {
                    firestoreService.createNotification(
                            tutorId,
                            "New Session Request",
                            "A student requested a session on " + date + " for " + credits + " credits.",
                            "request_created"
                    );

                    Toast.makeText(this, "Request sent!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RequestSessionActivity.this, StudentDashboardActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    sendButton.setEnabled(true);
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}