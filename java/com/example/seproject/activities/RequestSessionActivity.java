/*
 * Purpose: Lets a student submit a session request to a tutor.
 * Design: Android Activity that writes Request data to Firestore.
 * Outstanding issues: Validate date format and handle missing tutorId extras.
 */
package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seproject.R;
import com.example.seproject.models.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class RequestSessionActivity extends AppCompatActivity {
    private EditText dateInput, messageInput;
    private Button sendButton;
    private String tutorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_session);

        dateInput = findViewById(R.id.dateInput);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendRequestButton);

        tutorId = getIntent().getStringExtra("tutorId");

        sendButton.setOnClickListener(v -> sendRequest());
    }

    private void sendRequest() {
        String date = dateInput.getText().toString().trim();
        String message = messageInput.getText().toString().trim();

        if (TextUtils.isEmpty(date) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String studentId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String requestId = UUID.randomUUID().toString();

        Request request = new Request(
                requestId,
                studentId,
                tutorId,
                message,
                date,
                "pending"
        );

        FirebaseFirestore.getInstance()
                .collection("requests")
                .document(requestId)
                .set(request)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Request sent!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });

        startActivity(new Intent(RequestSessionActivity.this, StudentDashboardActivity.class));
        finish();
    }
}