/*
 * Purpose: Callback for retrieving a list of tutor profiles.
 * Design: Observer-style callback interface.
 * Outstanding issues: Clarify thread context for callback invocation.
 */
package com.example.seproject.services;

import com.example.seproject.models.TutorProfile;
import java.util.List;

public interface TutorProfilesCallback {
    void onSuccess(List<TutorProfile> list);
    void onFailure(String message);
}