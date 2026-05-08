package com.example.seproject.models;

public class AdvisorTutorStat {
    private String tutorId;
    private String tutorName;
    private double averageRating;
    private int reviewCount;

    public AdvisorTutorStat(String tutorId, String tutorName, double averageRating, int reviewCount) {
        this.tutorId = tutorId;
        this.tutorName = tutorName;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }

    public String getTutorId() {
        return tutorId;
    }

    public String getTutorName() {
        return tutorName;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }
}
