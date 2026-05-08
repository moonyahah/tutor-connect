package com.example.seproject.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seproject.R;
import com.example.seproject.services.FirestoreCallback;
import com.example.seproject.services.FirestoreService;
import com.example.seproject.services.TutorProfileCallback;
import com.example.seproject.services.UserCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditTutorProfileActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText emailInput;
    private EditText locationInput;
    private EditText subjectsInput;
    private EditText bioInput;
    private EditText availabilityInput;
    private Button saveButton;

    private FirestoreService firestoreService;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutor_profile);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        locationInput = findViewById(R.id.locationInput);
        subjectsInput = findViewById(R.id.subjectsInput);
        bioInput = findViewById(R.id.bioInput);
        availabilityInput = findViewById(R.id.availabilityInput);
        saveButton = findViewById(R.id.saveButton);

        firestoreService = new FirestoreService();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle("Edit Tutor Profile");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        loadTutorProfile();
        saveButton.setOnClickListener(v -> saveProfile());
    }

    private void loadTutorProfile() {
        firestoreService.getUserById(userId, new UserCallback() {
            @Override
            public void onSuccess(com.example.seproject.models.User user) {
                runOnUiThread(() -> {
                    nameInput.setText(user.getName());
                    emailInput.setText(user.getEmail());
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(EditTutorProfileActivity.this, message, Toast.LENGTH_SHORT).show());
            }
        });

        firestoreService.getTutorProfile(userId, new TutorProfileCallback() {
            @Override
            public void onSuccess(com.example.seproject.models.TutorProfile profile) {
                runOnUiThread(() -> {
                    bioInput.setText(profile.getBio());
                    locationInput.setText(profile.getLocation());
                    subjectsInput.setText(String.join(", ", profile.getSubjects()));
                    List<String> slots = profile.getAvailabilitySlots();
                    if (slots != null && !slots.isEmpty()) {
                        availabilityInput.setText(String.join(", ", slots));
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(EditTutorProfileActivity.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void saveProfile() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String subjectsText = subjectsInput.getText().toString().trim();
        String bio = bioInput.getText().toString().trim();
        String availabilityText = availabilityInput.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(subjectsText) || TextUtils.isEmpty(bio)) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> subjects = splitCommaSeparated(subjectsText);
        List<String> availabilitySlots = splitCommaSeparated(availabilityText);

        Map<String, Object> userFields = new HashMap<>();
        userFields.put("name", name);
        userFields.put("email", email);

        Map<String, Object> tutorFields = new HashMap<>();
        tutorFields.put("subjects", subjects);
        tutorFields.put("bio", bio);
        tutorFields.put("location", location);
        tutorFields.put("availabilitySlots", availabilitySlots);

        saveButton.setEnabled(false);
        firestoreService.updateUserFields(userId, userFields, new FirestoreCallback() {
            @Override
            public void onSuccess() {
                firestoreService.updateTutorProfileFields(userId, tutorFields, new FirestoreCallback() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(() -> {
                            Toast.makeText(EditTutorProfileActivity.this, "Tutor profile updated", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() -> {
                            saveButton.setEnabled(true);
                            Toast.makeText(EditTutorProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> {
                    saveButton.setEnabled(true);
                    Toast.makeText(EditTutorProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private List<String> splitCommaSeparated(String input) {
        List<String> values = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            return values;
        }

        String[] parts = input.split(",");
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                values.add(trimmed);
            }
        }

        return values;
    }
}
