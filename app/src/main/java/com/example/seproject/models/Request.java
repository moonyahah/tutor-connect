package com.example.seproject.models;

public class Request {
    private String requestId;
    private String studentId;
    private String tutorId;
    private String message;
    private String date;
    private String status; // "pending", "accepted", "declined"
    private int creditAmount;

    public Request() {
        // Required for Firebase
    }

    public Request(String requestId, String studentId, String tutorId, String message, String date, String status) {
        this(requestId, studentId, tutorId, message, date, status, 10);
    }

    public Request(String requestId, String studentId, String tutorId, String message, String date, String status, int creditAmount) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.tutorId = tutorId;
        this.message = message;
        this.date = date;
        this.status = status;
        this.creditAmount = creditAmount;
    }

    /** Returns request ID */
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

    /** Returns request message */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /** Returns date */
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /** Returns request status */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(int creditAmount) {
        this.creditAmount = creditAmount;
    }
}