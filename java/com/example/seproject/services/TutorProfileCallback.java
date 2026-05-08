/*
 * Purpose: Callback for retrieving a single TutorProfile.
 * Design: Observer-style callback interface.
 * Outstanding issues: Clarify thread context for callback invocation.
 */
package com.example.seproject.services;

import com.example.seproject.models.TutorProfile;

public interface TutorProfileCallback {
    void onSuccess(TutorProfile profile);
    void onFailure(String message);
}