package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seproject.R;
import com.example.seproject.models.TutorProfile;
import com.example.seproject.services.FirestoreCallback;
import com.example.seproject.services.FirestoreService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class CreateTutorProfileActivity extends AppCompatActivity {

    private EditText subjectsInput, bioInput, availabilityInput, locationInput;
    private Button saveButton;
    private FirestoreService firestoreService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tutor_profile);

        locationInput = findViewById(R.id.locationInput);
        subjectsInput = findViewById(R.id.subjectsInput);
        bioInput = findViewById(R.id.bioInput);
        availabilityInput = findViewById(R.id.availabilityInput);
        saveButton = findViewById(R.id.saveButton);

        firestoreService = new FirestoreService();

        saveButton.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String location = locationInput.getText().toString().trim();
        String subjectsText = subjectsInput.getText().toString().trim();
        String bio = bioInput.getText().toString().trim();
        String availabilityText = availabilityInput.getText().toString().trim();

        if (TextUtils.isEmpty(subjectsText) || TextUtils.isEmpty(bio)) {
            Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        List<String> subjects = Arrays.asList(subjectsText.split(","));
        List<String> availabilitySlots = new java.util.ArrayList<>();
        if (!availabilityText.isEmpty()) {
            String[] rawSlots = availabilityText.split(",");
            for (String rawSlot : rawSlots) {
                String slot = rawSlot.trim();
                if (!slot.isEmpty()) {
                    availabilitySlots.add(slot);
                }
            }
        }

        TutorProfile profile = new TutorProfile(userId, subjects, bio, availabilitySlots);
        profile.setLocation(location);

        saveButton.setEnabled(false);

        firestoreService.saveTutorProfile(profile, new FirestoreCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(CreateTutorProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(CreateTutorProfileActivity.this, TutorDashboardActivity.class));
                    finish();
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> {
                    saveButton.setEnabled(true);
                    Toast.makeText(CreateTutorProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}