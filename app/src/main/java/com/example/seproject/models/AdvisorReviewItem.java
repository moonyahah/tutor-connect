package com.example.seproject.models;

public class AdvisorReviewItem {
    private String sessionId;
    private String tutorId;
    private String studentId;
    private String tutorName;
    private String studentName;
    private int rating;
    private String comment;
    private boolean flagged;

    public AdvisorReviewItem(String sessionId,
                             String tutorId,
                             String studentId,
                             String tutorName,
                             String studentName,
                             int rating,
                             String comment,
                             boolean flagged) {
        this.sessionId = sessionId;
        this.tutorId = tutorId;
        this.studentId = studentId;
        this.tutorName = tutorName;
        this.studentName = studentName;
        this.rating = rating;
        this.comment = comment;
        this.flagged = flagged;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getTutorId() {
        return tutorId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getTutorName() {
        return tutorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }
}
