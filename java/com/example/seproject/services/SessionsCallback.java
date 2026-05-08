/*
 * Purpose: Callback for retrieving session history lists.
 * Design: Observer-style callback interface.
 * Outstanding issues: Clarify thread context for callback invocation.
 */
package com.example.seproject.services;

import com.example.seproject.models.Session;
import java.util.List;

public interface SessionsCallback {
    void onSuccess(List<Session> list);
    void onFailure(String message);
}