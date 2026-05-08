/*
 * Purpose: Domain model representing tutoring sessions stored in Firestore.
 * Design: Plain data holder (POJO) used by adapters and activities.
 * Outstanding issues: Consider using typed date/time instead of String.
 */
package com.example.seproject.models;

/**
 * Represents a tutoring session between a student and tutor.
 */
public class Session {

    private String sessionId;
    private String studentId;
    private String tutorId;
    private String date;
    private String status; // "accepted", "completed", etc.

    /**
     * Creates an empty session for Firebase deserialization.
     */
    public Session() {
        // Required for Firebase
    }

    /**
     * Creates a session instance.
     *
     * @param sessionId session identifier
     * @param studentId student identifier
     * @param tutorId tutor identifier
     * @param date session date (string-formatted)
     * @param status current status
     */
    public Session(String sessionId, String studentId, String tutorId, String date, String status) {
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.date = date;
        this.status = status;
    }

    /**
     * Returns the session ID.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the session ID.
     *
     * @param sessionId session identifier
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Returns the student ID.
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Sets the student ID.
     *
     * @param studentId student identifier
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns the tutor ID.
     */
    public String getTutorId() {
        return tutorId;
    }

    /**
     * Sets the tutor ID.
     *
     * @param tutorId tutor identifier
     */
    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    /**
     * Returns the session date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the session date.
     *
     * @param date session date (string-formatted)
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the session status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the session status.
     *
     * @param status session status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}