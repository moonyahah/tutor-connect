package com.example.seproject.models;

public class Session {

    private String sessionId;
    private String requestId;
    private String studentId;
    private String tutorId;
    private String date;
    private String status; // "accepted", "completed", etc.
    private int rating;
    private String reviewComment;
    private boolean flagged;
    private int creditAmount;

    public Session() {
        // Required for Firebase
    }

    public Session(String sessionId, String studentId, String tutorId, String date, String status) {
        this.sessionId = sessionId;
        this.requestId = "";
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.date = date;
        this.status = status;
        this.rating = 0;
        this.reviewComment = "";
        this.flagged = false;
        this.creditAmount = 0;
    }

    public Session(String sessionId, String requestId, String studentId, String tutorId, String date, String status) {
        this.sessionId = sessionId;
        this.requestId = requestId;
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.date = date;
        this.status = status;
        this.rating = 0;
        this.reviewComment = "";
        this.flagged = false;
        this.creditAmount = 0;
    }

    public Session(String sessionId, String requestId, String studentId, String tutorId, String date, String status, int creditAmount) {
        this.sessionId = sessionId;
        this.requestId = requestId;
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.date = date;
        this.status = status;
        this.rating = 0;
        this.reviewComment = "";
        this.flagged = false;
        this.creditAmount = creditAmount;
    }

    /** Returns session ID */
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /** Returns student ID */
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /** Returns tutor ID */
    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    /** Returns session date */
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /** Returns session status */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public int getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(int creditAmount) {
        this.creditAmount = creditAmount;
    }
}