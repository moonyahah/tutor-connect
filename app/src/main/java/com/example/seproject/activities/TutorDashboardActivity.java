package com.example.seproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.seproject.R;
import com.example.seproject.adapters.RequestAdapter;
import com.example.seproject.models.Request;
import com.example.seproject.services.FirestoreCallback;
import com.example.seproject.services.FirestoreService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class TutorDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestAdapter adapter;
    private List<Request> requestList;
    private FirestoreService firestoreService;
    private TextView incomingCount;
    private View viewHistorySection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_dashboard);

        recyclerView = findViewById(R.id.requestRecyclerView);
        incomingCount = findViewById(R.id.incomingCount);
        viewHistorySection = findViewById(R.id.viewHistorySection);
        
        Button manageAvailabilityButton = findViewById(R.id.manageAvailabilityButton);
        Button editProfileButton = findViewById(R.id.editProfileButton);
        ImageButton notificationsButton = findViewById(R.id.notificationsButton);
        ImageButton logoutButton = findViewById(R.id.logoutButton);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestoreService = new FirestoreService();
        requestList = new ArrayList<>();
        adapter = new RequestAdapter(requestList);
        recyclerView.setAdapter(adapter);

        String tutorId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestoreService
                .getRequestsForTutor(tutorId, new com.example.seproject.services.RequestsCallback() {
                    @Override
                    public void onSuccess(List<Request> list) {
                        runOnUiThread(() -> {
                            requestList.clear();
                            requestList.addAll(list);
                            adapter.notifyDataSetChanged();
                            
                            long pendingCount = list.stream().filter(r -> "pending".equalsIgnoreCase(r.getStatus())).count();
                            incomingCount.setText(String.valueOf(pendingCount));
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() ->
                                android.widget.Toast.makeText(TutorDashboardActivity.this, message, android.widget.Toast.LENGTH_SHORT).show()
                        );
                    }
                });

        manageAvailabilityButton.setOnClickListener(v -> showAvailabilityDialog(tutorId));
        editProfileButton.setOnClickListener(v ->
                startActivity(new Intent(TutorDashboardActivity.this, EditTutorProfileActivity.class)));
        notificationsButton.setOnClickListener(v ->
                startActivity(new Intent(TutorDashboardActivity.this, NotificationsActivity.class)));
                
        viewHistorySection.setOnClickListener(v -> 
                startActivity(new Intent(TutorDashboardActivity.this, TutorEarningsActivity.class)));

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(TutorDashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void showAvailabilityDialog(String tutorId) {
        EditText input = new EditText(this);
        input.setHint("YYYY-MM-DD, YYYY-MM-DD");

        new android.app.AlertDialog.Builder(this)
                .setTitle("Set Availability")
                .setMessage("Enter available dates (comma separated)")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    String text = input.getText().toString().trim();
                    List<String> slots = new ArrayList<>();
                    if (!text.isEmpty()) {
                        for (String value : Arrays.asList(text.split(","))) {
                            String slot = value.trim();
                            if (!slot.isEmpty()) {
                                slots.add(slot);
                            }
                        }
                    }

                    firestoreService.updateTutorAvailability(tutorId, slots, new FirestoreCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> Toast.makeText(TutorDashboardActivity.this, "Availability updated", Toast.LENGTH_SHORT).show());
                        }

                        @Override
                        public void onFailure(String message) {
                            runOnUiThread(() -> Toast.makeText(TutorDashboardActivity.this, message, Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}