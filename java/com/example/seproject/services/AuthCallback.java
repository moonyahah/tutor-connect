/*
 * Purpose: Callback contract for authentication success/failure.
 * Design: Observer-style callback interface.
 * Outstanding issues: Clarify whether callbacks are invoked on the main thread.
 */
package com.example.seproject.services;

public interface AuthCallback {
    void onSuccess(String role);
    void onFailure(String message);
}