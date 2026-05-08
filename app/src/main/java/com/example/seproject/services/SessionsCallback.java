package com.example.seproject.services;

import com.example.seproject.models.Session;
import java.util.List;

public interface SessionsCallback {
    void onSuccess(List<Session> list);
    void onFailure(String message);
}