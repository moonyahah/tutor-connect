package com.example.seproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seproject.R;
import com.example.seproject.models.AdvisorTutorStat;

import java.util.List;
import java.util.Locale;

public class AdvisorTutorStatAdapter extends RecyclerView.Adapter<AdvisorTutorStatAdapter.ViewHolder> {
    private final List<AdvisorTutorStat> stats;

    public AdvisorTutorStatAdapter(List<AdvisorTutorStat> stats) {
        this.stats = stats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tutorName;
        TextView ratingSummary;

        public ViewHolder(View itemView) {
            super(itemView);
            tutorName = itemView.findViewById(R.id.tutorName);
            ratingSummary = itemView.findViewById(R.id.ratingSummary);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_advisor_tutor_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AdvisorTutorStat stat = stats.get(position);
        holder.tutorName.setText(stat.getTutorName());
        holder.ratingSummary.setText(String.format(Locale.US, "%.1f ★ (%d reviews)", stat.getAverageRating(), stat.getReviewCount()));
    }

    @Override
    public int getItemCount() {
        return stats.size();
    }
}
