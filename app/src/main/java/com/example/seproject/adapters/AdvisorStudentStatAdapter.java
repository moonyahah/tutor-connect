package com.example.seproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.models.AdvisorStudentStat;

import java.util.List;

public class AdvisorStudentStatAdapter extends RecyclerView.Adapter<AdvisorStudentStatAdapter.ViewHolder> {
    private final List<AdvisorStudentStat> stats;

    public AdvisorStudentStatAdapter(List<AdvisorStudentStat> stats) {
        this.stats = stats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        TextView requestCount;

        public ViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            requestCount = itemView.findViewById(R.id.requestCount);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_advisor_student_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AdvisorStudentStat stat = stats.get(position);
        holder.studentName.setText(stat.getStudentName());
        holder.requestCount.setText("Requests: " + stat.getRequestCount());
    }

    @Override
    public int getItemCount() {
        return stats.size();
    }
}
