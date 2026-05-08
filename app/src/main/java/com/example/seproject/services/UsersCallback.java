package com.example.seproject.services;

import com.example.seproject.models.User;

import java.util.List;

public interface UsersCallback {
    void onSuccess(List<User> users);
    void onFailure(String message);
}
