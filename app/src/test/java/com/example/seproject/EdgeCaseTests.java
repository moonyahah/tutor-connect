package com.example.seproject;

import com.example.seproject.models.*;

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Tests edge cases and unusual inputs.
 */
public class EdgeCaseTests {

    @Test
    public void testEmptyUserFields() {
        User user = new User("", "", "", "");

        assertEquals("", user.getName());
        assertEquals("", user.getEmail());
    }

    @Test
    public void testInvalidUserRole() {
        User user = new User("1", "Ali", "ali@test.com", "admin");

        assertNotEquals("student", user.getRole());
        assertNotEquals("tutor", user.getRole());
    }

    @Test
    public void testNullRequestMessage() {
        Request request = new Request();

        request.setMessage(null);

        assertNull(request.getMessage());
    }

    @Test
    public void testEmptyTutorSubjects() {
        TutorProfile tutor = new TutorProfile(
                "1",
                new ArrayList<>(),
                "No subjects yet"
        );

        assertTrue(tutor.getSubjects().isEmpty());
    }

    @Test
    public void testEmptyTutorList() {
        List<TutorProfile> tutors = new ArrayList<>();

        assertTrue(tutors.isEmpty());
    }

    @Test
    public void testSessionInvalidDateFormat() {
        Session session = new Session(
                "1",
                "student1",
                "tutor1",
                "10-04-2026", // wrong format
                "accepted"
        );

        assertFalse(session.getDate().matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    @Test
    public void testMultipleStatusChanges() {
        Request request = new Request(
                "1", "student1", "tutor1", "Help", "2026-10-04", "pending"
        );

        request.setStatus("accepted");
        request.setStatus("declined");

        assertEquals("declined", request.getStatus());
    }

    @Test
    public void testSessionWithEmptyFields() {
        Session session = new Session("", "", "", "", "");

        assertEquals("", session.getSessionId());
        assertEquals("", session.getStudentId());
    }
}
