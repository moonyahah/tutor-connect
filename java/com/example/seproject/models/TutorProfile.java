/*
 * Purpose: Domain model for tutor profile details stored in Firestore.
 * Design: Plain data holder (POJO) backing tutor discovery and profiles.
 * Outstanding issues: Consider normalizing subject input and trimming values.
 */
package com.example.seproject.models;

import java.util.List;

/**
 * Represents tutor-specific profile information.
 */
public class TutorProfile {
    private String userId;
    private List<String> subjects;
    private String bio;

    /**
     * Creates an empty profile for Firebase deserialization.
     */
    public TutorProfile() {
        // Required for Firebase
    }

    /**
     * Creates a tutor profile instance.
     *
     * @param userId tutor's user ID
     * @param subjects list of subjects taught
     * @param bio short biography
     */
    public TutorProfile(String userId, List<String> subjects, String bio) {
        this.userId = userId;
        this.subjects = subjects;
        this.bio = bio;
    }

    /**
     * Returns the tutor's user ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the tutor's user ID.
     *
     * @param userId tutor identifier
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the list of subjects taught.
     */
    public List<String> getSubjects() {
        return subjects;
    }

    /**
     * Sets the list of subjects taught.
     *
     * @param subjects subject list
     */
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    /**
     * Returns the tutor bio.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the tutor bio.
     *
     * @param bio short biography
     */
    public void setBio(String bio) {
        this.bio = bio;
    }
}
