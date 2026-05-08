package com.example.seproject.services;

public interface AuthCallback {
    void onSuccess(String role, String uid);
    void onFailure(String message);
}