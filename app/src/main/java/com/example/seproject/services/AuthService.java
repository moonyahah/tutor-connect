package com.example.seproject.services;

public interface AuthService {
    void signup(String name, String email, String password, String role, AuthCallback callback);

    void login(String email, String password, AuthCallback callback);
}