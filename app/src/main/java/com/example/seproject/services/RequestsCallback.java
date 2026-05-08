package com.example.seproject.services;

import com.example.seproject.models.Request;
import java.util.List;

public interface RequestsCallback {
    void onSuccess(List<Request> list);
    void onFailure(String message);
}