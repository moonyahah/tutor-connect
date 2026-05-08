/*
 * Purpose: Callback for retrieving a single User from Firestore.
 * Design: Observer-style callback interface.
 * Outstanding issues: Clarify thread context for callback invocation.
 */
package com.example.seproject.services;

import com.example.seproject.models.User;

public interface UserCallback {
    void onSuccess(User user);
    void onFailure(String message);
}