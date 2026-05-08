package com.example.seproject.models;

import java.util.List;

/**
 * Represents tutor-specific profile information.
 */
public class TutorProfile {
    private String userId;
    private List<String> subjects;
    private String bio;
    private List<String> availabilitySlots;
    private double averageRating;
    private int reviewCount;
    private String location;

    public TutorProfile() {
        // Required for Firebase
    }

    public TutorProfile(String userId, List<String> subjects, String bio) {
        this.userId = userId;
        this.subjects = subjects;
        this.bio = bio;
        this.availabilitySlots = new java.util.ArrayList<>();
        this.averageRating = 0.0;
        this.reviewCount = 0;
        this.location = "";
    }

    public TutorProfile(String userId, List<String> subjects, String bio, List<String> availabilitySlots) {
        this.userId = userId;
        this.subjects = subjects;
        this.bio = bio;
        this.availabilitySlots = availabilitySlots;
        this.averageRating = 0.0;
        this.reviewCount = 0;
        this.location = "";
    }

    /** Returns tutor's user ID */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** Returns list of subjects */
    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    /** Returns tutor bio */
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getAvailabilitySlots() {
        return availabilitySlots;
    }

    public void setAvailabilitySlots(List<String> availabilitySlots) {
        this.availabilitySlots = availabilitySlots;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
