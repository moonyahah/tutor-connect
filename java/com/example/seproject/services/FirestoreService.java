/*
 * Purpose: Central data access layer for Firestore collections.
 * Design: Service layer wrapping Firestore read/write operations.
 * Outstanding issues: Add null checks for toObject results and consider caching.
 */
package com.example.seproject.services;

import com.example.seproject.models.TutorProfile;
import com.example.seproject.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirestoreService {

    private FirebaseFirestore db;

    public FirestoreService() {
        db = FirebaseFirestore.getInstance();
    }

    public void saveUser(User user, FirestoreCallback callback) {
        db.collection("users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void getUser(String uid, UserCallback callback) {
        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        User user = doc.toObject(User.class);
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure("User not found");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void saveTutorProfile(com.example.seproject.models.TutorProfile profile, FirestoreCallback callback) {
        db.collection("tutorProfiles")
                .document(profile.getUserId())
                .set(profile)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void getTutorProfile(String userId, TutorProfileCallback callback) {
        db.collection("tutorProfiles")
                .document(userId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        com.example.seproject.models.TutorProfile profile =
                                doc.toObject(com.example.seproject.models.TutorProfile.class);
                        callback.onSuccess(profile);
                    } else {
                        callback.onFailure("Profile not found");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void getAllTutorProfiles(TutorProfilesCallback callback) {
        db.collection("tutorProfiles")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<TutorProfile> list = new ArrayList<>();
                    for (var doc : queryDocumentSnapshots) {
                        list.add(doc.toObject(com.example.seproject.models.TutorProfile.class));
                    }
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void getUserById(String userId, UserCallback callback) {
        db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        callback.onSuccess(doc.toObject(com.example.seproject.models.User.class));
                    } else {
                        callback.onFailure("User not found");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void getRequestsForTutor(String tutorId, RequestsCallback callback) {
        db.collection("requests")
                .whereEqualTo("tutorId", tutorId)
                .get()
                .addOnSuccessListener(query -> {
                    List<com.example.seproject.models.Request> list = new ArrayList<>();
                    for (var doc : query) {
                        list.add(doc.toObject(com.example.seproject.models.Request.class));
                    }
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void updateRequestStatus(String requestId, String status, FirestoreCallback callback) {
        db.collection("requests")
                .document(requestId)
                .update("status", status)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void getSessionsForStudent(String studentId, SessionsCallback callback) {
        db.collection("sessions")
                .whereEqualTo("studentId", studentId)
                .get()
                .addOnSuccessListener(query -> {
                    List<com.example.seproject.models.Session> list = new ArrayList<>();
                    for (var doc : query) {
                        list.add(doc.toObject(com.example.seproject.models.Session.class));
                    }
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }
}