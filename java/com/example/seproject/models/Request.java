/*
 * Purpose: Domain model for student-to-tutor session requests in Firestore.
 * Design: Plain data holder (POJO) consumed by request adapters.
 * Outstanding issues: Consider validating status transitions.
 */
package com.example.seproject.models;

/**
 * Represents a session request from a student to a tutor.
 */
public class Request {
    private String requestId;
    private String studentId;
    private String tutorId;
    private String message;
    private String date;
    private String status; // "pending", "accepted", "declined"

    /**
     * Creates an empty request for Firebase deserialization.
     */
    public Request() {
        // Required for Firebase
    }

    /**
     * Creates a request instance.
     *
     * @param requestId request identifier
     * @param studentId student identifier
     * @param tutorId tutor identifier
     * @param message request message
     * @param date requested date
     * @param status request status
     */
    public Request(String requestId, String studentId, String tutorId, String message, String date, String status) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.message = message;
        this.date = date;
        this.status = status;
    }

    /**
     * Returns the request ID.
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the request ID.
     *
     * @param requestId request identifier
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
     * Returns the request message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the request message.
     *
     * @param message request message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the requested date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the requested date.
     *
     * @param date requested date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the request status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the request status.
     *
     * @param status request status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}