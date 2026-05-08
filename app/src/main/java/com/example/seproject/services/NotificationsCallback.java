package com.example.seproject.services;

import com.example.seproject.models.AppNotification;

import java.util.List;

public interface NotificationsCallback {
    void onSuccess(List<AppNotification> notifications);
    void onFailure(String message);
}
