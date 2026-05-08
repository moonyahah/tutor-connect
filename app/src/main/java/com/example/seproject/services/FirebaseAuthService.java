package com.example.seproject.services;

import com.example.seproject.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthService implements AuthService {
    private FirebaseAuth mAuth;
    private FirestoreService firestoreService;

    public FirebaseAuthService() {
        mAuth = FirebaseAuth.getInstance();
        firestoreService = new FirestoreService();
    }

    @Override
    public void signup(String name, String email, String password, String role, AuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();

                        User user = new User(uid, name, email, role);

                        firestoreService.saveUser(user, new FirestoreCallback() {
                            @Override
                            public void onSuccess() {
                                callback.onSuccess(role, uid);
                            }

                            @Override
                            public void onFailure(String message) {
                                callback.onFailure(message);
                            }
                        });
                    }
                    else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();

                        firestoreService.getUser(uid, new UserCallback() {
                            @Override
                            public void onSuccess(User user) {
                                callback.onSuccess(user.getRole(), uid);
                            }

                            @Override
                            public void onFailure(String message) {
                                callback.onFailure(message);
                            }
                        });
                    }
                    else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }
}
