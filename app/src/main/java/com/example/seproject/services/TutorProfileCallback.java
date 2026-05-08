package com.example.seproject.services;

import com.example.seproject.models.TutorProfile;

public interface TutorProfileCallback {
    void onSuccess(TutorProfile profile);
    void onFailure(String message);
}