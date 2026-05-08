package com.example.seproject.services;

import com.example.seproject.models.User;

public interface UserCallback {
    void onSuccess(User user);
    void onFailure(String message);
}