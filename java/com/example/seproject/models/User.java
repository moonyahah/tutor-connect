/*
 * Purpose: Domain model for application users stored in Firestore.
 * Design: Plain data holder (POJO) used for Firebase serialization.
 * Outstanding issues: Consider validating role values before persistence.
 */
package com.example.seproject.models;

/**
 * Represents an application user (student or tutor).
 */
public class User {

    private String id;
    private String name;
    private String email;
    private String role; // "student" or "tutor"

    /**
     * Creates an empty user for Firebase deserialization.
     */
    public User() {
        // Required empty constructor for Firebase
    }

    /**
     * Creates a fully populated user.
     *
     * @param id user identifier
     * @param name display name
     * @param email email address
     * @param role role name (e.g., student or tutor)
     */
    public User(String id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    /**
     * Returns the user ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id unique identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the user's display name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's display name.
     *
     * @param name display name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the role (student or tutor).
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role role name
     */
    public void setRole(String role) {
        this.role = role;
    }
}