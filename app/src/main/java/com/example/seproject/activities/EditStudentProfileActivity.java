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
import com.example.seproject.services.UserCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class EditStudentProfileActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText emailInput;
    private EditText creditsInput;
    private Button saveButton;
    private FirestoreService firestoreService;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        creditsInput = findViewById(R.id.creditsInput);
        saveButton = findViewById(R.id.saveButton);

        firestoreService = new FirestoreService();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle("Edit Student Profile");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        loadStudentProfile();
        saveButton.setOnClickListener(v -> saveProfile());
    }

    private void loadStudentProfile() {
        firestoreService.getUserById(userId, new UserCallback() {
            @Override
            public void onSuccess(com.example.seproject.models.User user) {
                runOnUiThread(() -> {
                    nameInput.setText(user.getName());
                    emailInput.setText(user.getEmail());
                    creditsInput.setText(String.valueOf(user.getCredits()));
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(EditStudentProfileActivity.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void saveProfile() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Name and email are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> fields = new HashMap<>();
        fields.put("name", name);
        fields.put("email", email);

        saveButton.setEnabled(false);
        firestoreService.updateUserFields(userId, fields, new FirestoreCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(EditStudentProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> {
                    saveButton.setEnabled(true);
                    Toast.makeText(EditStudentProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
