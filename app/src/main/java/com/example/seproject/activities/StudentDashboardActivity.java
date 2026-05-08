package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.seproject.R;
import com.example.seproject.adapters.TutorAdapter;
import com.example.seproject.models.TutorProfile;
import com.example.seproject.services.UserCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.*;

public class StudentDashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TutorAdapter adapter;
    private List<TutorProfile> tutorList;
    private EditText searchInput;
    private Button searchButton, viewSessionsButton;
    private TextView creditsText;
    private ImageButton notificationsButton, logoutButton;
    private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        recyclerView = findViewById(R.id.tutorRecyclerView);
        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        viewSessionsButton = findViewById(R.id.viewSessionsButton);
        creditsText = findViewById(R.id.creditsText);
        notificationsButton = findViewById(R.id.notificationsButton);
        logoutButton = findViewById(R.id.logoutButton);
        editProfileButton = findViewById(R.id.editProfileButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tutorList = new ArrayList<>();
        adapter = new TutorAdapter(tutorList);
        recyclerView.setAdapter(adapter);

        new com.example.seproject.services.FirestoreService()
                .getAllTutorProfiles(new com.example.seproject.services.TutorProfilesCallback() {
                    @Override
                    public void onSuccess(List<TutorProfile> list) {
                        runOnUiThread(() -> {
                            tutorList.clear();
                            tutorList.addAll(list);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() ->
                                Toast.makeText(StudentDashboardActivity.this, message, Toast.LENGTH_SHORT).show()
                        );
                    }
                });

        String studentId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        new com.example.seproject.services.FirestoreService().getUserById(studentId, new UserCallback() {
            @Override
            public void onSuccess(com.example.seproject.models.User user) {
                runOnUiThread(() -> creditsText.setText("Wallet: " + user.getCredits() + " credits"));
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> creditsText.setText("Wallet unavailable"));
            }
        });

        searchButton.setOnClickListener(view -> {
            String query = searchInput.getText().toString().trim();
            filterTutors(query);
        });

        viewSessionsButton.setOnClickListener(view -> {
            startActivity(new Intent(StudentDashboardActivity.this, StudentSessionsActivity.class));
        });

        notificationsButton.setOnClickListener(v ->
                startActivity(new Intent(StudentDashboardActivity.this, NotificationsActivity.class)));

        editProfileButton.setOnClickListener(v ->
                startActivity(new Intent(StudentDashboardActivity.this, EditStudentProfileActivity.class)));

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(StudentDashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void filterTutors(String query) {
        List<TutorProfile> filtered = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (TutorProfile tutor : tutorList) {
            boolean matches = false;

            // Check subjects
            for (String subject : tutor.getSubjects()) {
                if (subject.toLowerCase().contains(lowerQuery)) {
                    matches = true;
                    break;
                }
            }

            // Check location
            if (!matches && tutor.getLocation() != null && tutor.getLocation().toLowerCase().contains(lowerQuery)) {
                matches = true;
            }

            if (matches) {
                filtered.add(tutor);
            }
        }

        adapter.updateList(filtered);
    }
}
