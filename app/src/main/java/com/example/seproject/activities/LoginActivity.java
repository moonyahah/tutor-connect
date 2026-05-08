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
import com.example.seproject.services.FirestoreService;
import com.example.seproject.services.TutorProfileCallback;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView signupRedirect;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signupRedirect = findViewById(R.id.signupRedirect);

        if (authService == null) {
            authService = new FirebaseAuthService();
        }

        loginButton.setOnClickListener(view -> handleLogin());

        signupRedirect.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }

    private void handleLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        loginButton.setEnabled(false);

        authService.login(email, password, new AuthCallback() {
            @Override
            public void onSuccess(String role, String uid) {
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    if (role.equals("tutor")) {
                        new FirestoreService()
                            .getTutorProfile(uid, new TutorProfileCallback() {
                                    @Override
                                    public void onSuccess(com.example.seproject.models.TutorProfile profile) {
                                        startActivity(new Intent(LoginActivity.this, TutorDashboardActivity.class));
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(String message) {
                                        // profile doesn't exist → create it
                                        startActivity(new Intent(LoginActivity.this, CreateTutorProfileActivity.class));
                                        finish();
                                    }
                                });
                    }
                    else if (role.equals("advisor")) {
                        startActivity(new Intent(LoginActivity.this, AdvisorDashboardActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(LoginActivity.this, StudentDashboardActivity.class));
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> {
                    loginButton.setEnabled(true);
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}