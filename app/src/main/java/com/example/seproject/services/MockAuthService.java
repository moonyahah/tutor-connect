package com.example.seproject.services;

public class MockAuthService implements AuthService {
    @Override
    public void signup(String name, String email, String password, String role, AuthCallback callback) {
        if (email.equals("fail@test.com")) {
            callback.onFailure("Signup failed");
        }
        else {
            callback.onSuccess(role, "string");
        }
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        if (email.equals("soap@xyz.com") && password.equals("123456")) {
            callback.onSuccess("student", "student123");
        }
        else if (email.equals("tut@xyz.com") && password.equals("123456")) {
            callback.onSuccess("tutor", "tutor123");
        }
        else if (email.equals("advisor@xyz.com") && password.equals("123456")) {
            callback.onSuccess("advisor", "advisor123");
        }
        else {
            callback.onFailure("Invalid credentials");
        }
    }
}