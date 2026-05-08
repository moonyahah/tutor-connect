package com.example.seproject.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.adapters.NotificationAdapter;
import com.example.seproject.models.AppNotification;
import com.example.seproject.services.FirestoreService;
import com.example.seproject.services.NotificationsCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyText;
    private NotificationAdapter adapter;
    private List<AppNotification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = findViewById(R.id.notificationsRecyclerView);
        emptyText = findViewById(R.id.emptyNotificationText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        new FirestoreService().getNotificationsForUser(userId, new NotificationsCallback() {
            @Override
            public void onSuccess(List<AppNotification> notifications) {
                runOnUiThread(() -> {
                    notificationList.clear();
                    notificationList.addAll(notifications);
                    adapter.notifyDataSetChanged();
                    emptyText.setVisibility(notificationList.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> emptyText.setText(message));
            }
        });
    }
}
