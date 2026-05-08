/*
 * Purpose: Callback contract for Firestore write operations.
 * Design: Observer-style callback interface.
 * Outstanding issues: Clarify main-thread delivery for UI usage.
 */
package com.example.seproject.services;

public interface FirestoreCallback {
    void onSuccess();
    void onFailure(String message);
}