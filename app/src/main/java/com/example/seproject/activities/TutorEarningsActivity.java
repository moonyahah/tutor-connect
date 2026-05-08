package com.example.seproject.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.adapters.SessionAdapter;
import com.example.seproject.models.Session;
import com.example.seproject.services.FirestoreService;
import com.example.seproject.services.SessionsCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class TutorEarningsActivity extends AppCompatActivity {

    private TextView totalEarningsText;
    private RecyclerView historyRecyclerView;
    private SessionAdapter adapter;
    private List<Session> sessionList;
    private FirestoreService firestoreService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_earnings);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Earnings & History");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        totalEarningsText = findViewById(R.id.totalEarningsText);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionList = new ArrayList<>();
        firestoreService = new FirestoreService();
        
        // Pass true for isTutorView so it displays Student names
        adapter = new SessionAdapter(sessionList, firestoreService, null, true);
        historyRecyclerView.setAdapter(adapter);

        loadTutorHistory();
    }

    private void loadTutorHistory() {
        String tutorId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestoreService.getSessionsForTutor(tutorId, new SessionsCallback() {
            @Override
            public void onSuccess(List<Session> list) {
                runOnUiThread(() -> {
                    sessionList.clear();
                    sessionList.addAll(list);
                    adapter.notifyDataSetChanged();

                    int total = 0;
                    for (Session s : list) {
                        // Include both accepted (pending completion) and completed sessions in earnings
                        if ("completed".equalsIgnoreCase(s.getStatus()) || "accepted".equalsIgnoreCase(s.getStatus())) {
                             total += s.getCreditAmount();
                        }
                    }
                    totalEarningsText.setText(total + " Credits");
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(TutorEarningsActivity.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }
}