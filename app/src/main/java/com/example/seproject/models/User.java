package com.example.seproject.models;

public class User {

    private String id;
    private String name;
    private String email;
    private String role; // "student" or "tutor"
    private int credits;

    public User() {
        // Required empty constructor for Firebase
    }

    public User(String id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.credits = 100;
    }

    public User(String id, String name, String email, String role, int credits) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.credits = credits;
    }

    /** Returns user ID */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /** Returns user name */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Returns user email */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /** Returns role (student/tutor) */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}