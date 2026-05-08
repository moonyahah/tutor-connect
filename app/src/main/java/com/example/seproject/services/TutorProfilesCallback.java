package com.example.seproject.services;

import com.example.seproject.models.TutorProfile;
import java.util.List;

public interface TutorProfilesCallback {
    void onSuccess(List<TutorProfile> list);
    void onFailure(String message);
}