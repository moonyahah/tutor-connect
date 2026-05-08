/*
 * Purpose: Displays a student's session history list.
 * Design: Android Activity with RecyclerView and toolbar navigation.
 * Outstanding issues: Handle empty sessions state and auth null checks.
 */
package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.seproject.R;
import com.example.seproject.adapters.SessionAdapter;
import com.example.seproject.models.Session;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.*;

public class StudentSessionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SessionAdapter adapter;
    private List<Session> sessionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sessions);

        recyclerView = findViewById(R.id.sessionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionList = new ArrayList<>();
        com.example.seproject.services.FirestoreService firestoreService = new com.example.seproject.services.FirestoreService();
        adapter = new SessionAdapter(sessionList, firestoreService);
        recyclerView.setAdapter(adapter);

        String studentId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();

        new com.example.seproject.services.FirestoreService()
                .getSessionsForStudent(studentId, new com.example.seproject.services.SessionsCallback() {
                    @Override
                    public void onSuccess(List<Session> list) {
                        runOnUiThread(() -> {
                            sessionList.clear();
                            sessionList.addAll(list);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() ->
                                android.widget.Toast.makeText(StudentSessionsActivity.this, message, android.widget.Toast.LENGTH_SHORT).show()
                        );
                    }
                });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); // optional custom icon
        }

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(StudentSessionsActivity.this, StudentDashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}