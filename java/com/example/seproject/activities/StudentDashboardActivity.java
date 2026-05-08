/*
 * Purpose: Student home screen listing tutors and search/filter actions.
 * Design: Android Activity with RecyclerView list of tutor profiles.
 * Outstanding issues: Debounce search and handle empty subject lists.
 */
package com.example.seproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.seproject.R;
import com.example.seproject.adapters.TutorAdapter;
import com.example.seproject.models.TutorProfile;

import java.util.*;

public class StudentDashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TutorAdapter adapter;
    private List<TutorProfile> tutorList;
    private EditText searchInput;
    private Button searchButton, viewSessionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        recyclerView = findViewById(R.id.tutorRecyclerView);
        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        viewSessionsButton = findViewById(R.id.viewSessionsButton);

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

        searchButton.setOnClickListener(view -> {
            String query = searchInput.getText().toString().trim();
            filterTutors(query);
        });

        viewSessionsButton.setOnClickListener(view -> {
            startActivity(new Intent(StudentDashboardActivity.this, StudentSessionsActivity.class));
        });
    }

    private void filterTutors(String query) {
        List<TutorProfile> filtered = new ArrayList<>();

        for (TutorProfile tutor : tutorList) {
            for (String subject : tutor.getSubjects()) {
                if (subject.toLowerCase().contains(query.toLowerCase())) {
                    filtered.add(tutor);
                    break;
                }
            }
        }

        Button viewSessions = findViewById(R.id.viewSessionsButton);

        viewSessions.setOnClickListener(v -> {
            startActivity(new Intent(this, StudentSessionsActivity.class));
        });

        adapter.updateList(filtered);
    }
}
