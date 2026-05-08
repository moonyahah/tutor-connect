package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seproject.R;
import com.example.seproject.services.AuthCallback;
import com.example.seproject.services.AuthService;
import com.example.seproject.services.FirebaseAuthService;

public class SignupActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput;
    private RadioGroup roleGroup;
    private Button signupButton;
    private TextView loginRedirect;

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        roleGroup = findViewById(R.id.roleGroup);
        signupButton = findViewById(R.id.signupButton);
        loginRedirect = findViewById(R.id.loginRedirect);

        if (authService == null) {
            authService = new FirebaseAuthService();
        }

        signupButton.setOnClickListener(view -> handleSignup());

        loginRedirect.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void handleSignup() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        int selectedId = roleGroup.getCheckedRadioButtonId();
        String role;
        if (selectedId == R.id.tutorRole) {
            role = "tutor";
        } else if (selectedId == R.id.advisorRole) {
            role = "advisor";
        } else {
            role = "student";
        }

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        signupButton.setEnabled(false);

        authService.signup(name, email, password, role, new AuthCallback() {
            @Override
            public void onSuccess(String role, String uid) {
                runOnUiThread(() -> {
                    Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();

                    if (role.equals("tutor")) {
                        startActivity(new Intent(SignupActivity.this, CreateTutorProfileActivity.class));
                    }
                    else if (role.equals("advisor")) {
                        startActivity(new Intent(SignupActivity.this, AdvisorDashboardActivity.class));
                    }
                    else {
                        startActivity(new Intent(SignupActivity.this, StudentDashboardActivity.class));
                    }

                    finish();
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> {
                    signupButton.setEnabled(true);
                    Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}