/*
 * Purpose: Tutor home screen listing incoming session requests.
 * Design: Android Activity with RecyclerView using RequestAdapter.
 * Outstanding issues: Add pull-to-refresh and empty state messaging.
 */
package com.example.seproject.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.seproject.R;
import com.example.seproject.adapters.RequestAdapter;
import com.example.seproject.models.Request;

import java.util.*;

public class TutorDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestAdapter adapter;
    private List<Request> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_dashboard);

        recyclerView = findViewById(R.id.requestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestList = new ArrayList<>();
        adapter = new RequestAdapter(requestList);
        recyclerView.setAdapter(adapter);

        String tutorId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();

        new com.example.seproject.services.FirestoreService()
                .getRequestsForTutor(tutorId, new com.example.seproject.services.RequestsCallback() {
                    @Override
                    public void onSuccess(List<Request> list) {
                        runOnUiThread(() -> {
                            requestList.clear();
                            requestList.addAll(list);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() ->
                                android.widget.Toast.makeText(TutorDashboardActivity.this, message, android.widget.Toast.LENGTH_SHORT).show()
                        );
                    }
                });
    }
}