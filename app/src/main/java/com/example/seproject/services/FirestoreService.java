package com.example.seproject.services;

import com.example.seproject.models.TutorProfile;
import com.example.seproject.models.User;
import com.example.seproject.models.AppNotification;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void updateTutorAvailability(String userId, List<String> slots, FirestoreCallback callback) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("availabilitySlots", slots);

        db.collection("tutorProfiles")
                .document(userId)
                .set(fields, SetOptions.merge())
                .addOnSuccessListener(unused -> callback.onSuccess())
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

    public void updateUserFields(String userId, Map<String, Object> fields, FirestoreCallback callback) {
        db.collection("users")
                .document(userId)
                .set(fields, SetOptions.merge())
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void updateTutorProfileFields(String userId, Map<String, Object> fields, FirestoreCallback callback) {
        db.collection("tutorProfiles")
                .document(userId)
                .set(fields, SetOptions.merge())
                .addOnSuccessListener(unused -> callback.onSuccess())
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

    public void getAllRequests(RequestsCallback callback) {
        db.collection("requests")
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

    public void updateRequestFields(String requestId, Map<String, Object> fields, FirestoreCallback callback) {
        db.collection("requests")
                .document(requestId)
                .update(fields)
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

    public void getSessionsForTutor(String tutorId, SessionsCallback callback) {
        db.collection("sessions")
                .whereEqualTo("tutorId", tutorId)
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

    public void getAllSessions(SessionsCallback callback) {
        db.collection("sessions")
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

    public void getAllUsers(UsersCallback callback) {
        db.collection("users")
                .get()
                .addOnSuccessListener(query -> {
                    List<User> users = new ArrayList<>();
                    for (var doc : query) {
                        users.add(doc.toObject(User.class));
                    }
                    callback.onSuccess(users);
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void updateSessionStatus(String sessionId, String status, FirestoreCallback callback) {
        db.collection("sessions")
                .document(sessionId)
                .update("status", status)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void updateSessionByRequestId(String requestId, Map<String, Object> fields, FirestoreCallback callback) {
        db.collection("sessions")
                .whereEqualTo("requestId", requestId)
                .get()
                .addOnSuccessListener(query -> {
                    if (query.isEmpty()) {
                        callback.onFailure("Session not found");
                        return;
                    }

                    for (var doc : query.getDocuments()) {
                        doc.getReference()
                                .set(fields, SetOptions.merge())
                                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
                    }

                    callback.onSuccess();
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void updateSessionDateByRequestId(String requestId, String newDate, FirestoreCallback callback) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("date", newDate);
        fields.put("status", "rescheduled");
        updateSessionByRequestId(requestId, fields, callback);
    }

    public void transferCredits(String studentId, String tutorId, int creditAmount, FirestoreCallback callback) {
        DocumentReference studentRef = db.collection("users").document(studentId);
        DocumentReference tutorRef = db.collection("users").document(tutorId);

        db.runTransaction(transaction -> {
            var studentSnapshot = transaction.get(studentRef);
            var tutorSnapshot = transaction.get(tutorRef);

            User student = studentSnapshot.toObject(User.class);
            User tutor = tutorSnapshot.toObject(User.class);

            if (student == null || tutor == null) {
                throw new RuntimeException("User record not found for wallet transfer");
            }

            int studentCredits = student.getCredits();
            int tutorCredits = tutor.getCredits();

            if (studentCredits < creditAmount) {
                throw new RuntimeException("Student has insufficient credits");
            }

            transaction.update(studentRef, "credits", studentCredits - creditAmount);
            transaction.update(tutorRef, "credits", tutorCredits + creditAmount);
            return null;
        }).addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void refundCredits(String studentId, String tutorId, int creditAmount, FirestoreCallback callback) {
        DocumentReference studentRef = db.collection("users").document(studentId);
        DocumentReference tutorRef = db.collection("users").document(tutorId);

        db.runTransaction(transaction -> {
            var studentSnapshot = transaction.get(studentRef);
            var tutorSnapshot = transaction.get(tutorRef);

            User student = studentSnapshot.toObject(User.class);
            User tutor = tutorSnapshot.toObject(User.class);

            if (student == null || tutor == null) {
                throw new RuntimeException("User record not found for credit refund");
            }

            int studentCredits = student.getCredits();
            int tutorCredits = tutor.getCredits();

            transaction.update(studentRef, "credits", studentCredits + creditAmount);
            transaction.update(tutorRef, "credits", Math.max(0, tutorCredits - creditAmount));
            return null;
        }).addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void submitReviewForSession(String sessionId,
                                       String tutorId,
                                       int rating,
                                       String reviewComment,
                                       FirestoreCallback callback) {
        DocumentReference sessionRef = db.collection("sessions").document(sessionId);
        DocumentReference tutorProfileRef = db.collection("tutorProfiles").document(tutorId);

        db.runTransaction(transaction -> {
            var tutorSnapshot = transaction.get(tutorProfileRef);
            TutorProfile profile = tutorSnapshot.toObject(TutorProfile.class);

            if (profile == null) {
                throw new RuntimeException("Tutor profile not found");
            }

            int currentCount = profile.getReviewCount();
            double currentAverage = profile.getAverageRating();
            int newCount = currentCount + 1;
            double newAverage = ((currentAverage * currentCount) + rating) / newCount;

            Map<String, Object> sessionFields = new HashMap<>();
            sessionFields.put("rating", rating);
            sessionFields.put("reviewComment", reviewComment);
            sessionFields.put("status", "completed");

            transaction.set(sessionRef, sessionFields, SetOptions.merge());
            transaction.update(tutorProfileRef, "averageRating", newAverage);
            transaction.update(tutorProfileRef, "reviewCount", newCount);
            return null;
        }).addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void createNotification(String userId, String title, String message, String type) {
        String notificationId = java.util.UUID.randomUUID().toString();
        AppNotification notification = new AppNotification(
                notificationId,
                userId,
                title,
                message,
                type,
                System.currentTimeMillis(),
                false
        );

        db.collection("notifications")
                .document(notificationId)
                .set(notification);
    }

    public void flagReview(String sessionId, String advisorId, String reason, FirestoreCallback callback) {
    Map<String, Object> sessionFields = new HashMap<>();
    sessionFields.put("flagged", true);

    Map<String, Object> flagRecord = new HashMap<>();
    flagRecord.put("flagId", java.util.UUID.randomUUID().toString());
    flagRecord.put("sessionId", sessionId);
    flagRecord.put("advisorId", advisorId);
    flagRecord.put("reason", reason);
    flagRecord.put("createdAt", System.currentTimeMillis());

    db.collection("sessions")
        .document(sessionId)
        .set(sessionFields, SetOptions.merge())
        .addOnSuccessListener(unused -> db.collection("flags")
            .document((String) flagRecord.get("flagId"))
            .set(flagRecord)
            .addOnSuccessListener(u -> callback.onSuccess())
            .addOnFailureListener(e -> callback.onFailure(e.getMessage())))
        .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public void getNotificationsForUser(String userId, NotificationsCallback callback) {
        db.collection("notifications")
                .whereEqualTo("userId", userId)
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(query -> {
                    List<AppNotification> list = new ArrayList<>();
                    for (var doc : query) {
                        list.add(doc.toObject(AppNotification.class));
                    }
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }
}