/*
 * Purpose: Callback for retrieving tutor request lists.
 * Design: Observer-style callback interface.
 * Outstanding issues: Clarify thread context for callback invocation.
 */
package com.example.seproject.services;

import com.example.seproject.models.Request;
import java.util.List;

public interface RequestsCallback {
    void onSuccess(List<Request> list);
    void onFailure(String message);
}