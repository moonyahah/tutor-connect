package com.example.seproject.services;

public interface FirestoreCallback {
    void onSuccess();
    void onFailure(String message);
}