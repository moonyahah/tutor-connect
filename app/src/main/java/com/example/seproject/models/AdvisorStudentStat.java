package com.example.seproject.models;

public class AdvisorStudentStat {
    private String studentId;
    private String studentName;
    private int requestCount;

    public AdvisorStudentStat(String studentId, String studentName, int requestCount) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.requestCount = requestCount;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getRequestCount() {
        return requestCount;
    }
}
